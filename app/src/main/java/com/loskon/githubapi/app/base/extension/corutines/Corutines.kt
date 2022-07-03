package com.loskon.githubapi.app.base.extension.corutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun CoroutineScope.launchDelay(time: Long, block: () -> Unit): Job {
    return launch {
        delay(time)
        block()
    }
}