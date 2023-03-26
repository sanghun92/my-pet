import React from 'react';
import {
  Box,
  Button,
  Container,
  Divider,
  Grid,
  TextField,
} from '@mui/material';
import CustomTextField from '@/components/Field/CustomTextField';
import { Controller, useForm } from 'react-hook-form';
import StyledLink from '@/components/Link/StyledLink';
import Path from '@/constants/path';
import { useMemberQuery, useUpdateMember } from '@/hooks/useMemberHooks';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { MemberModel } from '@/models/MemberModels';
import { jsx } from '@emotion/react';
import { showMessage } from '@/utils/SnackbarUtils';
import { DatePicker } from '@mui/x-date-pickers';
import { format } from 'date-fns';
import { ko } from 'date-fns/locale';
import MemberChangePasswordDialog from '@/components/Dialog/MemberChangePasswordDialog';
import IntrinsicAttributes = jsx.JSX.IntrinsicAttributes;

interface MemberDataType {
  member: IntrinsicAttributes & MemberModel;
}

const MemberProfilePage = () => {
  const { data } = useMemberQuery({
    retry: 1,
  });

  if (data) {
    return <MemberProfileForm member={data} />;
  }

  return 'loading...';
};

const schema = yup.object().shape({
  nickname: yup
    .string()
    .min(2, '닉네임은 2자 이상, 20자 이하의 닉네임이어야 합니다')
    .max(20, '닉네임은 2자 이상, 20자 이하의 닉네임이어야 합니다')
    .required('닉네임을 입력해주세요'),
  phoneNumber: yup
    .string()
    .required("핸드폰 번호는 '-'없이 숫자만 입력해주세요"),
});

const MemberProfileForm = ({ member }: MemberDataType) => {
  const { mutateAsync: updateMember } = useUpdateMember();
  const defaultValues: MemberModel = {
    email: member?.email,
    nickname: member?.nickname,
    phoneNumber: member?.phoneNumber,
    birthDay: member?.birthDay,
  };

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<MemberModel>({
    defaultValues: defaultValues,
    resolver: yupResolver(schema),
  });

  const onSubmit = async (member: MemberModel) => {
    await updateMember({
      id: member.id as number,
      nickname: member.nickname as string,
      phoneNumber: member.phoneNumber as string,
      birthDay: member.birthDay
        ? format(member.birthDay, 'yyyy-MM-dd', { locale: ko })
        : undefined,
    })
      .then(() => showMessage('프로필', '저장에 성공하였습니다'))
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
          noValidate
          onSubmit={handleSubmit(onSubmit)}
          sx={{ mt: 3 }}
        >
          <Grid container spacing={2} alignItems="top" justifyContent="center">
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="email"
                name="email"
                label="Email (Read Only)"
                defaultValue={member.email}
                InputProps={{
                  readOnly: true,
                }}
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
                placeholder="2~20자 이내로 입력해주세요"
              />
            </Grid>
            <Grid item xs={12} sm={7}>
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
            <Grid item xs={12} sm={5}>
              <Controller
                control={control}
                name="birthDay"
                render={({ field }) => (
                  <DatePicker
                    label="생일"
                    format="yyyy-MM-dd"
                    defaultValue={field.value}
                    onChange={date => field.onChange(date)}
                  />
                )}
              />
            </Grid>
          </Grid>
          <Divider color="light" sx={{ mt: 2, mb: 2 }} />
          <MemberChangePasswordDialog />
          <Divider color="light" sx={{ mt: 2, mb: 2 }} />
          <Button type="submit" size={'large'} fullWidth variant="contained">
            저장 하기
          </Button>
        </Box>
        <Grid container sx={{ mt: 2 }}>
          <Grid item xs>
            <StyledLink href={Path.SIGN_IN}>회원 탈퇴</StyledLink>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
};

export default MemberProfilePage;
