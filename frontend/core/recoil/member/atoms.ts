import { atom } from 'recoil';
import { MemberModel } from '@/models/MemberModels';

export const memberState = atom<MemberModel>({
  key: 'member/memberState',
});
