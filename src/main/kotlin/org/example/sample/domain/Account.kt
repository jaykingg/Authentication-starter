package org.example.sample.domain

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.example.sample.view.AccountView
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table
data class Account(

    @field: Schema(description = "Account Id")
    @field: Id
    val id: Long? = null,

    @field: Schema(description = "사용자 명")
    @field: NotBlank
    val name: String,

    @field: Schema(description = "비밀번호")
    @field: NotBlank
    val password: String,

    @field: Schema(description = "권한 타입")
    @field: Valid
    val role: AccountRole,

    @field: Schema(description = "나이")
    @field: NotEmpty
    @field: Min(0)
    val age: Int,

    @field: Schema(description = "생성일자 - UTC")
    @field: CreatedDate
    val createAt: Instant? = null,

    @field: Schema(description = "수정일자 - UTC")
    @field: LastModifiedDate
    val updateAt: Instant? = null
)

fun Account.toView(): AccountView = AccountView(
    id = id!!,
    name = name,
    role = role
)