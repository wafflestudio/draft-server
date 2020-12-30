package com.wafflestudio.draft.api

import org.hamcrest.CoreMatchers
import org.hamcrest.collection.IsCollectionWithSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// FIXME: This test is depending on DataLoader and other logics, which is not desirable as genuine unit test.
// @ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class RoomApiControllerTest(
        private val mockMvc: MockMvc
) {

    @get:Throws(Exception::class)
    @get:WithMockUser
    @get:Test
    val roomTest: Unit
        get() {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/{roomId}", 1).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['id']", CoreMatchers.`is`(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['name']", CoreMatchers.`is`("TEST_ROOM_1")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['participants']", IsCollectionWithSize.hasSize<Any>(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['participants'][0]['id']", CoreMatchers.`is`(1)))
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/{roomId}", 100).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound)
        }

    @get:Throws(Exception::class)
    @get:WithMockUser
    @get:Test
    val roomsTest: Unit
        get() {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/")
                    .param("name", "TEST_ROOM_3").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0].id", CoreMatchers.`is`(3)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0].name", CoreMatchers.`is`("TEST_ROOM_3")))
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/")
                    .param("regionId", "1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(15)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['count']", CoreMatchers.`is`(15)))
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.add("name", "TEST_ROOM_4")
            params.add("regionId", "1")
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/")
                    .params(params).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0].name", CoreMatchers.`is`("TEST_ROOM_4")))
            val oneMinuteBefore = LocalDateTime.now().minusMinutes(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/")
                    .param("startTime", oneMinuteBefore).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(15)))
            val fiveDayAfter = LocalDateTime.now().plusDays(5).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/")
                    .param("startTime", fiveDayAfter).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(0)))
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/")
                    .param("endTime", fiveDayAfter).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(15)))
            val oneMinuteAfter = LocalDateTime.now().plusMinutes(1).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/")
                    .param("endTime", oneMinuteAfter).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(0)))
        }
}