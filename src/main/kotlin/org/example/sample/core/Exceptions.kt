package org.example.sample.core

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class BadRequestException : ResponseStatusException(HttpStatus.BAD_REQUEST)
class NotFoundException : ResponseStatusException(HttpStatus.NOT_FOUND)
class InternalServerException : ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)

class ForbiddenException(message: String?) : RuntimeException(message)
class UnauthorizedException(message: String?) : RuntimeException(message)
