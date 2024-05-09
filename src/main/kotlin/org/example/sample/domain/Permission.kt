package org.example.sample.domain

enum class Permission(val value: String) {
    CAN_READ("읽기 권한"),
    CAN_WRITE("쓰기 권한"),
    CAN_DELETE("삭제 권한")
}