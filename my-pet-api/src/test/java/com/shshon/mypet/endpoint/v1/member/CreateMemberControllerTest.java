package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.endpoint.RestControllerTest;
import com.shshon.mypet.endpoint.v1.member.request.CreateMemberRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateMemberControllerTest extends RestControllerTest {

    @Test
    @DisplayName("회원 가입 요청시 회원 생성 후 201 코드로 응답한다")
    void createMemberRequestThenReturnResponse() {
        // given
        CreateMemberRequest request = CreateMemberRequest.builder()
                .email("test@test.com")
                .password("test123456!")
                .nickname("myNickName")
                .build();

        // when
        AtomicReference<ExtractableResponse<Response>> atomicResponse = new AtomicReference<>();
        assertTimeout(Duration.ofSeconds(1),
                () -> atomicResponse.set(MemberRestAssuredV1.joinMember(request)));

        // then
        ExtractableResponse<Response> response = atomicResponse.get();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotEmpty()
        );
    }
}
