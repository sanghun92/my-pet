import httpClient from '@/apis/httpClient';
import { deleteCookie, getCookie, hasCookie, setCookie } from 'cookies-next';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '@/constants/common';
import { OptionsType } from 'cookies-next/src/types';

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
  return hasCookie(ACCESS_TOKEN, options) && hasCookie(REFRESH_TOKEN, options);
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

function getCookieOrEmpty(key: string, options?: OptionsType): string {
  const cookieVal = getCookie(key, options);
  return cookieVal ? cookieVal.toString() : '';
}
