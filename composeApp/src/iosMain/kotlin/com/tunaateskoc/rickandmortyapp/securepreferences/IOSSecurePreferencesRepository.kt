@file:OptIn(ExperimentalForeignApi::class)

package com.tunaateskoc.rickandmortyapp.securepreferences

import com.tunaateskoc.rickandmortyapp.SecurePreferencesRepository
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.value
import platform.CoreFoundation.CFArrayGetCount
import platform.CoreFoundation.CFArrayGetValueAtIndex
import platform.CoreFoundation.CFArrayRefVar
import platform.CoreFoundation.CFDictionaryCreate
import platform.CoreFoundation.CFDictionaryGetValue
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.CFTypeRef
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFBooleanFalse
import platform.CoreFoundation.kCFBooleanTrue
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.NSKeyedArchiver
import platform.Foundation.NSKeyedUnarchiver
import platform.Foundation.NSNumber
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Foundation.numberWithBool
import platform.Foundation.numberWithFloat
import platform.Foundation.numberWithInt
import platform.Foundation.numberWithLongLong
import platform.Security.SecCopyErrorMessageString
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.SecItemUpdate
import platform.Security.errSecItemNotFound
import platform.Security.kSecAttrAccount
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitAll
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnAttributes
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import platform.darwin.OSStatus

@OptIn(ExperimentalForeignApi::class)
class IOSSecurePreferencesRepository(vararg defaultProperties: Pair<CFStringRef?, CFTypeRef?>) :
    SecurePreferencesRepository {

    private val defaultProperties =
        mapOf(kSecClass to kSecClassGenericPassword) + mapOf(*defaultProperties)

    private val keys: Set<String>
        get() = memScoped {
            val attributes = alloc<CFArrayRefVar>()
            val status = keyChainOperation(
                kSecMatchLimit to kSecMatchLimitAll,
                kSecReturnAttributes to kCFBooleanTrue
            ) { SecItemCopyMatching(it, attributes.ptr.reinterpret()) }
            status.checkError(errSecItemNotFound)
            if (status == errSecItemNotFound) {
                return emptySet()
            }

            val count = CFArrayGetCount(attributes.value)
            val keys = mutableListOf<String>()
            for (i in 0 until count) {
                val item: CFDictionaryRef? =
                    CFArrayGetValueAtIndex(attributes.value, i)?.reinterpret()
                val cfKey: CFStringRef? = CFDictionaryGetValue(item, kSecAttrAccount)?.reinterpret()
                val nsKey = CFBridgingRelease(cfKey) as NSString
                keys.add(nsKey.toKString())
            }

            return keys.toSet()
        }

    override fun clear(): Unit = keys.forEach { remove(it) }
    override fun remove(key: String): Unit = removeKeychainItem(key)

    override fun <T> save(key: String, value: T) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
        }
    }

    private fun putString(key: String, value: String): Unit =
        addOrUpdateKeychainItem(key, value.toNSString().dataUsingEncoding(NSUTF8StringEncoding))

    private fun putInt(key: String, value: Int): Unit =
        addOrUpdateKeychainItem(key, archiveNumber(NSNumber.numberWithInt(value)))

    private fun putBoolean(key: String, value: Boolean): Unit =
        addOrUpdateKeychainItem(key, archiveNumber(NSNumber.numberWithBool(value)))

    private fun putFloat(key: String, value: Float): Unit =
        addOrUpdateKeychainItem(key, archiveNumber(NSNumber.numberWithFloat(value)))

    private fun putLong(key: String, value: Long): Unit =
        addOrUpdateKeychainItem(key, archiveNumber(NSNumber.numberWithLongLong(value)))

    override fun getString(key: String, defaultValue: String?): String? =
        getStringOrNull(key) ?: defaultValue

    override fun getInt(key: String, defaultValue: Int): Int =
        getIntOrNull(key) ?: defaultValue

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        getBooleanOrNull(key) ?: defaultValue

    override fun getFloat(key: String, defaultValue: Float): Float =
        getFloatOrNull(key) ?: defaultValue

    override fun getLong(key: String, defaultValue: Long): Long =
        getLongOrNull(key) ?: defaultValue

    private fun getIntOrNull(key: String): Int? =
        unarchiveNumber(getKeychainItem(key))?.intValue

    private fun getLongOrNull(key: String): Long? =
        unarchiveNumber(getKeychainItem(key))?.longLongValue

    private fun getStringOrNull(key: String): String? =
        getKeychainItem(key)?.let { NSString.create(it, NSUTF8StringEncoding)?.toKString() }

    private fun getFloatOrNull(key: String): Float? =
        unarchiveNumber(getKeychainItem(key))?.floatValue

    private fun getBooleanOrNull(key: String): Boolean? =
        unarchiveNumber(getKeychainItem(key))?.boolValue

    private inline fun unarchiveNumber(data: NSData?): NSNumber? =
        data?.let { NSKeyedUnarchiver.unarchiveObjectWithData(it) } as? NSNumber

    private inline fun archiveNumber(number: NSNumber): NSData? =
        NSKeyedArchiver.archivedDataWithRootObject(number, true, null)

    private inline fun addOrUpdateKeychainItem(key: String, value: NSData?) {
        if (hasKeychainItem(key)) {
            updateKeychainItem(key, value)
        } else {
            addKeychainItem(key, value)
        }
    }

    private inline fun addKeychainItem(key: String, value: NSData?): Unit =
        cfRetain(key, value) { (cfKey, cfValue) ->
            val status = keyChainOperation(
                kSecAttrAccount to cfKey,
                kSecValueData to cfValue
            ) { SecItemAdd(it, null) }
            status.checkError()
        }

    private inline fun removeKeychainItem(key: String): Unit = cfRetain(key) { (cfKey) ->
        val status = keyChainOperation(
            kSecAttrAccount to cfKey,
        ) { SecItemDelete(it) }
        status.checkError(errSecItemNotFound)
    }

    private inline fun updateKeychainItem(key: String, value: NSData?): Unit =
        cfRetain(key, value) { (cfKey, cfValue) ->
            val status = keyChainOperation(
                kSecAttrAccount to cfKey,
                kSecReturnData to kCFBooleanFalse
            ) { SecItemUpdate(it, cfDictionaryOf(kSecValueData to cfValue)) }
            status.checkError()
        }

    private inline fun getKeychainItem(key: String): NSData? = cfRetain(key) { (cfKey) ->
        val cfValue = alloc<CFTypeRefVar>()
        val status = keyChainOperation(
            kSecAttrAccount to cfKey,
            kSecReturnData to kCFBooleanTrue,
            kSecMatchLimit to kSecMatchLimitOne
        ) { SecItemCopyMatching(it, cfValue.ptr) }
        status.checkError(errSecItemNotFound)
        if (status == errSecItemNotFound) {
            return@cfRetain null
        }
        CFBridgingRelease(cfValue.value) as? NSData
    }

    private inline fun hasKeychainItem(key: String): Boolean = cfRetain(key) { (cfKey) ->
        val status = keyChainOperation(
            kSecAttrAccount to cfKey,
            kSecMatchLimit to kSecMatchLimitOne
        ) { SecItemCopyMatching(it, null) }

        status != errSecItemNotFound
    }

    private inline fun MemScope.keyChainOperation(
        vararg input: Pair<CFStringRef?, CFTypeRef?>,
        operation: (query: CFDictionaryRef?) -> OSStatus,
    ): OSStatus {
        val query = cfDictionaryOf(defaultProperties + mapOf(*input))
        return operation(query)
    }

    private inline fun OSStatus.checkError(vararg expectedErrors: OSStatus) {
        if (this != 0 && this !in expectedErrors) {
            val cfMessage = SecCopyErrorMessageString(this, null)
            val nsMessage = CFBridgingRelease(cfMessage) as? NSString
            val message = nsMessage?.toKString() ?: "Unknown error"
            error("Keychain error $this: $message")
        }
    }
}

internal inline fun MemScope.cfDictionaryOf(vararg items: Pair<CFStringRef?, CFTypeRef?>): CFDictionaryRef? =
    cfDictionaryOf(mapOf(*items))

internal inline fun MemScope.cfDictionaryOf(map: Map<CFStringRef?, CFTypeRef?>): CFDictionaryRef? {
    val size = map.size
    val keys = allocArrayOf(*map.keys.toTypedArray())
    val values = allocArrayOf(*map.values.toTypedArray())
    return CFDictionaryCreate(
        kCFAllocatorDefault,
        keys.reinterpret(),
        values.reinterpret(),
        size.convert(),
        null,
        null
    )
}

// Turn casts into dot calls for better readability
@Suppress("CAST_NEVER_SUCCEEDS")
internal inline fun String.toNSString() = this as NSString

@Suppress("CAST_NEVER_SUCCEEDS")
internal inline fun NSString.toKString() = this as String

internal inline fun <T> cfRetain(vararg values: Any?, block: MemScope.(Array<CFTypeRef?>) -> T): T =
    memScoped {
        val cfValues = Array(values.size) { i -> CFBridgingRetain(values[i]) }
        return try {
            block(cfValues)
        } finally {
            cfValues.forEach { CFBridgingRelease(it) }
        }
    }
