package org.example.sample.domain

import com.fasterxml.jackson.annotation.JsonProperty

enum class AccountRole(val label: String) {
    @field: JsonProperty("user")
    USER("사용자"),

    @field: JsonProperty("admin")
    ADMIN("관리자")
}