import { atom } from 'recoil';
import { AuthModel } from '@/models/AuthModels';

export const authState = atom<AuthModel>({
  key: 'auth/authState',
  default: {
    accessToken: undefined,
    isVerifiedEmail: false,
    isLoggedIn: false,
  },
});
