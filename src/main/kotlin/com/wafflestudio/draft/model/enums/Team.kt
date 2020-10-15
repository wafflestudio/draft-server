package com.wafflestudio.draft.model.enums

enum class Team(val value: String) {
    A("a"), B("b"), NONE("none");

    fun getKey(): String {
        return name
    }

}