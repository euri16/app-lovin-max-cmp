package dev.euryperez.kmp.applovinmax

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform