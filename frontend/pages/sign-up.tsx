import { Avatar, Box, Button, Checkbox, Container, Grid } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import React from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import CustomTextField from '@/components/Field/CustomTextField';
import { DatePicker } from '@mui/x-date-pickers';
import StyledLink from '@/components/Link/StyledLink';
import Path from '@/constants/path';
import { useSignIn, useSignUp } from '@/hooks/useAuthHooks';
import StyledFormControlLabel from '@/components/FormLabel/StyledFormControlLabel';

interface SignUpForm {
  email: string;
  certificationCode: string;
  password: string;
  confirmPassword: string;
  nickname: string;
  phoneNumber: string;
  birthDay: string;
}

const defaultValues: SignUpForm = {
  email: '',
  certificationCode: '',
  password: '',
  confirmPassword: '',
  nickname: '',
  phoneNumber: '',
  birthDay: '',
};

const schema = yup.object().shape({
  email: yup.string().email().required('이메일을 입력해주세요'),
  certificationCode: yup.string().required(),
  password: yup
    .string()
    .min(8, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .max(20, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .required('패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요'),
  confirmPassword: yup
    .string()
    .required('패스워드를 한번 더 입력해주세요')
    .oneOf([yup.ref('password')]),
  nickname: yup.string().required('닉네임을 입력해주세요'),
  phoneNumber: yup
    .string()
    .required("핸드폰 번호는 '-'없이 숫자만 입력해주세요"),
});

const SignUpPage = () => {
  const { mutateAsync: signUp } = useSignUp();
  const { mutateAsync: signIn } = useSignIn();
  const methods = useForm<SignUpForm>({
    defaultValues: defaultValues,
    resolver: yupResolver(schema),
  });

  const {
    register,
    watch,
    handleSubmit,
    formState: { errors }, // 버전 6라면 errors라고 작성함.
  } = methods;

  const formData: SignUpForm = watch();

  const onSubmit = async (form: SignUpForm) => {
    await signUp(form)
      .then(() => signIn(form))
      .catch(errors => console.error(errors));
  };

  const sendEmailEmailVerification = () => {};

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
        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
          <LockOutlinedIcon />
        </Avatar>
        <FormProvider {...methods}>
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit(onSubmit)}
            sx={{ mt: 3 }}
          >
            <Grid
              container
              spacing={2}
              alignItems="top"
              justifyContent="center"
            >
              <Grid item xs={9}>
                <CustomTextField
                  register={register}
                  errors={errors}
                  required
                  fullWidth
                  id="email"
                  name="email"
                  label="Email"
                  placeholder="example@example.com"
                />
              </Grid>
              <Grid item xs={3}>
                <Button
                  type="button"
                  fullWidth
                  variant="contained"
                  sx={{ height: '56px' }}
                  onClick={sendEmailEmailVerification}
                  disabled={!formData.email}
                >
                  인증 코드 받기
                </Button>
              </Grid>
              <Grid item xs={12}>
                <CustomTextField
                  register={register}
                  errors={errors}
                  required
                  fullWidth
                  id="certificationCode"
                  name="certificationCode"
                  label="Email 인증 코드"
                  placeholder="example@example.com"
                />
              </Grid>
              <Grid item xs={12}>
                <CustomTextField
                  register={register}
                  errors={errors}
                  required
                  fullWidth
                  type="password"
                  id="password"
                  name="password"
                  label="패스워드"
                  autoComplete="off"
                  placeholder="영문/숫자/특수문자 혼합 8~20자"
                />
              </Grid>
              <Grid item xs={12}>
                <CustomTextField
                  register={register}
                  errors={errors}
                  required
                  fullWidth
                  type="password"
                  id="confirmPassword"
                  name="confirmPassword"
                  label="패스워드 확인"
                  autoComplete="off"
                  placeholder="패스워드를 한번 더 입력해주세요"
                />
              </Grid>
              <Grid item xs={12}>
                <CustomTextField
                  register={register}
                  errors={errors}
                  required
                  fullWidth
                  id="nickname"
                  name="nickname"
                  label="닉네임"
                  placeholder="2~15자 이내로 입력해주세요"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <CustomTextField
                  register={register}
                  errors={errors}
                  required
                  fullWidth
                  id="phoneNumber"
                  name="phoneNumber"
                  label="핸드폰 번호"
                  placeholder="'-'없이 숫자만 입력해주세요"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <DatePicker label="생년월일" />
              </Grid>
              <Grid item xs={12}>
                <StyledFormControlLabel
                  control={
                    <Checkbox value="allowExtraEmails" color="primary" />
                  }
                  label="이용 약관 동의"
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              size={'large'}
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              회원 가입
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <StyledLink href={Path.SIGN_IN}>
                  계정이 있으신가요? 로그인하기
                </StyledLink>
              </Grid>
            </Grid>
          </Box>
        </FormProvider>
      </Box>
    </Container>
  );
};
export default SignUpPage;
