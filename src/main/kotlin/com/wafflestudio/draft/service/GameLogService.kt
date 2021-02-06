package com.wafflestudio.draft.service

import com.wafflestudio.draft.dto.GameDTO
import com.wafflestudio.draft.error.RoomNotFoundException
import com.wafflestudio.draft.model.Game
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.UserGameLog
import com.wafflestudio.draft.model.enums.GameResult
import com.wafflestudio.draft.model.enums.Team
import com.wafflestudio.draft.repository.GameRepository
import com.wafflestudio.draft.repository.ParticipantRepository
import com.wafflestudio.draft.repository.RoomRepository
import com.wafflestudio.draft.repository.UserGameLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GameLogService(
        private val roomRepository: RoomRepository,
        private val gameRepository: GameRepository,
        private val userGameLogRepository: UserGameLogRepository,
        private val participantRepository: ParticipantRepository
) {
    fun setGameResult(roomId: Long, request: GameDTO.CreateRequest): GameDTO.Response {
        val room: Room = roomRepository.findRoomById(roomId) ?: throw RoomNotFoundException()
        val winningTeam: Team =
                when {
                    request.teamAScore > request.teamBScore -> Team.A
                    request.teamAScore < request.teamBScore -> Team.B
                    else -> Team.NONE
                }
        val newGame = Game(
                elapsedTime = request.elapsedTime,
                teamAScore = request.teamAScore,
                teamBScore = request.teamBScore,
                winningTeam = winningTeam
        )
        gameRepository.save(newGame)
        val participants = participantRepository.getAllByRoom(room)
        participants.forEach {
            val result = when (winningTeam) {
                Team.NONE -> GameResult.DRAW
                it.team -> GameResult.WIN
                else -> GameResult.LOSE
            }
            //FIXME: Score should be changed
            val userLog = UserGameLog(user = it.user, game = newGame, result = result, score = 0, team = it.team)
            userGameLogRepository.save(userLog)
        }
        return newGame.toResponse()
    }
}