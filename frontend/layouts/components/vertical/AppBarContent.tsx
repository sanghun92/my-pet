// ** MUI Imports
import Box from '@mui/material/Box';
import { Theme } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import useMediaQuery from '@mui/material/useMediaQuery';
import InputAdornment from '@mui/material/InputAdornment';

// ** Icons Imports
import Menu from 'mdi-material-ui/Menu';
import Magnify from 'mdi-material-ui/Magnify';

// ** Type Import
import { Settings } from '@/core/context/settingsContext';

// ** Components
import ModeToggler from '@/core/layouts/components/shared-components/ModeToggler';
import NotificationDropdown from '@/core/layouts/components/shared-components/NotificationDropdown';
import UserDropdown from '@/core/layouts/components/shared-components/UserDropdown';
import { useRecoilValue } from 'recoil';
import { authState } from '@/core/recoil/auth/atoms';
import AnonymousDropdown from '@/core/layouts/components/shared-components/AnonymousDropdown';
import { NavLink } from '@/core/layouts/types';

export interface AppBarContentProps {
  hidden: boolean;
  settings: Settings;
  toggleNavVisibility: () => void;
  saveSettings: (values: Settings) => void;
  anonymousNavItems: NavLink[];
}

const AppBarContent = (props: AppBarContentProps) => {
  // ** Props
  const { hidden, settings, saveSettings, toggleNavVisibility, anonymousNavItems } = props;

  // ** Hook
  const hiddenSm = useMediaQuery((theme: Theme) => theme.breakpoints.down('sm'));
  const authRecoilState = useRecoilValue(authState);

  return (
    <Box sx={{ width: '100%', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
      <Box className='actions-left' sx={{ mr: 2, display: 'flex', alignItems: 'center' }}>
        {hidden ? (
          <IconButton
            color='inherit'
            onClick={toggleNavVisibility}
            sx={{ ml: -2.75, ...(hiddenSm ? {} : { mr: 3.5 }) }}
          >
            <Menu />
          </IconButton>
        ) : null}
        <TextField
          size='small'
          sx={{ '& .MuiOutlinedInput-root': { borderRadius: 4 } }}
          InputProps={{
            startAdornment: (
              <InputAdornment position='start'>
                <Magnify fontSize='small' />
              </InputAdornment>
            ),
          }}
        />
      </Box>
      <Box className='actions-right' sx={{ display: 'flex', alignItems: 'center' }}>
        {/*{hiddenSm ? null : (
          <Box
            component='a'
            target='_blank'
            rel='noreferrer'
            sx={{ mr: 4, display: 'flex' }}
            href='https://github.com/themeselection/materio-mui-react-nextjs-admin-template-free'
          >
            <img
              height={24}
              alt='github stars'
              src='https://img.shields.io/github/stars/themeselection/materio-mui-react-nextjs-admin-template-free?style=social'
            />
          </Box>
        )}*/}
        <ModeToggler settings={settings} saveSettings={saveSettings} />
        {authRecoilState && authRecoilState.isLoggedIn ? (
          <>
            <NotificationDropdown />
            <UserDropdown />
          </>
        ) : (
          <>
            <AnonymousDropdown anonymousNavItems={anonymousNavItems} />
          </>
        )}
      </Box>
    </Box>
  );
};

export default AppBarContent;
