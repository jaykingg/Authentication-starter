package org.example.sample.config

import com.appmattus.kotlinfixture.kotlinFixture
import org.example.sample.domain.Account

val fixture = kotlinFixture()
val accountFixture = fixture<Account> {
    property(Account::name) { faker.name.name() }
    property(Account::age) { faker.random.nextInt(max = 100, min = 1) }
}

