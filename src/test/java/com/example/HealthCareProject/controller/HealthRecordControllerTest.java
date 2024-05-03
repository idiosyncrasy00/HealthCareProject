package com.example.HealthCareProject.controller;

import com.example.HealthCareProject.dto.UserDataDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.example.HealthCareProject.config.JsonString.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HealthRecordControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @Test
    public void testViewHealthRecordByDoctor() throws Exception {

        mockMvc.perform(get("http://localhost:8081/api/health/view/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserDataDTO.LoginRequest("testuser1", "testpass1"))))
                        //.content(asJsonString("abcd")))
                .andExpect(status().isOk());

    }
}
