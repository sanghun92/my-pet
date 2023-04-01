export interface MemberModel {
  profile: {
    id: number;
    email: string;
    nickname: string;
    birthDay?: Date;
    phoneNumber: string;
    createdAt: Date;
  };
  emailVerification?: {
    verifiedAt: Date;
    isVerified: boolean;
  };
}

export interface EditMemberProfileRequest {
  id: number;
  nickname: string;
  birthDay?: string;
  phoneNumber: string;
}

export interface ChangePasswordRequest {
  password: string;
  newPassword: string;
}
