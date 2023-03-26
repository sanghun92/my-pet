import React from 'react';
import Head from 'next/head';
import { NextPage } from 'next';
import { Box, Typography } from '@mui/material';

import Link from 'next/link';

const Custom404Page: NextPage = () => {
  return (
    <>
      <Head>
        <title>해당 페이지를 찾을 수 없습니다 | 404</title>
      </Head>
      <Box>
        <Typography
          variant="h5"
          align="center"
          color="text.secondary"
          component="p"
        >
          This page does not exist <br />
          <Link href="/">Return to Home Page</Link>
        </Typography>
      </Box>
    </>
  );
};

export default Custom404Page;
