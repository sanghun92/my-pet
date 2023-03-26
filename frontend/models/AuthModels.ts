export interface AuthModel {
  accessToken?: string;
  isLoggedIn: boolean;
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

export interface SignUpRequest {
  email: string;
  password: string;
  nickname: string;
  phoneNumber: string;
  birthDay: string;
}
