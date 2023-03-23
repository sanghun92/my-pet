package com.shshon.mypet.auth.dto;

import com.shshon.mypet.auth.domain.RefreshToken;

public record TokenDto(String accessToken,
                       RefreshToken refreshToken) {

}
