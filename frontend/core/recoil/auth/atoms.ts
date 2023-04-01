import { atom } from 'recoil';
import { AuthModel } from '@/core/models/AuthModels';

export const authState = atom<AuthModel>({
  key: 'auth/authState',
  default: {
    isLoggedIn: false,
  },
});
