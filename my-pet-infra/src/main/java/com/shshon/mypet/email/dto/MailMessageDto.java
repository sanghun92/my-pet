package com.shshon.mypet.email.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Map;

@Getter
public class MailMessageDto {

    @Nullable
    private final String[] to;

    @Nullable
    private final String subject;

    @Nullable
    private final String template;

    private final Map<String, String> attribute;

    @Builder
    public MailMessageDto(@Nullable String[] to,
                          @Nullable String subject,
                          @NonNull String template,
                          @Nullable Map<String, String> attribute) {
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.attribute = attribute;
    }
}
