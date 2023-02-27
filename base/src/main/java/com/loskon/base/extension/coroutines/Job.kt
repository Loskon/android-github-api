package com.loskon.base.extension.coroutines

import kotlinx.coroutines.Job

fun Job.onStart(start: () -> Unit): Job {
    if (isActive) start()
    return this
}