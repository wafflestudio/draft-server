package com.wafflestudio.draft.dto.response

data class PageResponse<T>(
        var results: List<T>? = mutableListOf(),
        var count: Int = 0,
        var next: Int = 2
) {
    constructor(results: List<T>?,next: Int) : this(
            results,
            results?.size ?: 0,
            next
    )
}
