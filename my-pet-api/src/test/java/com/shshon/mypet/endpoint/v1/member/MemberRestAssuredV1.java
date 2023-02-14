package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.endpoint.v1.member.request.CreateMemberRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberRestAssuredV1 {

    public static ExtractableResponse<Response> joinMember(CreateMemberRequest request) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(MemberPaths.JOIN_MEMBER)
                .then().log().all()
                .extract();
    }
}
