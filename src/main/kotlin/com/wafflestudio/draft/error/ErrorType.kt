package com.wafflestudio.draft.error;

import org.springframework.http.HttpStatus

enum class ErrorType (
    val code: Int,
    val httpStatus: HttpStatus
) {
    USER_NOT_FOUND(10001, HttpStatus.NOT_FOUND),
    ROOM_NOT_FOUND(10002, HttpStatus.NOT_FOUND),
    COURT_NOT_FOUND(10003, HttpStatus.NOT_FOUND),
    REGION_NOT_FOUND(10004, HttpStatus.NOT_FOUND),

    ROOM_IS_NOT_WAITING(10101, HttpStatus.BAD_REQUEST),
    ALREADY_PARTICIPATING_ROOM(10102, HttpStatus.CONFLICT),
    ROOM_IS_FULL(10103, HttpStatus.CONFLICT),
    CONCURRENTLY_PARTICIPATING_OTHER_ROOM(10104, HttpStatus.CONFLICT),

    WRONG_ROOM_TIME(11001, HttpStatus.BAD_REQUEST)
}
