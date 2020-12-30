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
class CourtApiControllerTest(private val mockMvc: MockMvc) {
    @get:Throws(Exception::class)
    @get:WithMockUser
    @get:Test
    val courtTest: Unit
        get() {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/court/").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results']", IsCollectionWithSize.hasSize<Any>(15)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['count']", CoreMatchers.`is`(15)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0].id", CoreMatchers.`is`(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0].name", CoreMatchers.`is`("TEST_COURT_1")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0].regionId", CoreMatchers.`is`(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0]['location'].lat", CoreMatchers.`is`(37.5186202)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$['results'][0]['location'].lng", CoreMatchers.`is`(126.904905)))
        }
}