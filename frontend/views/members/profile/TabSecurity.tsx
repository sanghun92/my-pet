// ** React Imports
import React, { useState } from 'react'; // ** MUI Imports
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import CardContent from '@mui/material/CardContent';
import InputAdornment from '@mui/material/InputAdornment'; // ** Icons Imports // ** Icons Imports
import EyeOutline from 'mdi-material-ui/EyeOutline';
import EyeOffOutline from 'mdi-material-ui/EyeOffOutline';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { FormTextField } from '@/core/components/field';
import { useChangePassword } from '@/core/hooks/useMemberHooks';
import { showMessage } from '@/utils/SnackbarUtils';

interface MemberSecurityState {
  showCurrentPassword: boolean;
  showNewPassword: boolean;
  showConfirmNewPassword: boolean;
}

interface MemberSecurityForm {
  currentPassword: string;
  newPassword: string;
  confirmNewPassword: string;
}

const schema = yup.object().shape({
  currentPassword: yup
    .string()
    .min(8, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .max(20, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .required('패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요'),
  newPassword: yup
    .string()
    .min(8, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .max(20, '패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요')
    .required('패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요'),
  confirmNewPassword: yup
    .string()
    .required('패스워드를 한번 더 입력해주세요')
    .oneOf([yup.ref('newPassword')], '패스워드가 일치하지 않습니다'),
});

const TabSecurity = () => {
  // ** States
  const [memberSecurityState, setMemberSecurityState] = useState<MemberSecurityState>({
    showCurrentPassword: false,
    showNewPassword: false,
    showConfirmNewPassword: false,
  });

  const methods = useForm<MemberSecurityForm>({
    resolver: yupResolver(schema),
    defaultValues: {
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
    },
  });

  const { mutateAsync: changePassword } = useChangePassword();
  const { handleSubmit } = methods;

  const onSubmit = async (form: MemberSecurityForm) => {
    await changePassword({
      password: form.currentPassword,
      newPassword: form.newPassword,
    })
      .then(() => showMessage('패스워드', '변경 완료'))
      .catch(errors => console.error(errors));
  };

  const handleClickShowCurrentPassword = () => {
    setMemberSecurityState({ ...memberSecurityState, showCurrentPassword: !memberSecurityState.showCurrentPassword });
  };

  const handleClickShowNewPassword = () => {
    setMemberSecurityState({ ...memberSecurityState, showNewPassword: !memberSecurityState.showNewPassword });
  };

  const handleClickShowConfirmNewPassword = () => {
    setMemberSecurityState({
      ...memberSecurityState,
      showConfirmNewPassword: !memberSecurityState.showConfirmNewPassword,
    });
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <CardContent sx={{ paddingBottom: 0 }}>
        <Grid container spacing={5}>
          <Grid item xs={12} sm={6}>
            <Grid container spacing={5}>
              <Grid item xs={12} sx={{ marginTop: 4.75 }}>
                <FormTextField
                  methods={methods}
                  fullWidth
                  id='currentPassword'
                  name='currentPassword'
                  label='현재 비밀번호'
                  placeholder='2~20자 이내로 입력해주세요'
                  type={memberSecurityState.showCurrentPassword ? 'text' : 'password'}
                  InputProps={{
                    endAdornment: (
                      <InputAdornment position='end'>
                        <IconButton
                          edge='end'
                          aria-label='toggle password visibility'
                          onClick={handleClickShowCurrentPassword}
                        >
                          {memberSecurityState.showCurrentPassword ? <EyeOutline /> : <EyeOffOutline />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>

              <Grid item xs={12} sx={{ marginTop: 6 }}>
                <FormTextField
                  methods={methods}
                  fullWidth
                  id='newPassword'
                  name='newPassword'
                  label='신규 비밀번호'
                  placeholder='2~20자 이내로 입력해주세요'
                  type={memberSecurityState.showNewPassword ? 'text' : 'password'}
                  InputProps={{
                    endAdornment: (
                      <InputAdornment position='end'>
                        <IconButton
                          edge='end'
                          aria-label='toggle password visibility'
                          onClick={handleClickShowNewPassword}
                        >
                          {memberSecurityState.showNewPassword ? <EyeOutline /> : <EyeOffOutline />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>

              <Grid item xs={12}>
                <FormTextField
                  methods={methods}
                  fullWidth
                  id='confirmNewPassword'
                  name='confirmNewPassword'
                  label='신규 비밀번호 재입력'
                  placeholder='2~20자 이내로 입력해주세요'
                  type={memberSecurityState.showConfirmNewPassword ? 'text' : 'password'}
                  InputProps={{
                    endAdornment: (
                      <InputAdornment position='end'>
                        <IconButton
                          edge='end'
                          aria-label='toggle password visibility'
                          onClick={handleClickShowConfirmNewPassword}
                        >
                          {memberSecurityState.showConfirmNewPassword ? <EyeOutline /> : <EyeOffOutline />}
                        </IconButton>
                      </InputAdornment>
                    ),
                  }}
                />
              </Grid>
            </Grid>
          </Grid>

          <Grid
            item
            sm={6}
            xs={12}
            sx={{ display: 'flex', marginTop: [7.5, 2.5], alignItems: 'center', justifyContent: 'center' }}
          >
            <img width={183} alt='avatar' height={256} src='/images/pages/pose-m-1.png' />
          </Grid>
        </Grid>
      </CardContent>

      <Divider sx={{ margin: 0 }} />

      <CardContent>
        {/*<Box sx={{ mt: 1.75, display: 'flex', alignItems: 'center' }}>
          <KeyOutline sx={{ marginRight: 3 }} />
          <Typography variant='h6'>Two-factor authentication</Typography>
        </Box>

        <Box sx={{ mt: 5.75, display: 'flex', justifyContent: 'center' }}>
          <Box
            sx={{
              maxWidth: 368,
              display: 'flex',
              textAlign: 'center',
              alignItems: 'center',
              flexDirection: 'column',
            }}
          >
            <Avatar
              variant='rounded'
              sx={{ width: 48, height: 48, color: 'common.white', backgroundColor: 'primary.main' }}
            >
              <LockOpenOutline sx={{ fontSize: '1.75rem' }} />
            </Avatar>
            <Typography sx={{ fontWeight: 600, marginTop: 3.5, marginBottom: 3.5 }}>
              Two factor authentication is not enabled yet.
            </Typography>
            <Typography variant='body2'>
              Two-factor authentication adds an additional layer of security to your account by requiring more than just
              a password to log in. Learn more.
            </Typography>
          </Box>
        </Box>*/}

        <Box sx={{ mt: 11 }}>
          <Button type='submit' variant='contained' sx={{ marginRight: 3.5 }}>
            저장
          </Button>
        </Box>
      </CardContent>
    </form>
  );
};
export default TabSecurity;
