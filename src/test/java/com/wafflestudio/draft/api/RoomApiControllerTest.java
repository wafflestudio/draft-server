package com.wafflestudio.draft.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
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
                .andExpect(jsonPath("$['participants']", hasSize(1)))
                .andExpect(jsonPath("$['participants'][0]['id']", is(1)));
    }

    @Test
    @WithMockUser
    public void getRoomsTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/room/")
                .param("name", "TEST_ROOM_3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].name", is("TEST_ROOM_3")));

        this.mockMvc.perform(get("/api/v1/room/")
                .param("regionId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "TEST_ROOM_4");
        params.add("regionId", "1");
        this.mockMvc.perform(get("/api/v1/room/")
                .params(params).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("TEST_ROOM_4")));

        String oneMinuteBefore = LocalDateTime.now().minusMinutes(1).format(
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.mockMvc.perform(get("/api/v1/room/")
                .param("startTime", oneMinuteBefore).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        String oneMinuteAfter = LocalDateTime.now().plusMinutes(1).format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.mockMvc.perform(get("/api/v1/room/")
                .param("startTime", oneMinuteAfter).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        String oneDayAfter = LocalDateTime.now().plusDays(1).format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.mockMvc.perform(get("/api/v1/room/")
                .param("endTime", oneDayAfter).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        this.mockMvc.perform(get("/api/v1/room/")
                .param("endTime", oneMinuteAfter).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
