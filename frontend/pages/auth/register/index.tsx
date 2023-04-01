// ** React Imports
import React, { MouseEvent, ReactNode } from 'react';

// ** Next Imports
import Link from 'next/link';

// ** MUI Components
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import CardContent from '@mui/material/CardContent';
import { styled, useTheme } from '@mui/material/styles';
import MuiCard, { CardProps } from '@mui/material/Card';
import { Grid } from '@mui/material';

// ** Icons Imports
import Google from 'mdi-material-ui/Google';
import Github from 'mdi-material-ui/Github';
import Twitter from 'mdi-material-ui/Twitter';
import Facebook from 'mdi-material-ui/Facebook';

// ** Configs
import themeConfig from '@/configs/themeConfig';

// ** Layout Import
import BlankLayout from '@/core/layouts/BlankLayout';

// ** Demo Imports
import router from 'next/router';
import FooterIllustrationsV1 from '@/views/pages/auth/FooterIllustration';
import * as yup from 'yup';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import Path from '@/navigation/NavigationPaths';
import { useLogin, useRegister } from '@/core/hooks/useAuthHooks';
import { FormCheckBoxField, FormDateField, FormTextField } from '@/core/components/field';
import { MemberRegisterState } from '@/core/models/AuthModels';
import { showMessage } from '@/utils/SnackbarUtils';

const schema = yup.object().shape({
  email: yup.string().email().required('이메일을 입력해주세요'),
  birthDay: yup.date(),
  password: yup
    .string()
    .min(8, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .max(20, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .required('패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요'),
  confirmPassword: yup
    .string()
    .required('패스워드를 한번 더 입력해주세요')
    .oneOf([yup.ref('password')], '패스워드가 일치하지 않습니다'),
  nickname: yup.string().required('닉네임을 입력해주세요'),
  phoneNumber: yup
    .string()
    .matches(/^[0-9]+$/, "핸드폰 번호는 '-'없이 숫자만 입력해주세요")
    .required("핸드폰 번호는 '-'없이 숫자만 입력해주세요"),
  termsAndConditions: yup.boolean().isTrue('이용 약관에 동의해야 가입할 수 있습니다'),
});

// ** Styled Components
const Card = styled(MuiCard)<CardProps>(({ theme }) => ({
  [theme.breakpoints.up('sm')]: { width: '28rem' },
}));

const LinkStyled = styled('a')(({ theme }) => ({
  fontSize: '0.875rem',
  textDecoration: 'none',
  color: theme.palette.primary.main,
}));

const RegisterPage = () => {
  // ** Hook
  const theme = useTheme();
  const { mutateAsync: registerMember } = useRegister();
  const { mutateAsync: login } = useLogin();
  const methods = useForm<MemberRegisterState>({
    resolver: yupResolver(schema),
    defaultValues: {
      email: '',
      password: '',
      confirmPassword: '',
      nickname: '',
      phoneNumber: '',
      birthDay: undefined,
      termsAndConditions: false,
    },
  });
  const { handleSubmit } = methods;

  const onSubmit = async (state: MemberRegisterState) => {
    await registerMember(state)
      .then(() => login(state))
      .then(() => {
        showMessage('회원가입', '회원가입 되었습니다');
        router.push(Path.ROOT);
      })
      .catch(errors => console.error(errors));
  };

  return (
    <Box className='content-center'>
      <Card sx={{ zIndex: 1 }}>
        <CardContent sx={{ padding: theme => `${theme.spacing(12, 9, 7)} !important` }}>
          <Box sx={{ mb: 8, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <svg
              width={35}
              height={29}
              version='1.1'
              viewBox='0 0 30 23'
              xmlns='http://www.w3.org/2000/svg'
              xmlnsXlink='http://www.w3.org/1999/xlink'
            >
              <g stroke='none' strokeWidth='1' fill='none' fillRule='evenodd'>
                <g id='Artboard' transform='translate(-95.000000, -51.000000)'>
                  <g id='logo' transform='translate(95.000000, 50.000000)'>
                    <path
                      id='Combined-Shape'
                      fill={theme.palette.primary.main}
                      d='M30,21.3918362 C30,21.7535219 29.9019196,22.1084381 29.7162004,22.4188007 C29.1490236,23.366632 27.9208668,23.6752135 26.9730355,23.1080366 L26.9730355,23.1080366 L23.714971,21.1584295 C23.1114106,20.7972624 22.7419355,20.1455972 22.7419355,19.4422291 L22.7419355,19.4422291 L22.741,12.7425689 L15,17.1774194 L7.258,12.7425689 L7.25806452,19.4422291 C7.25806452,20.1455972 6.88858935,20.7972624 6.28502902,21.1584295 L3.0269645,23.1080366 C2.07913318,23.6752135 0.850976404,23.366632 0.283799571,22.4188007 C0.0980803893,22.1084381 2.0190442e-15,21.7535219 0,21.3918362 L0,3.58469444 L0.00548573643,3.43543209 L0.00548573643,3.43543209 L0,3.5715689 C3.0881846e-16,2.4669994 0.8954305,1.5715689 2,1.5715689 C2.36889529,1.5715689 2.73060353,1.67359571 3.04512412,1.86636639 L15,9.19354839 L26.9548759,1.86636639 C27.2693965,1.67359571 27.6311047,1.5715689 28,1.5715689 C29.1045695,1.5715689 30,2.4669994 30,3.5715689 L30,3.5715689 Z'
                    />
                    <polygon
                      id='Rectangle'
                      opacity='0.077704'
                      fill={theme.palette.common.black}
                      points='0 8.58870968 7.25806452 12.7505183 7.25806452 16.8305646'
                    />
                    <polygon
                      id='Rectangle'
                      opacity='0.077704'
                      fill={theme.palette.common.black}
                      points='0 8.58870968 7.25806452 12.6445567 7.25806452 15.1370162'
                    />
                    <polygon
                      id='Rectangle'
                      opacity='0.077704'
                      fill={theme.palette.common.black}
                      points='22.7419355 8.58870968 30 12.7417372 30 16.9537453'
                      transform='translate(26.370968, 12.771227) scale(-1, 1) translate(-26.370968, -12.771227) '
                    />
                    <polygon
                      id='Rectangle'
                      opacity='0.077704'
                      fill={theme.palette.common.black}
                      points='22.7419355 8.58870968 30 12.6409734 30 15.2601969'
                      transform='translate(26.370968, 11.924453) scale(-1, 1) translate(-26.370968, -11.924453) '
                    />
                    <path
                      id='Rectangle'
                      fillOpacity='0.15'
                      fill={theme.palette.common.white}
                      d='M3.04512412,1.86636639 L15,9.19354839 L15,9.19354839 L15,17.1774194 L0,8.58649679 L0,3.5715689 C3.0881846e-16,2.4669994 0.8954305,1.5715689 2,1.5715689 C2.36889529,1.5715689 2.73060353,1.67359571 3.04512412,1.86636639 Z'
                    />
                    <path
                      id='Rectangle'
                      fillOpacity='0.35'
                      fill={theme.palette.common.white}
                      transform='translate(22.500000, 8.588710) scale(-1, 1) translate(-22.500000, -8.588710) '
                      d='M18.0451241,1.86636639 L30,9.19354839 L30,9.19354839 L30,17.1774194 L15,8.58649679 L15,3.5715689 C15,2.4669994 15.8954305,1.5715689 17,1.5715689 C17.3688953,1.5715689 17.7306035,1.67359571 18.0451241,1.86636639 Z'
                    />
                  </g>
                </g>
              </g>
            </svg>
            <Typography
              variant='h6'
              sx={{
                ml: 3,
                lineHeight: 1,
                fontWeight: 600,
                textTransform: 'uppercase',
                fontSize: '1.5rem !important',
              }}
            >
              {themeConfig.templateName}
            </Typography>
          </Box>
          <Box component='form' noValidate onSubmit={handleSubmit(onSubmit)} sx={{ mt: 3 }}>
            <Grid container spacing={2} alignItems='top' justifyContent='center'>
              <Grid item xs={12}>
                <FormTextField
                  methods={methods}
                  required
                  fullWidth
                  id='email'
                  name='email'
                  label='Email'
                  placeholder='example@example.com'
                />
              </Grid>
              <Grid item xs={12}>
                <FormTextField
                  methods={methods}
                  required
                  fullWidth
                  type='password'
                  id='password'
                  name='password'
                  label='패스워드'
                  autoComplete='off'
                  placeholder='영문/숫자/특수문자 혼합 8~20자'
                />
              </Grid>
              <Grid item xs={12}>
                <FormTextField
                  methods={methods}
                  required
                  fullWidth
                  type='password'
                  id='confirmPassword'
                  name='confirmPassword'
                  label='패스워드 확인'
                  autoComplete='off'
                  placeholder='패스워드를 한번 더 입력해주세요'
                />
              </Grid>
              <Grid item xs={12}>
                <FormTextField
                  methods={methods}
                  required
                  fullWidth
                  id='nickname'
                  name='nickname'
                  label='닉네임'
                  placeholder='2~15자 이내로 입력해주세요'
                />
              </Grid>
              <Grid item xs={12}>
                <FormTextField
                  methods={methods}
                  required
                  fullWidth
                  id='phoneNumber'
                  name='phoneNumber'
                  label='핸드폰 번호'
                  placeholder="'-'없이 숫자만 입력해주세요"
                />
              </Grid>
              <Grid item xs={12}>
                <FormDateField
                  methods={methods}
                  dateFormat='yyyy-MM-dd'
                  name='birthDay'
                  customFieldProps={{
                    id: 'birthDay',
                    label: '생일',
                    fullWidth: true,
                  }}
                />
              </Grid>
            </Grid>
            <Divider sx={{ my: 4 }} />
            <Grid item xs={12}>
              <FormCheckBoxField methods={methods} name='termsAndConditions' label='이용 약관 동의' />
            </Grid>
            <Button fullWidth size='large' type='submit' variant='contained' sx={{ mt: 3, mb: 7 }}>
              회원가입
            </Button>
          </Box>
          <Box sx={{ display: 'flex', alignItems: 'center', flexWrap: 'wrap', justifyContent: 'center' }}>
            <Typography variant='body2' sx={{ marginRight: 2 }}>
              계정이 있으신가요?
            </Typography>
            <Typography variant='body2'>
              <Link passHref legacyBehavior href={Path.AUTH.LOGIN}>
                <LinkStyled>로그인하기</LinkStyled>
              </Link>
            </Typography>
          </Box>
          <Divider sx={{ my: 5 }}>or</Divider>
          <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <Link href='/' passHref legacyBehavior>
              <IconButton component='a' onClick={(e: MouseEvent<HTMLElement>) => e.preventDefault()}>
                <Facebook sx={{ color: '#497ce2' }} />
              </IconButton>
            </Link>
            <Link href='/' passHref legacyBehavior>
              <IconButton component='a' onClick={(e: MouseEvent<HTMLElement>) => e.preventDefault()}>
                <Twitter sx={{ color: '#1da1f2' }} />
              </IconButton>
            </Link>
            <Link href='/' passHref legacyBehavior>
              <IconButton component='a' onClick={(e: MouseEvent<HTMLElement>) => e.preventDefault()}>
                <Github
                  sx={{ color: theme => (theme.palette.mode === 'light' ? '#272727' : theme.palette.grey[300]) }}
                />
              </IconButton>
            </Link>
            <Link href='/' passHref legacyBehavior>
              <IconButton component='a' onClick={(e: MouseEvent<HTMLElement>) => e.preventDefault()}>
                <Google sx={{ color: '#db4437' }} />
              </IconButton>
            </Link>
          </Box>
          <Box sx={{ mt: 4, display: 'flex', alignItems: 'center', flexWrap: 'wrap', justifyContent: 'center' }}>
            <Typography variant='body2'>
              <Link passHref legacyBehavior href={Path.ROOT}>
                <LinkStyled>다음에 가입 할게요</LinkStyled>
              </Link>
            </Typography>
          </Box>
        </CardContent>
      </Card>
      <FooterIllustrationsV1 />
    </Box>
  );
};

RegisterPage.getLayout = (page: ReactNode) => <BlankLayout>{page}</BlankLayout>;

export default RegisterPage;
