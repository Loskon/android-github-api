package com.loskon.base.extension.coroutines

import kotlinx.coroutines.Job

/**
 * isActive - returns true when this job is active -
 * it was already started and has not completed nor was cancelled yet.
 */
fun Job.onStart(block: () -> Unit): Job {
    if (isActive) block()
    return this
}