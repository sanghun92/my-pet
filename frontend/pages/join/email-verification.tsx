import { requestVerifyEmailVerificationCode } from '@/apis/AuthApi';
import { Container, Divider, Typography } from '@mui/material';
import { ReactNode } from 'react';
import BlankLayout from '@/core/layouts/BlankLayout';
import { useRecoilState } from 'recoil';
import { authState } from '@/core/recoil/auth/atoms';
import { NextPageContext } from 'next/dist/shared/lib/utils';

const EmailVerificationPage = ({ error }: any) => {
  const [authRecoilState, setAuthRecoilState] = useRecoilState(authState);

  if (!error) {
    setAuthRecoilState({
      ...authRecoilState,
      isVerifiedEmail: true,
    });
  }

  return (
    <Container disableGutters maxWidth='sm' component='main' sx={{ pt: 8, pb: 6 }}>
      <Typography component='h1' variant='h3' align='center' color='text.primary' gutterBottom>
        가입 인증
      </Typography>
      <Divider color='dark' />
      <br />
      {error && (
        <Typography component='h2' variant='h3' color='text.primary'>
          정상적인 인증코드가 아닙니다.
        </Typography>
      )}
      {!error && (
        <Typography component='h2' variant='h3' color='text.primary'>
          인증되었습니다.
        </Typography>
      )}
    </Container>
  );
};

EmailVerificationPage.getLayout = (page: ReactNode) => <BlankLayout>{page}</BlankLayout>;

export default EmailVerificationPage;

export const getServerSideProps = async (context: NextPageContext) => {
  const code = context.query.code;
  let error = false;
  if (typeof code === 'string') {
    await requestVerifyEmailVerificationCode(code).catch(() => (error = true));
  } else {
    error = true;
  }
  return {
    props: {
      error: error,
    }, // will be passed to the page component as props
  };
};
