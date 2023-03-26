import { atom } from 'recoil';
import { MemberModel } from '@/models/MemberModels';

export const memberState = atom<MemberModel>({
  key: 'member/memberState',
  default: {
    id: undefined,
    email: undefined,
    nickname: undefined,
    birthDay: undefined,
    phoneNumber: undefined,
    createdAt: undefined,
  },
});
