package com.shshon.mypet;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.util.StringUtils;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.snippet.Attributes.key;

public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    static Attributes.Attribute constraints(ConstraintDescriptions constraints, String property) {
        return key("constraints").value(
                StringUtils.collectionToDelimitedString(
                        constraints.descriptionsForProperty(property),
                        System.lineSeparator())
        );
    }
}
