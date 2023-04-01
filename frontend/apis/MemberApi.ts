import httpClient from '@/apis/httpClient';
import { GenericResponse, NonDataResponse } from '@/core/models/ResponseModels';
import { ChangePasswordRequest, EditMemberProfileRequest, MemberModel } from '@/core/models/MemberModels';

export const getMember = () => {
  return httpClient.get<GenericResponse<MemberModel>>('v1/members/mine').then(res => res.data);
};

export const putMember = (req: EditMemberProfileRequest) => {
  return httpClient.put<GenericResponse<MemberModel>>('v1/members', req).then(res => res.data);
};

export const putMemberPassword = (req: ChangePasswordRequest) => {
  return httpClient.put<NonDataResponse>('v1/members/password', req).then(res => res.data);
};
