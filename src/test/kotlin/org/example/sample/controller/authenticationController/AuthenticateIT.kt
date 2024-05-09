package org.example.sample.controller.authenticationController

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.example.sample.core.ErrorResponse
import org.example.sample.core.JwtUtil
import org.example.sample.domain.Account
import org.example.sample.domain.AccountRole
import org.example.sample.payload.AccountTokenPayload
import org.example.sample.repository.AccountRepository
import org.example.sample.view.AccountTokenView
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class AuthenticateIT(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val webTestClient: WebTestClient,
    private val jwtUtil: JwtUtil
) : BehaviorSpec({
    val endpoint = "/api/auth/token"
    val request = webTestClient.post().uri(endpoint)


    beforeSpec {
        accountRepository.save(
            Account(
                name = "testUser",
                password = passwordEncoder.encode("1234"),
                role = AccountRole.ADMIN,
                age = 30
            )
        )
    }

    afterSpec {
        accountRepository.deleteAll()
    }

    Given("JWT 발급") {
        When("사용자 정보가 유효하지 않을 때") {
            val payload = AccountTokenPayload(
                name = "testUser",
                password = "4321"
            )

            Then("Response 401 - UnAuthorized") {
                request.bodyValue(payload)
                    .exchange()
                    .expectStatus().isBadRequest
                    .expectBody<ErrorResponse>()
                    .returnResult()
                    .responseBody!!
                    .should {
                        it.code shouldBe 401
                    }
            }
        }

        When("사용자 정보가 유효할 때") {
            val payload = AccountTokenPayload(
                name = "testUser",
                password = "1234"
            )

            Then("Response 200 - Token") {
                val result = request.bodyValue(payload)
                    .exchange()
                    .expectStatus().isOk
                    .expectBody<AccountTokenView>()
                    .returnResult()
                    .responseBody!!

                jwtUtil.validateToken(result.token, payload.name) shouldBe true
            }
        }

    }


})