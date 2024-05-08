package org.example.sample.service

import org.example.sample.core.JwtUtil
import org.example.sample.domain.Account
import org.example.sample.payload.AccountSavePayload
import org.example.sample.payload.AccountTokenPayload
import org.example.sample.repository.AccountRepository
import org.example.sample.view.AccountTokenView
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {
    @Transactional
    suspend fun registerAccount(payload: AccountSavePayload): Account {
        if (accountRepository.existsAccountByName(payload.name)) throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "이메일이 이미 사용 중입니다."
        )
        return accountRepository.save(
            Account(
                name = payload.name,
                password = passwordEncoder.encode(payload.password),
                role = payload.role,
                age = payload.age
            )
        )
    }

    suspend fun getToken(payload: AccountTokenPayload): AccountTokenView {
        val account = accountRepository.findByName(payload.name) ?: throw ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "로그인 정보가 잘못되었습니다."
        )

        if (!passwordEncoder.matches(payload.password, account.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.")
        }

        val token = jwtUtil.generateToken(payload.name)
        return AccountTokenView(token = token)
    }
}