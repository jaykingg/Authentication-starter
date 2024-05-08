package org.example.sample.repository

import org.example.sample.domain.Account
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AccountRepository : CoroutineCrudRepository<Account, Long> {
    suspend fun existsAccountByName(name: String): Boolean
    suspend fun findByName(name: String): Account?
}