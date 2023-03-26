package com.shshon.mypet.auth.domain;

public record LoginMember(Long id,
                          String email,
                          String ip,
                          String userAgent) {

}
