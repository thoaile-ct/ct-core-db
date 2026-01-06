package com.chotot.vn.core_db

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform