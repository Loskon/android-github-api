package com.loskon.githubapi.network.retrofit.exception

import java.io.IOException

class EmptyCacheException : IOException() {
    // TODO
    override val message: String get() = "No internet connection"
}