import { useMutation, UseMutationOptions, useQuery, useQueryClient, UseQueryOptions } from 'react-query';
import { getMember, putMember, putMemberPassword } from '@/apis/MemberApi';
import { showError } from '@/utils/SnackbarUtils';
import { AxiosError } from 'axios';
import { ChangePasswordRequest, MemberModel } from '@/core/models/MemberModels';
import { ErrorResponse, GenericResponse, NonDataResponse } from '@/core/models/ResponseModels';
import { queries, QueryKeys } from '@/constants/queries';
import { useSetRecoilState } from 'recoil';
import { memberState } from '@/core/recoil/member/atoms';
import { format } from 'date-fns';
import { ko } from 'date-fns/locale';

export const useMemberQuery = (
  options: UseQueryOptions<
    GenericResponse<MemberModel>,
    AxiosError,
    MemberModel,
    QueryKeys['members']['detail']['queryKey']
  > = {},
) => {
  return useQuery(queries.members.detail().queryKey, (): Promise<GenericResponse<MemberModel>> => getMember(), {
    select: data => {
      const profile = data.data.profile;
      const emailVerification = data.data.emailVerification;
      return {
        profile: {
          ...profile,
          birthDay: profile.birthDay ? new Date(profile.birthDay) : undefined,
          createdAt: new Date(profile.createdAt),
        },
        emailVerification: emailVerification
          ? {
              ...emailVerification,
              verifiedAt: new Date(emailVerification.verifiedAt),
            }
          : undefined,
      };
    },
    onError: error => {
      showError('회원 조회 실패', error);
    },
    ...options,
  });
};

export const useUpdateMember = (
  options: UseMutationOptions<GenericResponse<MemberModel>, AxiosError<ErrorResponse>, MemberModel> = {},
) => {
  const queryClient = useQueryClient();
  const setMember = useSetRecoilState(memberState);
  return useMutation(
    (member): Promise<GenericResponse<MemberModel>> => {
      return putMember({
        ...member.profile,
        birthDay: member.profile.birthDay ? format(member.profile.birthDay, 'yyyy-MM-dd', { locale: ko }) : undefined,
      });
    },
    {
      ...options,
      onSuccess: res => {
        setMember({
          ...res.data,
        });
        return queryClient.invalidateQueries(queries.members.detail().queryKey);
      },
      onError: error => {
        showError('프로필 수정 실패', error);
      },
    },
  );
};

export const useChangePassword = (
  options: UseMutationOptions<NonDataResponse, AxiosError<ErrorResponse>, ChangePasswordRequest> = {},
) => {
  return useMutation(
    (req): Promise<NonDataResponse> => {
      return putMemberPassword(req);
    },
    {
      ...options,
      onError: error => {
        showError('패스워드 변경 실패', error);
      },
    },
  );
};
