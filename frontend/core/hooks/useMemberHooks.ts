import { useMutation, UseMutationOptions, useQuery, useQueryClient, UseQueryOptions } from 'react-query';
import { getMember, putMember } from '@/apis/MemberApi';
import { showError } from '@/utils/SnackbarUtils';
import { AxiosError } from 'axios';
import { MemberModel } from '@/models/MemberModels';
import { ErrorResponse, GenericResponse } from '@/models/ResponseModels';
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
      return {
        ...data.data,
        birthDay: data.data.birthDay ? new Date(data.data.birthDay) : undefined,
        createdAt: new Date(data.data.createdAt),
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
        ...member,
        birthDay: member.birthDay ? format(member.birthDay, 'yyyy-MM-dd', { locale: ko }) : undefined,
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
