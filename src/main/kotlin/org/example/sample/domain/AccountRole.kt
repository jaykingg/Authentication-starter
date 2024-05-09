package org.example.sample.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class AccountRole(val label: String) {
    @field: JsonProperty("user")
    USER("사용자"),

    @field: JsonProperty("admin")
    ADMIN("관리자");

    fun getGrantAuthorities(): List<SimpleGrantedAuthority> {
        return when (this) {
            USER -> listOf(SimpleGrantedAuthority(Permission.CAN_READ.value))
            ADMIN -> listOf(
                SimpleGrantedAuthority(Permission.CAN_READ.value),
                SimpleGrantedAuthority(Permission.CAN_WRITE.value),
                SimpleGrantedAuthority(Permission.CAN_DELETE.value)
            )
        }
    }
}