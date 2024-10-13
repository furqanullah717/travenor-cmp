package com.codewithfk.travenor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform