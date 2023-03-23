package com.shshon.mypet.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shshon.mypet.advice.errorHandler.V1BindExceptionHandler;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.auth.infra.JwtTokenProviderImpl;
import com.shshon.mypet.config.AuthenticationMemberTestConfig;
import com.shshon.mypet.properties.JwtTokenProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(JwtTokenProperties.class)
@Import({
        V1BindExceptionHandler.class,
        AuthenticationMemberTestConfig.class,
        JwtTokenProviderImpl.class
})
@WebMvcTest
@AutoConfigureRestDocs
public class ApiDocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    protected void setUp() {
  /*      if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }*/
//        databaseCleanup.execute();
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

    protected String createAuthToken(String email) {
        return jwtTokenProvider.createToken(email);
    }

    protected String auth(String email) {
        return "Bearer " + createAuthToken(email);
    }
}
