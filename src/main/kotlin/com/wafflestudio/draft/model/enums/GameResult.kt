package com.wafflestudio.draft.model.enums

enum class GameResult(private val value: String) {
    WIN("win"), LOSE("lose"), DRAW("draw");

    fun getKey(): String {
        return name
    }

    fun getValue(): String {
        return value
    }

}