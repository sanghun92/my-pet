import httpClient from '@/apis/httpClient';
import { MemberRegisterRequest, SignInRequest, TokenResponse } from '@/core/models/AuthModels';
import { GenericResponse, NonDataResponse } from '@/core/models/ResponseModels';

export const postSignIn = async (req: SignInRequest) => {
  return httpClient
    .post<GenericResponse<TokenResponse>>('/v1/auth/login', req, {
      withCredentials: true,
    })
    .then(res => res.data);
};

export const postSignUp = async (req: MemberRegisterRequest) => {
  return httpClient.post<NonDataResponse>('/v1/members', req).then(res => res.data);
};

export const postReIssueToken = async (refreshToken: string) => {
  return httpClient
    .post<GenericResponse<TokenResponse>>('/v1/auth/token', {
      refreshToken: refreshToken,
    })
    .then(res => res.data);
};

export const postSendEmailVerificationCode = async (email: string) => {
  return httpClient.post<NonDataResponse>('/v1/auth/verification', email).then(res => res.data);
};

export const getVerifyEmailCodeResult = async (code: string) => {
  return httpClient
    .get<NonDataResponse>('/v1/auth/verification', {
      params: { code: code },
    })
    .then(res => res.data);
};
