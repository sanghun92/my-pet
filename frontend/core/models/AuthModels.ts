export interface AuthModel {
  accessToken?: string;
  isLoggedIn: boolean;
  jwtPayload?: JwtPayload;
}

export interface JwtPayload {
  memberId: number;
  role: string;
  email: string;
  nickname: string;
  ip: string;
  issued_at: Date;
  expires_at: Date;
  isVerifiedToken: boolean;
}

export interface SignInForm {
  email: string;
  password: string;
}

export interface SignInRequest {
  email: string;
  password: string;
}

export interface TokenResponse {
  accessToken: string;
}

export interface MemberRegisterState {
  email: string;
  password: string;
  confirmPassword: string;
  nickname: string;
  phoneNumber: string;
  birthDay: Date;
  termsAndConditions: boolean;
}

export interface MemberRegisterRequest {
  email: string;
  password: string;
  nickname: string;
  phoneNumber: string;
  birthDay?: string;
}
