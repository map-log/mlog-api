package com.mlog.travel.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TravelControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void travel_테스트_예시() throws Exception {
//
//        MvcResult result = mockMvc.perform(get("/travel")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
//        assertThat(response.get("success"))
//                .isEqualTo(true);
//        assertThat("hello travel")
//                .isEqualTo(response.get("response"));
//        assertThat(response.get("error"))
//                .isEqualTo(null);
//    }

}