package org.example.authenticationstarter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthenticationStarterApplication

fun main(args: Array<String>) {
    runApplication<AuthenticationStarterApplication>(*args)
}
