package org.example.sample.payload

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.example.sample.domain.AccountRole
import org.hibernate.validator.constraints.Length

data class AccountSavePayload(
    @field: NotBlank
    @field: Length(max = 30)
    val name: String,

    @field: NotBlank
    val password: String,

    @field: Valid
    val role: AccountRole,

    @field: Min(0)
    val age: Int
)