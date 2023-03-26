import React from 'react';
import { useRouter } from 'next/router';
import { useSignIn } from '@/hooks/useAuthHooks';
import { useForm } from 'react-hook-form';
import { SignInForm } from '@/models/AuthModels';
import Path from '@/constants/path';
import { Box, Button, Checkbox, Container, Grid } from '@mui/material';
import CustomTextField from '@/components/Field/CustomTextField';
import StyledFormControlLabel from '@/components/FormLabel/StyledFormControlLabel';
import StyledLink from '@/components/Link/StyledLink';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';

const SignInPage = () => {
  return <SignInForm />;
};

const schema = yup.object().shape({
  email: yup.string().email().required('이메일을 입력해주세요'),
  password: yup
    .string()
    .min(8, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .max(20, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .required('패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요'),
});

const SignInForm = () => {
  const router = useRouter();
  const { mutateAsync: signIn } = useSignIn();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<SignInForm>({ resolver: yupResolver(schema) });
  const onSubmit = async (form: SignInForm) => {
    await signIn(form)
      .then(() => router.push(Path.ROOT))
      .catch(errors => console.error(errors));
  };

  return (
    <Container maxWidth="sm">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Box
          component="form"
          onSubmit={handleSubmit(onSubmit)}
          noValidate
          sx={{ mt: 1 }}
        >
          <CustomTextField
            register={register}
            errors={errors}
            required
            fullWidth
            margin="normal"
            id="email"
            name="email"
            label="Email"
            placeholder="example@example.com"
          />
          <CustomTextField
            register={register}
            errors={errors}
            required
            fullWidth
            margin="normal"
            type="password"
            id="password"
            name="password"
            autoComplete="off"
            label="패스워드"
            placeholder="영문/숫자/특수문자 혼합 8~20자"
          />
          <StyledFormControlLabel
            control={<Checkbox value="remember" color="primary" />}
            sx={{
              color: theme => theme.palette.primary.light,
            }}
            label="계정 정보 기억하기"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            로그인
          </Button>
          <Grid container>
            <Grid item xs>
              <StyledLink href={Path.SIGN_UP}>
                계정이 없으신가요? 회원가입하기
              </StyledLink>
            </Grid>
            <Grid item>
              <StyledLink href={Path.MEMBER_RESET_PASSWORD}>
                패스워드 초기화
              </StyledLink>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
};

export default SignInPage;
