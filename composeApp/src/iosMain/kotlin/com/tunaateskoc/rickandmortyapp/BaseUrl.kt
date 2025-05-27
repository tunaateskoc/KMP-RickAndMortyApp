package com.tunaateskoc.rickandmortyapp

import platform.Foundation.*

actual fun getBaseUrl(): String {
    val path = NSBundle.mainBundle.pathForResource("Secrets", "plist") ?: return ""
    val dict = NSDictionary.dictionaryWithContentsOfFile(path) as? NSDictionary
    return dict?.objectForKey("BASE_URL") as? String ?: ""
}