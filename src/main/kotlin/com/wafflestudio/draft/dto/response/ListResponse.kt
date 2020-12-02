package com.wafflestudio.draft.dto.response

data class ListResponse<T>(
        val results: List<T>? = listOf(),
        var count: Int = 0
)
{
    constructor(results: List<T>?) : this(
            results,
            results?.size ?: 0
    )
}
