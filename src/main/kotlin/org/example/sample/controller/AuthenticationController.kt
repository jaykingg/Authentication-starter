package org.example.sample.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.example.sample.domain.toView
import org.example.sample.payload.AccountSavePayload
import org.example.sample.payload.AccountTokenPayload
import org.example.sample.service.AccountService
import org.example.sample.view.AccountTokenView
import org.example.sample.view.AccountView
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "JWT API", description = "JWT API for Sample")
@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val accountService: AccountService
) {

    @PostMapping("/register")
    suspend fun registerAccount(
        @Valid @RequestBody payload: AccountSavePayload
    ): AccountView = accountService.registerAccount(payload).toView()

    @PostMapping("/token")
    suspend fun authenticate(
        @Valid @RequestBody payload: AccountTokenPayload
    ): AccountTokenView = accountService.getToken(payload)
}