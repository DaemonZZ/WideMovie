package com.daemonz.base_sdk.model

import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmField

data class UpdateData(
    val version: String = "",
    @field:JvmField
    val isOptional: Boolean = false,
    val time: LocalDate? = null,
)