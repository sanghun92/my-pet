package com.shshon.mypet.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.endpoint.v1.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RestControllerTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataLoader dataLoader;

    @BeforeEach
    protected void setUp() {
        dataLoader.addTestMember();
    }

    protected String toJsonString(Object contents) {
        try {
            return objectMapper.writeValueAsString(contents);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected byte[] toJsonBytes(Object contents) {
        try {
            return objectMapper.writeValueAsBytes(contents);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected String auth(String email) {
        return "Bearer " + jwtTokenProvider.createToken(email);
    }
}
