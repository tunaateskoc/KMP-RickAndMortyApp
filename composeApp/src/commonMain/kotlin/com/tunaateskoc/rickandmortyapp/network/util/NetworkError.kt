package com.tunaateskoc.rickandmortyapp.network.util

enum class NetworkError {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER,
    SERIALIZATION,
    UNKNOWN;
}