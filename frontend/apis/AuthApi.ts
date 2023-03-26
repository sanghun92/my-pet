import httpClient from '@/apis/httpClient';
import {
  SignInRequest,
  SignUpRequest,
  TokenResponse,
} from '@/models/AuthModels';
import { GenericResponse, NonDataResponse } from '@/models/ResponseModels';

export const requestSignIn = async (req: SignInRequest) => {
  return httpClient
    .post<GenericResponse<TokenResponse>>('/v1/auth/login', req, {
      withCredentials: true,
    })
    .then(res => res.data);
};

export const requestSignUp = async (req: SignUpRequest) => {
  return httpClient
    .post<NonDataResponse>('/v1/members', req)
    .then(res => res.data);
};

export const requestReIssueToken = async (refreshToken: string) => {
  return httpClient
    .post<GenericResponse<TokenResponse>>('/v1/auth/token', {
      refreshToken: refreshToken,
    })
    .then(res => res.data);
};

export const requestEmailVerification = async (code: string) => {
  return httpClient
    .post<NonDataResponse>('/v1/auth/verification', {
      code: code,
    })
    .then(res => res.data);
};
