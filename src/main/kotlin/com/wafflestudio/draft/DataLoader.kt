package com.wafflestudio.draft

import com.wafflestudio.draft.model.*
import com.wafflestudio.draft.repository.CourtRepository
import com.wafflestudio.draft.repository.DeviceRepository
import com.wafflestudio.draft.repository.RegionRepository
import com.wafflestudio.draft.repository.UserRepository
import com.wafflestudio.draft.service.ParticipantService
import com.wafflestudio.draft.service.RoomService
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon
import org.locationtech.jts.util.GeometricShapeFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Profile("!test")
@Component
class DataLoader(val userRepository: UserRepository, val regionRepository: RegionRepository, val courtRepository: CourtRepository, val deviceRepository: DeviceRepository, val roomService: RoomService, val participantService: ParticipantService) : ApplicationRunner {
    val gf = GeometryFactory()
    override fun run(args: ApplicationArguments) {
        val point = gf.createPoint(Coordinate(37.5186202, 126.904905))
        point.srid = 4326

//        val testRegion = regionRepository.findRegionByPolygonContainsPoint(37.5186202,126.904905)
        val gsf = GeometricShapeFactory()
        val testPolygon = gf.createMultiPolygon(arrayOf<Polygon>(gsf.createCircle(), gsf.createRectangle()))
        testPolygon.srid = 5179
        val testRegion = Region(null, 99999999, null, null, "테스트", testPolygon, "TEST_REGION")
        regionRepository.save(testRegion)

        val testCourt = Court(region = testRegion, name = "TEST_COURT", capacity = 10, location = point)
        // FIXME: how to set location with Point?
        courtRepository.save(testCourt)

        val oauth2User = User(username = "OAUTH2_TESTUSER", email = "authuser@test.com")
        oauth2User.addRole("TEST_API")
        oauth2User.addRole("ROLE_USER")
        oauth2User.addRole("USER")
        oauth2User.region = testRegion
        userRepository.save(oauth2User)

        val passwordUser = User("PASSWORD_TESTUSER", "passworduser@test.com")
        passwordUser.addRole("TEST_API")
        passwordUser.addRole("ROLE_USER")
        passwordUser.addRole("USER")
//        passwordUser.region = testRegion
        passwordUser.password = BCryptPasswordEncoder().encode("testpassword")
        userRepository.save(passwordUser)

        val testDevice = Device("euSJRfcqJTc:APA91bFIUxmYZX68KWUZSZPW0sMhCl1tJKdH8L-lvhUv71DbePYmA8RI-QrVGGAqBzoxfklsl-i7NdgazQAHGQXlFkCnaCIpP3B_oDCCkpTR_HxxUVeNoG8_DeNODrwxEMfardoz_4Ym")
        testDevice.user = passwordUser
        deviceRepository.save(testDevice)

        for (i in 1..5) {
            val room = Room()
            room.owner = oauth2User
            room.court = testCourt
            room.name = "TEST_ROOM_$i"
            room.startTime = LocalDateTime.now()
            room.endTime = LocalDateTime.now().plusHours(1)
            roomService.save(room)
            participantService.addParticipants(room, oauth2User)
        }
    }
}
