package com.loskon.githubapi.network.retrofit.data.exception

import java.io.IOException

class NoSuccessfulException(private val text: String) : IOException() {
    override val message: String get() = text
}