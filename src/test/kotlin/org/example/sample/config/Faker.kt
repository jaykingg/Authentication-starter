package org.example.sample.config

import io.github.serpro69.kfaker.faker

val faker = faker {
    fakerConfig {
        locale = "ko"
    }
}