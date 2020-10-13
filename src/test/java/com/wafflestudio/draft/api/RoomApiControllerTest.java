package com.wafflestudio.draft.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// FIXME: This test is depending on DataLoader and other logics, which is not desirable as genuine unit test.
// @ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getRoomTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/room/{roomId}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']", is(1)))
                .andExpect(jsonPath("$['name']", is("TEST_ROOM_1")))
                .andExpect(jsonPath("$['participants']['team1'][0]['id']", is(1)))
                .andExpect(jsonPath("$['participants']['team2']", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void getRoomsTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/room/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[4].id", is(5)))
                .andExpect(jsonPath("$[4].name", is("TEST_ROOM_5")))
                .andExpect(jsonPath("$[4]['participants']['team1'][0]['id']", is(1)));

        this.mockMvc.perform(get("/api/v1/room/")
                .param("name", "TEST_ROOM_3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].name", is("TEST_ROOM_3")));

        this.mockMvc.perform(get("/api/v1/room/")
                .param("courtId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "TEST_ROOM_4");
        params.add("courtId", "1");
        this.mockMvc.perform(get("/api/v1/room/")
                .params(params).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("TEST_ROOM_4")));
    }
}
