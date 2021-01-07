package com.wafflestudio.draft.service

import com.wafflestudio.draft.model.Court
import com.wafflestudio.draft.model.Region
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.repository.CourtRepository
import com.wafflestudio.draft.repository.RegionRepository
import com.wafflestudio.draft.repository.RoomRepository
import com.wafflestudio.draft.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon
import org.locationtech.jts.util.GeometricShapeFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class RoomServiceTest(
        private val roomService: RoomService,
        private val roomRepository: RoomRepository,
        private val userRepository: UserRepository,
        private val regionRepository: RegionRepository,
        private val courtRepository: CourtRepository) {
    @Test
    @Throws(Exception::class)
    fun create() {
        // given
        val room = Room()
        val user = User()
        val gf = GeometryFactory()
        val gsf = GeometricShapeFactory()
        val testPolygon = gf.createMultiPolygon(arrayOf<Polygon>(gsf.createCircle(), gsf.createRectangle()))
        testPolygon.srid = 5179
        val region = Region(null, 99999999, null, null, "테스트", "TEST_REGION", testPolygon)
        regionRepository.save(region)
        user.username = "TEST_USER"
        user.email = "user@test.com"
        userRepository.save(user)
        room.owner = user
        val location = gf.createPoint(Coordinate(37.5186202, 126.904905))
        location.srid = 4326
        val court = Court()
        court.region = region
        court.location = location
        courtRepository.save(court)
        room.court = court

        // when
        val savedId = roomService.save(room)

        // then
        Assertions.assertEquals(room, roomRepository.findById(savedId).get())
    }
}
