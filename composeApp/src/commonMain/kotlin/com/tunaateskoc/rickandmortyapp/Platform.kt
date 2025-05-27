package com.tunaateskoc.rickandmortyapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform