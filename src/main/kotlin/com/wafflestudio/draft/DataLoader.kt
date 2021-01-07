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
import kotlin.random.Random

@Profile("!test")
@Component
class DataLoader(val userRepository: UserRepository, val regionRepository: RegionRepository, val courtRepository: CourtRepository, val deviceRepository: DeviceRepository, val roomService: RoomService, val participantService: ParticipantService) : ApplicationRunner {
    val gf = GeometryFactory()

    private fun createCourt(name: String, region: Region, x: Double, y: Double): Court {
        val point = gf.createPoint(Coordinate(x, y))
        point.srid = 4326

        val court = Court(region = region, name = name, capacity = 10, location = point)
        courtRepository.save(court)
        return court
    }

    override fun run(args: ApplicationArguments) {
        val gsf = GeometricShapeFactory()
        val testPolygon = gf.createMultiPolygon(arrayOf<Polygon>(gsf.createCircle(), gsf.createRectangle()))
        testPolygon.srid = 5179
        val testRegion = Region(null, 99999999, null, null, "테스트", "TEST_REGION", testPolygon)
        regionRepository.save(testRegion)

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
        passwordUser.region = testRegion
        passwordUser.password = BCryptPasswordEncoder().encode("testpassword")
        userRepository.save(passwordUser)

        val testDevice = Device("euSJRfcqJTc:APA91bFIUxmYZX68KWUZSZPW0sMhCl1tJKdH8L-lvhUv71DbePYmA8RI-QrVGGAqBzoxfklsl-i7NdgazQAHGQXlFkCnaCIpP3B_oDCCkpTR_HxxUVeNoG8_DeNODrwxEMfardoz_4Ym")
        testDevice.user = passwordUser
        deviceRepository.save(testDevice)

        val locations = ArrayList<Pair<Double, Double>>()
        locations.add(Pair(126.904905, 37.5186202)) // 경도, 위도
        locations.add(Pair(127.04989734997379, 37.50332207088319))
        locations.add(Pair(126.97803007590922, 37.565580714076226))
        locations.add(Pair(126.98369760592783, 37.52841542691337))
        locations.add(Pair(127.10640196443349, 37.51308761822849))
        locations.add(Pair(126.95430218151192, 37.46008435548953))
        locations.add(Pair(126.95261385156185, 37.44881201782945))
        locations.add(Pair(126.99770484484361, 37.48156425092955))
        locations.add(Pair(126.86448115487498, 37.527005318002345))
        locations.add(Pair(127.00213973614696, 37.582007874759064))
        locations.add(Pair(127.01672406026478, 37.59281862238072))
        locations.add(Pair(127.0551103234095, 37.61763371872759))
        locations.add(Pair(127.0612242878372, 37.65603068820006))
        locations.add(Pair(126.92953647355564, 37.611408729611895))
        locations.add(Pair(126.94183736252563, 37.47166588238839))

        for (i in 1..15) {
            val room = Room()
            room.owner = oauth2User
            room.court = createCourt("TEST_COURT_$i", testRegion, locations[i-1].first, locations[i-1].second)
            room.name = "TEST_ROOM_$i"
            room.startTime = LocalDateTime.now().plusHours(Random.nextLong(0, 96)).plusMinutes(Random.nextLong(0, 60))
            room.endTime = room.startTime!!.plusMinutes(Random.nextLong(30, 90))
            roomService.save(room)
            participantService.addParticipants(room, oauth2User)
        }
    }
}
