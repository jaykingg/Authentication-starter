package org.example.sample.controller.authenticationController

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.example.sample.core.ErrorResponse
import org.example.sample.core.JwtUtil
import org.example.sample.domain.AccountRole
import org.example.sample.payload.AccountSavePayload
import org.example.sample.repository.AccountRepository
import org.example.sample.view.AccountView
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class RegisterAccountIT(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val webTestClient: WebTestClient,
    private val jwtUtil: JwtUtil
) : BehaviorSpec({
    val endpoint = "/api/auth/register"
    val request = webTestClient.post().uri(endpoint)

    Given("사용자 등록") {
        When("payload 가 유효하지 않을 때") {
            val payload = AccountSavePayload(
                name = "testUser",
                password = "1234",
                role = AccountRole.ADMIN,
                age = -100
            )

            Then("Response Bad Request") {
                request
                    .bodyValue(payload)
                    .exchange()
                    .expectStatus().isBadRequest
                    .expectBody<ErrorResponse>()
                    .returnResult()
                    .responseBody!!
                    .should {
                        it.code shouldBe 400
                    }
            }
        }
        When("payload 가 유효할 때") {
            val payload = AccountSavePayload(
                name = "testUser",
                password = "1234",
                role = AccountRole.ADMIN,
                age = 30
            )

            Then("Response 200 - AccountView") {
                request
                    .bodyValue(payload)
                    .exchange()
                    .expectStatus().isOk
                    .expectBody<AccountView>()
                    .returnResult()
                    .responseBody!!
                    .should {
                        it.name shouldBe payload.name
                        it.role shouldBe payload.role
                    }
            }
        }
    }
})