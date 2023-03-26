import { useMutation, UseMutationOptions } from 'react-query';
import { requestSignIn, requestSignUp } from '@/apis/AuthApi';
import { showError, showErrorMessage } from '@/utils/SnackbarUtils';
import {
  SignInRequest,
  SignUpRequest,
  TokenResponse,
} from '@/models/AuthModels';
import { AxiosError } from 'axios';
import {
  ErrorResponse,
  GenericResponse,
  NonDataResponse,
} from '@/models/ResponseModels';
import { useSetRecoilState } from 'recoil';
import { setAccessToken } from '@/utils/TokenManager';
import { authState } from '@/recoil/auth/atoms';

export const useSignIn = (
  options: UseMutationOptions<
    GenericResponse<TokenResponse>,
    AxiosError<ErrorResponse>,
    SignInRequest
  > = {},
) => {
  const setAuth = useSetRecoilState(authState);

  return useMutation(
    (req): Promise<GenericResponse<TokenResponse>> => requestSignIn(req),
    {
      ...options,
      onSuccess: res => {
        const accessToken = res.data.accessToken;
        setAuth({
          accessToken: accessToken,
          isLoggedIn: true,
        });
        setAccessToken(accessToken);
      },
      onError: error => {
        showErrorMessage(error, {
          title: '인증 정보가 잘못되었습니다.',
          content: '이메일 혹은 패스워드를 확인해주세요.',
        });
      },
    },
  );
};

export const useSignUp = (
  options: UseMutationOptions<
    NonDataResponse,
    AxiosError<ErrorResponse>,
    SignUpRequest
  > = {},
) => {
  return useMutation((req): Promise<NonDataResponse> => requestSignUp(req), {
    ...options,
    onError: error => {
      showError('회원 가입 실패', error);
    },
  });
};
