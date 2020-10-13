package com.wafflestudio.draft

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.wafflestudio.draft.model.*
import com.wafflestudio.draft.repository.CourtRepository
import com.wafflestudio.draft.repository.DeviceRepository
import com.wafflestudio.draft.repository.RegionRepository
import com.wafflestudio.draft.repository.UserRepository
import com.wafflestudio.draft.service.RoomService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DataLoader(val userRepository: UserRepository, val regionRepository: RegionRepository, val courtRepository: CourtRepository, val deviceRepository: DeviceRepository, val roomService: RoomService) : ApplicationRunner {
    val gf = GeometryFactory()
    override fun run(args: ApplicationArguments) {
        val point = gf.createPoint(Coordinate(2.0, 5.0))

        val testRegion = Region()
        testRegion.name = "TEST_REGION"
        regionRepository.save(testRegion)

        val testCourt = Court()
        testCourt.name = "TEST_COURT"
        testCourt.capacity = 10
        testCourt.region = testRegion
        // FIXME: how to set location with Point?
        // testCourt.setLocation(point);
        courtRepository.save(testCourt)

        val oauth2User = User(username="OAUTH2_TESTUSER", email="authuser@test.com")
        oauth2User.addRole("TEST_API")
        oauth2User.addRole("ROLE_USER")
        oauth2User.addRole("USER")
        oauth2User.region = testRegion
        userRepository.save(oauth2User)

        val passwordUser = User("PASSWORD_TESTUSER", "passworduser@test.com")
        passwordUser.addRole("TEST_API")
        passwordUser.addRole("ROLE_USER")
        passwordUser.addRole("USER")
        passwordUser.region = testRegion
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
        }
    }

}