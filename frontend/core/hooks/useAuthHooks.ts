import { useMutation, UseMutationOptions } from 'react-query';
import { postSendEmailVerificationCode, postSignIn, postSignUp } from '@/apis/AuthApi';
import { showError, showErrorMessage } from '@/utils/SnackbarUtils';
import { AxiosError } from 'axios';
import { ErrorResponse, GenericResponse, NonDataResponse } from '@/core/models/ResponseModels';
import { MemberRegisterState, SignInRequest, TokenResponse } from '@/core/models/AuthModels';
import { useSetRecoilState } from 'recoil';
import { getJwtPayload, setAccessToken } from '@/utils/TokenManager';
import { authState } from '@/core/recoil/auth/atoms';
import { format } from 'date-fns';
import { ko } from 'date-fns/locale';

export const useLogin = (
  options: UseMutationOptions<GenericResponse<TokenResponse>, AxiosError<ErrorResponse>, SignInRequest> = {},
) => {
  const setAuth = useSetRecoilState(authState);

  return useMutation((req): Promise<GenericResponse<TokenResponse>> => postSignIn(req), {
    ...options,
    onSuccess: res => {
      const accessToken = res.data.accessToken;
      setAuth({
        accessToken: accessToken,
        isLoggedIn: true,
        jwtPayload: getJwtPayload(res.data.accessToken),
      });
      setAccessToken(accessToken);
    },
    onError: error => {
      showErrorMessage(error, {
        title: '인증 정보가 잘못되었습니다.',
        content: '이메일 혹은 패스워드를 확인해주세요.',
      });
    },
  });
};

export const useRegister = (
  options: UseMutationOptions<NonDataResponse, AxiosError<ErrorResponse>, MemberRegisterState> = {},
) => {
  return useMutation(
    (member): Promise<NonDataResponse> =>
      postSignUp({
        ...member,
        birthDay: member.birthDay ? format(member.birthDay, 'yyyy-MM-dd', { locale: ko }) : undefined,
      }),
    {
      ...options,
      onError: error => {
        showError('회원 가입 실패', error);
      },
    },
  );
};

export const useSendEmailVerificationCode = (
  options: UseMutationOptions<NonDataResponse, AxiosError<ErrorResponse>, string> = {},
) => {
  return useMutation((req): Promise<NonDataResponse> => postSendEmailVerificationCode(req), {
    ...options,
    onError: error => {
      showError('인증 코드 전송 실패', error);
    },
  });
};
