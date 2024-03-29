// ** Icon imports
import Login from 'mdi-material-ui/Login';
import Table from 'mdi-material-ui/Table';
import CubeOutline from 'mdi-material-ui/CubeOutline';
import HomeOutline from 'mdi-material-ui/HomeOutline';
import FormatLetterCase from 'mdi-material-ui/FormatLetterCase';
import CreditCardOutline from 'mdi-material-ui/CreditCardOutline';
import AccountPlusOutline from 'mdi-material-ui/AccountPlusOutline';
import AlertCircleOutline from 'mdi-material-ui/AlertCircleOutline';
import GoogleCirclesExtended from 'mdi-material-ui/GoogleCirclesExtended';

// ** Type import
import { VerticalNavItemsType } from '@/core/layouts/types';
import NavigationPaths from '@/navigation/NavigationPaths';

const navigation = (): VerticalNavItemsType => {
  return [
    {
      title: 'Dashboard',
      icon: HomeOutline,
      path: NavigationPaths.ROOT,
    },
    {
      sectionTitle: 'Pages',
    },
    {
      title: 'Login',
      icon: Login,
      path: NavigationPaths.AUTH.LOGIN,
      // openInNewTab: true,
    },
    {
      title: 'Register',
      icon: AccountPlusOutline,
      path: NavigationPaths.AUTH.REGISTER,
      // openInNewTab: true,
    },
    {
      title: 'Error',
      icon: AlertCircleOutline,
      path: '/pages/error',
      // openInNewTab: true,
    },
    {
      sectionTitle: 'User Interface',
    },
    {
      title: 'Typography',
      icon: FormatLetterCase,
      path: '/typography',
    },
    {
      title: 'Icons',
      path: '/icons',
      icon: GoogleCirclesExtended,
    },
    {
      title: 'Cards',
      icon: CreditCardOutline,
      path: '/cards',
    },
    {
      title: 'Tables',
      icon: Table,
      path: '/tables',
    },
    {
      icon: CubeOutline,
      title: 'Form Layouts',
      path: '/form-layouts',
    },
  ];
};

export default navigation;
