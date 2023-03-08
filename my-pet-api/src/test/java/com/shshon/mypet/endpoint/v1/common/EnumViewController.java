package com.shshon.mypet.endpoint.v1.common;

import com.shshon.mypet.common.domain.EnumType;
import com.shshon.mypet.endpoint.v1.ApiResponseV1;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import com.shshon.mypet.pet.domain.PetType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EnumViewController {

    @GetMapping("/docs")
    public ApiResponseV1<Docs> findAll() {
        Docs docs = Docs.builder()
                .petBodyTypes(getEnumValues(PetBodyType.values()))
                .petGenders(getEnumValues(PetGender.values()))
                .petTypes(getEnumValues(PetType.values()))
                .build();
        return ApiResponseV1.ok(docs);
    }

    private static Map<String, String> getEnumValues(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getId, EnumType::getText));
    }
}
