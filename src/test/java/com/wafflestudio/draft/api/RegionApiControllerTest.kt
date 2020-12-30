package com.wafflestudio.draft.api

import org.hamcrest.CoreMatchers
import org.hamcrest.collection.IsCollectionWithSize
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

// FIXME: This test is depending on DataLoader and other logics, which is not desirable as genuine unit test.
// @ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class RegionApiControllerTest(
        private val mockMvc: MockMvc
) {

    @get:Throws(Exception::class)
    @get:WithMockUser
    @get:Test
    val regionRoomTest: Unit
        get() {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/region/room/").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0].id", CoreMatchers.`is`(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0].name", CoreMatchers.`is`("TEST_REGION")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0]['rooms']", IsCollectionWithSize.hasSize<Any>(15)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0]['rooms'][4].name", CoreMatchers.`is`("TEST_ROOM_5")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0]['rooms'][4]['participants']", IsCollectionWithSize.hasSize<Any>(1)))
        }
}