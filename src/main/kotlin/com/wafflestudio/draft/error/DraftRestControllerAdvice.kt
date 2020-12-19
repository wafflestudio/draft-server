package com.wafflestudio.draft.error

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class DraftRestControllerAdvice() {
    @ExceptionHandler(DraftException::class)
    fun handleDraftException(
            e: DraftException,
            request: HttpServletRequest,
            response: HttpServletResponse
    ): ErrorResponse {
        val errorType = e.errorType
        response.status = errorType.httpStatus.value()
        return ErrorResponse(errorType.code, errorType.name.toLowerCase())
    }
}
