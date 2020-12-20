package com.wafflestudio.draft.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// FIXME: This test is depending on DataLoader and other logics, which is not desirable as genuine unit test.
// @ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourtApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getCourtTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/court/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['results']", hasSize(15)))
                .andExpect(jsonPath("$['count']", is(15)))
                .andExpect(jsonPath("$['results'][0].id", is(1)))
                .andExpect(jsonPath("$['results'][0].name", is("TEST_COURT_1")))
                .andExpect(jsonPath("$['results'][0].regionId", is(1)))
                .andExpect(jsonPath("$['results'][0]['location'].lat", is(37.5186202)))
                .andExpect(jsonPath("$['results'][0]['location'].lng", is(126.904905)));
    }
}
