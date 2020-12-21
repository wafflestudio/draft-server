package com.wafflestudio.draft.error;

import org.springframework.http.HttpStatus

enum class ErrorType (
    val code: Int,
    val httpStatus: HttpStatus
) {
    ROOM_NOT_FOUND(10001, HttpStatus.NOT_FOUND),
}
