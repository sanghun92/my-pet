import Path from '@/navigation/NavigationPaths';
import { NavLink } from '@/core/layouts/types';
import Login from 'mdi-material-ui/Login';
import AccountPlusOutline from 'mdi-material-ui/AccountPlusOutline';

export const AnonymousDropdownNavigation = (): NavLink[] => {
  return [
    {
      title: 'Login',
      icon: Login,
      path: Path.AUTH.LOGIN,
    },
    {
      title: 'Register',
      icon: AccountPlusOutline,
      path: Path.AUTH.REGISTER,
    },
  ];
};
