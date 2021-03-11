package com.wafflestudio.draft.dto.response

import org.springframework.data.domain.Page

data class PageResponse<T>(
        var results: List<T>? = mutableListOf(),
        var count: Int = 0,
        var next: Int? = null
) {
    constructor(results: Page<T>?) : this() {
        this.results = results?.content
        this.count = results?.numberOfElements ?: 0
        if (results?.isLast == false) {
            this.next = results.number + 1
        }
    }
}
