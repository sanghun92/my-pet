export interface MemberModel {
  id: number;
  email: string;
  nickname: string;
  birthDay?: Date;
  phoneNumber: string;
  createdAt: Date;
}

export interface EditMemberRequest {
  id: number;
  nickname: string;
  birthDay?: string;
  phoneNumber: string;
}

export interface ChangePasswordRequest {
  prevPassword: string;
  newPassword: string;
}
