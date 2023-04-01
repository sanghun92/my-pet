// ** React Imports
import React, { ChangeEvent, ElementType, useState } from 'react'; // ** MUI Imports
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Alert from '@mui/material/Alert';
import { styled } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import AlertTitle from '@mui/material/AlertTitle';
import IconButton from '@mui/material/IconButton';
import CardContent from '@mui/material/CardContent';
import Button, { ButtonProps } from '@mui/material/Button'; // ** Icons Imports // ** Icons Imports
import Close from 'mdi-material-ui/Close';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useForm } from 'react-hook-form';
import { MemberModel } from '@/core/models/MemberModels';
import { FormDateField, FormTextField } from '@/core/components/field';
import { ContentLoading } from '@/core/components/loading';
import { useMemberQuery, useUpdateMember } from '@/core/hooks/useMemberHooks';
import { showMessage } from '@/utils/SnackbarUtils';
import { useSendEmailVerificationCode } from '@/core/hooks/useAuthHooks';

const ImgStyled = styled('img')(({ theme }) => ({
  width: 120,
  height: 120,
  marginRight: theme.spacing(6.25),
  borderRadius: theme.shape.borderRadius,
}));

const ButtonStyled = styled(Button)<ButtonProps & { component?: ElementType; htmlFor?: string }>(({ theme }) => ({
  [theme.breakpoints.down('sm')]: {
    width: '100%',
    textAlign: 'center',
  },
}));

const ResetButtonStyled = styled(Button)<ButtonProps>(({ theme }) => ({
  marginLeft: theme.spacing(4.5),
  [theme.breakpoints.down('sm')]: {
    width: '100%',
    marginLeft: 0,
    textAlign: 'center',
    marginTop: theme.spacing(4),
  },
}));

const schema = yup.object().shape({
  profile: yup.object().shape({
    nickname: yup
      .string()
      .min(2, '닉네임은 2자 이상, 20자 이하의 닉네임이어야 합니다')
      .max(20, '닉네임은 2자 이상, 20자 이하의 닉네임이어야 합니다')
      .required('닉네임을 입력해주세요'),
    phoneNumber: yup.string().required("핸드폰 번호는 '-'없이 숫자만 입력해주세요"),
    birthDay: yup.date(),
  }),
});

const TabMemberProfile = () => {
  // ** State
  const { isLoading, data: memberState } = useMemberQuery();
  const [imgSrc, setImgSrc] = useState<string>('/images/avatars/1.png');
  const { mutateAsync: updateMember } = useUpdateMember();
  const { mutateAsync: sendEmailVerificationCode } = useSendEmailVerificationCode();
  const methods = useForm<MemberModel>({
    defaultValues: memberState,
    resolver: yupResolver(schema),
  });
  const { handleSubmit, reset } = methods;

  const onSubmit = async (member: MemberModel) => {
    await updateMember(member)
      .then(() => showMessage('프로필', '저장에 성공하였습니다'))
      .catch(errors => console.error(errors));
  };

  const onChange = (file: ChangeEvent) => {
    const reader = new FileReader();
    const { files } = file.target as HTMLInputElement;
    if (files && files.length !== 0) {
      reader.onload = () => setImgSrc(reader.result as string);

      reader.readAsDataURL(files[0]);
    }
  };

  const handleSendEmailVerificationCode = async () => {
    await sendEmailVerificationCode(memberState!.profile.email)
      .then(() => showMessage('메일 인증', '인증 코드가 전송되었습니다'))
      .catch(errors => console.error(errors));
  };

  if (!memberState) {
    return <ContentLoading open={isLoading} />;
  }

  return (
    <CardContent>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Grid container spacing={7}>
          <Grid item xs={12} sx={{ marginTop: 4.8, marginBottom: 3 }}>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              <ImgStyled src={imgSrc} alt='Profile Pic' />
              <Box>
                <ButtonStyled component='label' variant='contained' htmlFor='account-settings-upload-image'>
                  Upload New Photo
                  <input
                    hidden
                    type='file'
                    onChange={onChange}
                    accept='image/png, image/jpeg'
                    id='account-settings-upload-image'
                  />
                </ButtonStyled>
                <ResetButtonStyled color='error' variant='outlined' onClick={() => setImgSrc('/images/avatars/1.png')}>
                  Reset
                </ResetButtonStyled>
                <Typography variant='body2' sx={{ marginTop: 5 }}>
                  Allowed PNG or JPEG. Max size of 800K.
                </Typography>
              </Box>
            </Box>
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              id='email'
              name='profile.email'
              label='Email (Read Only)'
              defaultValue={memberState.profile.email}
              InputProps={{
                readOnly: true,
              }}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <FormTextField
              methods={methods}
              fullWidth
              id='nickname'
              name='profile.nickname'
              label='닉네임'
              placeholder='2~20자 이내로 입력해주세요'
              defaultValue={memberState.profile.nickname}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <FormTextField
              methods={methods}
              fullWidth
              id='phoneNumber'
              name='profile.phoneNumber'
              label='핸드폰 번호'
              placeholder="'-'없이 숫자만 입력해주세요"
              defaultValue={memberState.profile.phoneNumber}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <FormDateField
              methods={methods}
              dateFormat='yyyy-MM-dd'
              name='profile.birthDay'
              defaultValue={memberState.profile.birthDay}
              customFieldProps={{
                id: 'birthDay',
                label: '생일',
                fullWidth: true,
              }}
            />
          </Grid>

          {memberState.emailVerification?.isVerified ? null : (
            <Grid item xs={12} sx={{ mb: 3 }}>
              <Alert
                severity='warning'
                sx={{ '& a': { fontWeight: 400 } }}
                action={
                  <IconButton size='small' color='inherit' aria-label='close'>
                    <Close fontSize='inherit' />
                  </IconButton>
                }
              >
                <AlertTitle>이메일 인증이 되어있지 않습니다.</AlertTitle>
                <Button onClick={handleSendEmailVerificationCode}>인증 메일 재전송</Button>
              </Alert>
            </Grid>
          )}

          <Grid item xs={12}>
            <Button type='submit' variant='contained' sx={{ marginRight: 3.5 }}>
              저장
            </Button>
            <Button type='reset' variant='outlined' color='secondary' onClick={() => reset()}>
              초기화
            </Button>
          </Grid>
        </Grid>
      </form>
    </CardContent>
  );
};

export default TabMemberProfile;
