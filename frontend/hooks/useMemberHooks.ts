import {
  useMutation,
  UseMutationOptions,
  useQuery,
  useQueryClient,
  UseQueryOptions,
} from 'react-query';
import { getMember, putMember } from '@/apis/MemberApi';
import { showError } from '@/utils/SnackbarUtils';
import { AxiosError } from 'axios';
import { EditMemberRequest, MemberModel } from '@/models/MemberModels';
import { ErrorResponse, GenericResponse } from '@/models/ResponseModels';
import { queries, QueryKeys } from '@/constants/queries';
import { useSetRecoilState } from 'recoil';
import { memberState } from '@/recoil/member/atoms';

export const useMemberQuery = (
  options: UseQueryOptions<
    GenericResponse<MemberModel>,
    AxiosError,
    MemberModel,
    QueryKeys['members']['detail']['queryKey']
  > = {},
) => {
  return useQuery(
    queries.members.detail().queryKey,
    (): Promise<GenericResponse<MemberModel>> => getMember(),
    {
      select: data => {
        return {
          ...data.data,
          birthDay: data.data.birthDay
            ? new Date(data.data.birthDay)
            : undefined,
          createdAt: data.data.createdAt
            ? new Date(data.data.createdAt)
            : undefined,
        };
      },
      onError: error => {
        showError('회원 조회 실패', error);
      },
      ...options,
    },
  );
};

export const useUpdateMember = (
  options: UseMutationOptions<
    GenericResponse<MemberModel>,
    AxiosError<ErrorResponse>,
    EditMemberRequest
  > = {},
) => {
  const queryClient = useQueryClient();
  const setMember = useSetRecoilState(memberState);
  return useMutation(
    (req): Promise<GenericResponse<MemberModel>> => putMember(req),
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
