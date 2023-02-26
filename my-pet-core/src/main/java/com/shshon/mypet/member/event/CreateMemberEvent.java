package com.shshon.mypet.member.event;

import lombok.Builder;

@Builder
public record CreateMemberEvent(
        Long id,
        String email,
        String nickname,
        String certificationCode
) {}
