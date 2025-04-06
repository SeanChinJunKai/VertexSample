package io.github.seanchinjunkai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform