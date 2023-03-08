package com.shshon.mypet.endpoint.v1.petcategory;

import com.shshon.mypet.endpoint.v1.petcategory.request.ParentPetCategoryRegisterRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class PetCategoryRestAssuredV1 {

    public static ExtractableResponse<Response> registerParentPetCategory(ParentPetCategoryRegisterRequest request) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON.toString())
                .body(request)
                .when().post(PetCategoryPaths.REGISTER_PARENT_PET_CATEGORY)
                .then().log().all()
                .extract();
    }
}
