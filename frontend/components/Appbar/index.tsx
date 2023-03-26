import React, { FC, ReactElement } from 'react';
import { AppBar, Button, Link, Toolbar, Typography } from '@mui/material';
import Path from '@/constants/path';
import { useRecoilValue } from 'recoil';
import { authState } from '@/recoil/auth/atoms';
import { useRouter } from 'next/router';

const Appbar: FC = (): ReactElement => {
  const router = useRouter();
  const auth = useRecoilValue(authState);

  return (
    <AppBar
      position="static"
      color="default"
      elevation={0}
      sx={{ borderBottom: theme => `1px solid ${theme.palette.divider}` }}
    >
      <Toolbar sx={{ flexWrap: 'wrap' }}>
        <Typography variant="h6" color="inherit" noWrap sx={{ flexGrow: 1 }}>
          Company name
        </Typography>
        <nav>
          <Link
            variant="button"
            color="text.primary"
            href="#"
            sx={{ my: 1, mx: 1.5 }}
          >
            Features
          </Link>
          <Link
            variant="button"
            color="text.primary"
            href="#"
            sx={{ my: 1, mx: 1.5 }}
          >
            Enterprise
          </Link>
          <Link
            variant="button"
            color="text.primary"
            href="#"
            sx={{ my: 1, mx: 1.5 }}
          >
            Support
          </Link>
        </nav>
        {!auth?.isLoggedIn && (
          <Button
            href={Path.SIGN_IN}
            variant="outlined"
            sx={{ my: 1, mx: 1.5 }}
          >
            로그인
          </Button>
        )}
        {auth?.isLoggedIn && (
          <Button
            href={Path.LOG_OUT}
            variant="outlined"
            sx={{ my: 1, mx: 1.5 }}
          >
            로그아웃
          </Button>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default Appbar;
