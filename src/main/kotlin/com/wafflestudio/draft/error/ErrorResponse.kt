package com.wafflestudio.draft.error

data class ErrorResponse(
        val errorCode: Int,
        val errorMessage: String?,
        val validation: Map<String, String>?
)
