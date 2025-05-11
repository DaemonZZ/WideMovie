package com.daemonz.base_sdk.utils


actual object TLog {
    actual fun d(tag: String?, message: String) {
        println("DEBUG [$tag]: $message")
    }

    actual fun e(tag: String?, message: String, throwable: Throwable?) {
        println("ERROR [$tag]: $message - ${throwable?.message}")
    }
}