package org.example.sample.payload

import jakarta.validation.constraints.NotBlank

data class AccountTokenPayload(
    @field: NotBlank
    val name: String,

    @field: NotBlank
    val password: String
)