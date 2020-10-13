package com.wafflestudio.draft.dto.response

class ParticipantsResponse(var team1: List<UserInformationResponse?>, var team2: List<UserInformationResponse?>) {

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is ParticipantsResponse) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$team1`: Any = team1
        val `other$team1`: Any = other.team1
        if (if (`this$team1` == null) `other$team1` != null else `this$team1` != `other$team1`) return false
        val `this$team2`: Any = team2
        val `other$team2`: Any = other.team2
        return if (if (`this$team2` == null) `other$team2` != null else `this$team2` != `other$team2`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is ParticipantsResponse
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$team1`: Any = team1
        result = result * PRIME + (`$team1`?.hashCode() ?: 43)
        val `$team2`: Any = team2
        result = result * PRIME + (`$team2`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "ParticipantsResponse(team1=" + team1 + ", team2=" + team2 + ")"
    }

}