package org.example.sample.core

import java.time.Instant

data class ErrorResponse(
    val timestamp: Long = Instant.now().toEpochMilli(),
    val code: Int,
    val error: String,
    val message: String?
)