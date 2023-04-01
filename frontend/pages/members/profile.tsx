// ** React Imports
import { SyntheticEvent, useState } from 'react';

// ** MUI Imports
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import TabContext from '@mui/lab/TabContext';
import { styled } from '@mui/material/styles';
import MuiTab, { TabProps } from '@mui/material/Tab';

// ** Icons Imports
import AccountOutline from 'mdi-material-ui/AccountOutline';
import LockOpenOutline from 'mdi-material-ui/LockOpenOutline';
import Paw from 'mdi-material-ui/Paw';

// ** Demo Tabs Imports
import TabMemberPet from '@/views/members/profile/TabInfo';
import TabMemberProfile from '@/views/members/profile/TabMemberProfile';
import TabSecurity from '@/views/members/profile/TabSecurity';

const Tab = styled(MuiTab)<TabProps>(({ theme }) => ({
  [theme.breakpoints.down('md')]: {
    minWidth: 100,
  },
  [theme.breakpoints.down('sm')]: {
    minWidth: 67,
  },
}));

const TabName = styled('span')(({ theme }) => ({
  lineHeight: 1.71,
  fontSize: '0.875rem',
  marginLeft: theme.spacing(2.4),
  [theme.breakpoints.down('md')]: {
    display: 'none',
  },
}));

const ProfilePage = () => {
  // ** State
  const [value, setValue] = useState<string>('profile');

  const handleChange = (event: SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  return (
    <Card>
      <TabContext value={value}>
        <TabList
          onChange={handleChange}
          aria-label='account-settings tabs'
          sx={{ borderBottom: theme => `1px solid ${theme.palette.divider}` }}
        >
          <Tab
            value='profile'
            label={
              <Box sx={{ display: 'flex', alignItems: 'center' }}>
                <AccountOutline />
                <TabName>프로필</TabName>
              </Box>
            }
          />
          <Tab
            value='security'
            label={
              <Box sx={{ display: 'flex', alignItems: 'center' }}>
                <LockOpenOutline />
                <TabName>Security</TabName>
              </Box>
            }
          />
          <Tab
            value='pet'
            label={
              <Box sx={{ display: 'flex', alignItems: 'center' }}>
                <Paw />
                <TabName>반려동물</TabName>
              </Box>
            }
          />
        </TabList>

        <TabPanel sx={{ p: 0 }} value='profile'>
          <TabMemberProfile />
        </TabPanel>
        <TabPanel sx={{ p: 0 }} value='security'>
          <TabSecurity />
        </TabPanel>
        <TabPanel sx={{ p: 0 }} value='pet'>
          <TabMemberPet />
        </TabPanel>
      </TabContext>
    </Card>
  );
};

export default ProfilePage;
