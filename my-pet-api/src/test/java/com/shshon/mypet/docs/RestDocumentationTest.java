package com.shshon.mypet.docs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

@ActiveProfiles("test")
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
public class RestDocumentationTest {

    private RestDocumentationContextProvider restDocumentation;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.restDocumentation = restDocumentation;
    }

    protected MockMvc mockMvc(Object controller,
                              Object[] controllerAdvices,
                              HandlerMethodArgumentResolver[] argumentResolvers,
                              HttpMessageConverter<?>[] httpMessageConverters) {
        return createMockMvc(controller, controllerAdvices, argumentResolvers, httpMessageConverters);
    }

    private MockMvc createMockMvc(Object controller,
                                  Object[] controllerAdvices,
                                  HandlerMethodArgumentResolver[] argumentResolvers,
                                  HttpMessageConverter<?>[] httpMessageConverters) {
        return MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(controllerAdvices)
                .setCustomArgumentResolvers(argumentResolvers)
                .setMessageConverters(httpMessageConverters)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }
}
