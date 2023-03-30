import httpClient from '@/apis/httpClient';
import { GenericResponse, NonDataResponse } from '@/models/ResponseModels';
import { ChangePasswordRequest, EditMemberRequest, MemberModel } from '@/models/MemberModels';

export const getMember = () => {
  return httpClient.get<GenericResponse<MemberModel>>('v1/members/mine').then(res => res.data);
};

export const putMember = (req: EditMemberRequest) => {
  return httpClient.put<GenericResponse<MemberModel>>('v1/members', req).then(res => res.data);
};

export const requestMemberChangePassword = (req: ChangePasswordRequest) => {
  return httpClient.put<NonDataResponse>('v1/members/password', req).then(res => res.data);
};
