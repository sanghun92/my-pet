package com.shshon.mypet.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shshon.mypet.advice.clientDecorator.RequestClientArgumentResolver;
import com.shshon.mypet.advice.requestDecorator.AuthenticationMemberArgumentResolver;
import com.shshon.mypet.advice.responseDecorator.V1ResponseDecorator;
import com.shshon.mypet.stub.auth.TokenServiceStub;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

public class ApiDocumentationTest extends RestDocumentationTest {

    protected static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNjc2NTM5NTgxLCJleHAiOjE2NzY1Mzk1ODN9.LTTbWaHFm5377EJURkf5NMmjXxDMgaHjGXw5EwUWrZ8";
    protected static final ObjectMapper OBJECT_MAPPER = objectMapper();
    private static final Object[] CONTROLLER_ADVICES = new Object[]{new V1ResponseDecorator()};
    private static final HttpMessageConverter<?>[] HTTP_MESSAGE_CONVERTERS = httpMessageConverters();
    private static final HandlerMethodArgumentResolver[] ARGUMENT_RESOLVERS = handlerMethodArgumentResolvers();

    protected MockMvc apiMockMvc(Object controller) {
        return mockMvc(controller,
                CONTROLLER_ADVICES,
                ARGUMENT_RESOLVERS,
                HTTP_MESSAGE_CONVERTERS
        );
    }

    private static HttpMessageConverter<?>[] httpMessageConverters() {
        return new HttpMessageConverter[]{
                new MappingJackson2HttpMessageConverter(OBJECT_MAPPER)
        };
    }

    private static HandlerMethodArgumentResolver[] handlerMethodArgumentResolvers() {
        return new HandlerMethodArgumentResolver[]{
                new AuthenticationMemberArgumentResolver(new TokenServiceStub()),
                new RequestClientArgumentResolver()
        };
    }

    private static ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
                .build();
    }

    protected String auth() {
        return "Bearer " + ACCESS_TOKEN;
    }

    protected String toJsonString(Object contents) {
        try {
            return OBJECT_MAPPER.writeValueAsString(contents);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected byte[] toJsonBytes(Object contents) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(contents);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
