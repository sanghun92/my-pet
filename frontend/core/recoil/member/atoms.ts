import { atom } from 'recoil';
import { MemberModel } from '@/core/models/MemberModels';

export const memberState = atom<MemberModel>({
  key: 'member/memberState',
});
