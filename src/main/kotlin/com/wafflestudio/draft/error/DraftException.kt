package com.wafflestudio.draft.error

import java.lang.RuntimeException

open class DraftException(val errorType: ErrorType) : RuntimeException(errorType.name)

class RoomNotFoundException : DraftException(ErrorType.ROOM_NOT_FOUND)
class CourtNotFoundException : DraftException(ErrorType.COURT_NOT_FOUND)

class RoomIsNotWaitingException : DraftException(ErrorType.ROOM_IS_NOT_WAITING)
class AlreadyParticipatingRoomException : DraftException(ErrorType.ALREADY_PARTICIPATING_ROOM)
class RoomIsFullException : DraftException(ErrorType.ROOM_IS_FULL)
class ConcurrentlyParticipatingOtherRoomException : DraftException(ErrorType.CONCURRENTLY_PARTICIPATING_OTHER_ROOM)

class WrongRoomTimeException : DraftException(ErrorType.WRONG_ROOM_TIME)
