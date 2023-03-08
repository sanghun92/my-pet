package com.shshon.mypet.email.dto;

import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.Map;

@Builder
public record MailMessageDto(@Nullable String[] to,
                             @Nullable String subject,
                             @Nullable String template,
                             Map<String, String> attribute) {

}
