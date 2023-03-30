import { CircularProgress } from '@mui/material';
import React from 'react';
import Typography from '@mui/material/Typography';

interface ContentLoadingProp {
  open: boolean;
}

const ContentLoading = ({ open }: ContentLoadingProp) => {
  return (
    <>
      {open && (
        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        >
          <Typography variant='h4' sx={{ mr: 4, color: 'text.primary' }}>
            Loading...
          </Typography>
          <CircularProgress />
        </div>
      )}
    </>
  );
};
export default ContentLoading;
