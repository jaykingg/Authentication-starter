package org.example.sample.view

import org.example.sample.domain.AccountRole

data class AccountView(
    val id: Long,
    val name: String,
    val role: AccountRole
)