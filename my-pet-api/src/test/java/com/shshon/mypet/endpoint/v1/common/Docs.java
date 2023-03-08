package com.shshon.mypet.endpoint.v1.common;

import lombok.*;

import java.util.Map;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Docs {

    private Map<String, String> petBodyTypes;
    private Map<String, String> petGenders;
    private Map<String, String> petTypes;
}
