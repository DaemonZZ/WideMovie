package com.daemonz.base_sdk.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField
@Serializable
data class UpdateData(
    val version: String = "",
    @field:JvmField
    val isOptional: Boolean = false,
    val time: LocalDate? = null,
)