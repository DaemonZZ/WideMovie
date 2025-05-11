package com.mp.widemovie.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


actual fun provideIoCoroutineScope(): CoroutineScope =
    CoroutineScope((Dispatchers.IO + Job()))