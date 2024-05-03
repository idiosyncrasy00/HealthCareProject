//package com.example.HealthCareProject.controller;
//
//import com.example.HealthCareProject.HealthCareProjectApplication;
//import com.example.HealthCareProject.HealthCareProjectApplicationTests;
//import com.example.HealthCareProject.dto.UserDataDTO;
//import jakarta.inject.Inject;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static com.example.HealthCareProject.config.JsonString.asJsonString;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class SpringSecurityTest extends HealthCareProjectApplicationTests {
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .alwaysExpect(forwardedUrl(null))
//                .build();
//    }
//
//    @Test
//    public void testUserLogin() throws Exception {
//
//        mockMvc.perform(post("http://localhost:8081/api/auth/signin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(new UserDataDTO.LoginRequest("testuser1", "testpass1"))))
//
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    //@WithMockUser(username = "testuser1", password = "testpass1", roles = {"ADMIN"})
//    public void testUserLogin_Failed() throws Exception {
//        mockMvc.perform(post("http://localhost:8081/api/auth/signin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(new UserDataDTO.LoginRequest("testuser1", "testpass2"))))
//
//                .andExpect(status().isOk());
//    }
//
//
//}
