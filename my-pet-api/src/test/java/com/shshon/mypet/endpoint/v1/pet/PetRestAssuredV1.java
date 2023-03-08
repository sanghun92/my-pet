package com.shshon.mypet.endpoint.v1.pet;

import com.shshon.mypet.endpoint.v1.pet.request.PetRegisterRequest;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

public class PetRestAssuredV1 {

    public static ExtractableResponse<Response> registerMyPet(String token,
                                                              PetRegisterRequest request) {
        MultiPartSpecification requestSpec = new MultiPartSpecBuilder(request)
                .with()
                .controlName("request")
                .and()
                .mimeType(ContentType.JSON.toString())
                .charset("utf-8")
                .build();
        MultiPartSpecification imageSpec = new MultiPartSpecBuilder("test".getBytes())
                .with()
                .controlName("petImage")
                .and()
                .mimeType(ContentType.MULTIPART.toString())
                .build();

        return RestAssured.given().log().all()
                .contentType(ContentType.MULTIPART.toString())
                .auth().oauth2(token)
                .multiPart(requestSpec)
                .multiPart(imageSpec)
                .when().post(PetPaths.REGISTER_MY_PET)
                .then().log().all()
                .extract();
    }
}
