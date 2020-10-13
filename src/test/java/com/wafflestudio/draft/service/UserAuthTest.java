package com.wafflestudio.draft.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

