import Appbar from '@/components/Appbar';
import SideBar from '@/components/SideBar';
import { Box, Container } from '@mui/material';
import Footer from '@/components/Footer';
import { PropsWithChildren } from 'react';

export const excludePage = [];

const AppLayout = ({ children }: PropsWithChildren) => {
  return (
    <>
      <Appbar />
      <SideBar />
      <Box
        component="main"
        sx={{ flexGrow: 1, color: 'background.default', p: 3 }}
      >
        <Container component="main">{children}</Container>
      </Box>
      <Footer />
    </>
  );
};
export default AppLayout;
