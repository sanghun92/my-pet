import httpClient from '@/apis/httpClient';
import { deleteCookie, getCookie, hasCookie, setCookie } from 'cookies-next';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '@/constants/common';
import { OptionsType } from 'cookies-next/src/types';
import jwt_decode from 'jwt-decode';
import { JwtPayload } from '@/core/models/AuthModels';

export function setAccessToken(token: string | undefined) {
  if (!token) {
    deleteCookie(ACCESS_TOKEN);
    httpClient.defaults.headers['Authorization'] = '';
    return;
  }
  const expired = new Date();
  expired.setMinutes(expired.getMinutes() + 15);

  setCookie(ACCESS_TOKEN, token, { path: '/', expires: expired });
  httpClient.defaults.headers['Authorization'] = `Bearer ${token}`;
}

export function hasTokens(options?: OptionsType): boolean {
  return hasAccessToken(options) && hasRefreshToken(options);
}

export function hasAccessToken(options?: OptionsType): boolean {
  return hasCookie(ACCESS_TOKEN, options);
}

export function hasRefreshToken(options?: OptionsType): boolean {
  return hasCookie(REFRESH_TOKEN, options);
}

export function getAccessToken(options?: OptionsType): string {
  return getCookieOrEmpty(ACCESS_TOKEN, options);
}

export function getRefreshToken(options?: OptionsType): string {
  return getCookieOrEmpty(REFRESH_TOKEN, options);
}

export function removeTokens(options?: OptionsType) {
  deleteCookie(ACCESS_TOKEN, options);
  deleteCookie(REFRESH_TOKEN, options);
  httpClient.defaults.headers['Authorization'] = '';
}

export function getJwtPayload(accessToken: string): JwtPayload {
  const decode: any = jwt_decode(accessToken);
  const now = Math.floor(Date.now() / 1000);
  return {
    memberId: decode.jti,
    role: decode.role,
    email: decode.sub,
    nickname: decode.nickname,
    ip: decode.ip,
    expires_at: new Date(decode.exp * 1000),
    issued_at: new Date(decode.iat * 1000),
    isVerifiedToken: decode.exp < now && decode.iat > now,
  };
}

function getCookieOrEmpty(key: string, options?: OptionsType): string {
  const cookieVal = getCookie(key, options);
  return cookieVal ? cookieVal.toString() : '';
}
