import { Backdrop, CircularProgress } from '@mui/material';
import React from 'react';

interface BackDropLoadingProp {
  open: boolean;
}

const BackDropLoading = ({ open }: BackDropLoadingProp) => {
  return (
    <Backdrop sx={{ color: '#fff', zIndex: theme => theme.zIndex.drawer + 1 }} open={open}>
      <CircularProgress color='inherit' />
    </Backdrop>
  );
};
export default BackDropLoading;
