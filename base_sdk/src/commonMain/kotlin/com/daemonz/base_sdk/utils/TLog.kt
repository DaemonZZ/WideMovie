package com.daemonz.base_sdk.utils

expect object TLog {
    fun d(tag: String?, message: String)
    fun e(tag: String?, message: String, throwable: Throwable? = null)
}