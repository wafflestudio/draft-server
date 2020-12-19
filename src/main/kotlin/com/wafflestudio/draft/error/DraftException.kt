package com.wafflestudio.draft.error

import java.lang.RuntimeException

open class DraftException(val errorType: ErrorType) : RuntimeException(errorType.name)

class RoomNotFoundException : DraftException(ErrorType.ROOM_NOT_FOUND)
