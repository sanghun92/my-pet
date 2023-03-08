package com.shshon.mypet.docs.util;

import lombok.RequiredArgsConstructor;

public interface DocumentLinkGenerator {

    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:common/%s.html[%s,role=\"popup\"]", docUrl.pageId, docUrl.text);
    }

    @RequiredArgsConstructor
    enum DocUrl {
        PET_BODY_TYPES("petBodyTypes", "반려동물 체형"),
        PET_GENDERS("petGenders", "반려동물 성별"),
        PET_TYPES("petTypes", "반려동물 종류");

        private final String pageId;
        private final String text;
    }
}
