import { requestEmailVerification } from '@/apis/AuthApi';
import { Container, Divider, Typography } from '@mui/material';
import { GetServerSideProps, InferGetServerSidePropsType } from 'next';

const EmailVerificationPage = ({
  error,
}: InferGetServerSidePropsType<typeof getServerSideProps>) => {
  return (
    <Container
      disableGutters
      maxWidth="sm"
      component="main"
      sx={{ pt: 8, pb: 6 }}
    >
      <Typography
        component="h1"
        variant="h3"
        align="center"
        color="text.primary"
        gutterBottom
      >
        가입 인증
      </Typography>
      <Divider color="dark" />
      <br />
      {error && (
        <Typography component="h2" variant="h3" color="text.primary">
          정상적인 인증코드가 아닙니다.
        </Typography>
      )}
      {!error && (
        <Typography component="h2" variant="h3" color="text.primary">
          인증되었습니다.
        </Typography>
      )}
    </Container>
  );
};
export default EmailVerificationPage;

export const getServerSideProps: GetServerSideProps = async context => {
  const code = context.query.code;
  let error = false;
  if (typeof code === 'string') {
    await requestEmailVerification(code).catch(() => (error = true));
  } else {
    error = true;
  }
  return {
    props: {
      error: error,
    }, // will be passed to the page component as props
  };
};
