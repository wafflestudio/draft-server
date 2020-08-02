package com.wafflestudio.draft.service;

import com.wafflestudio.draft.api.UserApiController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserAuthTest {

    // FIXME: error related with default constructor
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void before() {
//        mockMvc = MockMvcBuilders.standaloneSetup(UserApiController.class)
//                .build();
//    }
//
//    @Test
//    public void unAuthTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/me"))
//                .andExpect(MockMvcResultMatchers.status().isNetworkAuthenticationRequired())
//                .andDo(MockMvcResultHandlers.print());
//    }
}

