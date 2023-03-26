import {
  Box,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Typography,
} from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';
import SearchIcon from '@mui/icons-material/Search';
import ExploreIcon from '@mui/icons-material/Explore';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import SlowMotionVideoIcon from '@mui/icons-material/SlowMotionVideo';
import NotificationsIcon from '@mui/icons-material/Notifications';
import PostAddIcon from '@mui/icons-material/PostAdd';
import Person3Icon from '@mui/icons-material/Person3';
import React, { useState } from 'react';
import Path from '@/constants/path';
import Link from 'next/link';
import { useRecoilValue } from 'recoil';
import { authState } from '@/recoil/auth/atoms';

interface Props {
  /**
   * Injected by the documentation to work in an iframe.
   * You won't need it on your project.
   */
  window?: () => Window;
}

const drawerWidth = 240;

const SideBar = (props: Props) => {
  const { window } = props;
  const [mobileOpen, setMobileOpen] = useState(false);
  const auth = useRecoilValue(authState);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  const drawerItems = [
    { title: '홈', icon: <HomeIcon />, to: Path.ROOT, isLoggedIn: false },
    { title: '검색', icon: <SearchIcon />, to: Path.SEARCH, isLoggedIn: false },
    {
      title: '탐색 탭',
      icon: <ExploreIcon />,
      to: Path.SEARCH_TAP,
      isLoggedIn: false,
    },
    {
      title: '릴스',
      icon: <SlowMotionVideoIcon />,
      to: Path.REELS,
      isLoggedIn: false,
    },
    { title: '메시지', icon: <InboxIcon />, to: Path.INBOX, isLoggedIn: true },
    {
      title: '알림',
      icon: <NotificationsIcon />,
      to: Path.NOTIFICATIONS,
      isLoggedIn: true,
    },
    { title: '포스트', icon: <PostAddIcon />, to: Path.POST, isLoggedIn: true },
    {
      title: '프로필',
      icon: <Person3Icon />,
      to: Path.PROFILE,
      isLoggedIn: true,
    },
  ];

  const drawer = (
    <>
      <Toolbar>
        <Typography
          variant="h6"
          noWrap
          component="a"
          href="/"
          sx={{
            mr: 2,
            display: { xs: 'none', md: 'flex' },
            fontFamily: 'monospace',
            fontStyle: 'italic',
            fontWeight: 700,
            letterSpacing: '.3rem',
            color: 'inherit',
            textDecoration: 'none',
          }}
        >
          My Pet
        </Typography>
      </Toolbar>
      <Divider />
      <List>
        {auth?.isLoggedIn
          ? drawerItems.map(item => (
              <Link href={item.to} key={item.title}>
                <ListItem
                  key={item.title}
                  style={{ textDecoration: 'none', color: 'white' }}
                  disablePadding
                >
                  <ListItemButton>
                    <ListItemIcon>{item.icon}</ListItemIcon>
                    <ListItemText primary={item.title} />
                  </ListItemButton>
                </ListItem>
              </Link>
            ))
          : drawerItems
              .filter(item => !item.isLoggedIn)
              .map(item => (
                <Link href={item.to} key={item.title}>
                  <ListItem
                    key={item.title}
                    style={{ textDecoration: 'none', color: 'white' }}
                    disablePadding
                  >
                    <ListItemButton>
                      <ListItemIcon>{item.icon}</ListItemIcon>
                      <ListItemText primary={item.title} />
                    </ListItemButton>
                  </ListItem>
                </Link>
              ))}
      </List>
    </>
  );

  const container =
    window !== undefined ? () => window().document.body : undefined;

  return (
    <Box sx={{ display: 'flex' }}>
      <Box
        component="nav"
        sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
        aria-label="mailbox folders"
      >
        {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
        <Drawer
          container={container}
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true, // Better open performance on mobile.
          }}
          sx={{
            display: { xs: 'block', sm: 'none' },
            '& .MuiDrawer-paper': {
              boxSizing: 'border-box',
              width: drawerWidth,
            },
          }}
        >
          {drawer}
        </Drawer>
        <Drawer
          variant="permanent"
          sx={{
            display: { xs: 'none', sm: 'block' },
            '& .MuiDrawer-paper': {
              boxSizing: 'border-box',
              width: drawerWidth,
            },
          }}
          open
        >
          {drawer}
        </Drawer>
      </Box>
    </Box>
  );
};
export default SideBar;
