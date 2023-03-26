import * as React from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { useTheme } from '@mui/material/styles';
import { Box, Container, useMediaQuery } from '@mui/material';
import CustomTextField from '@/components/Field/CustomTextField';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { requestMemberChangePassword } from '@/apis/MemberApi';
import { showError, showMessage } from '@/utils/SnackbarUtils';

interface ChangePasswordForm {
  prevPassword: string;
  password: string;
  confirmPassword: string;
}

const schema = yup.object().shape({
  prevPassword: yup
    .string()
    .required(
      '이전 패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요',
    ),
  password: yup
    .string()
    .min(
      8,
      '신규 패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요',
    )
    .max(
      20,
      '신규 패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요',
    )
    .required(
      '신규 패스워드는 영문/숫자/특수문자 혼합 8~20자 이내로 입력해주세요',
    )
    .notOneOf([yup.ref('prevPassword')], '이전 패스워드와 동일합니다'),
  confirmPassword: yup
    .string()
    .required('신규 패스워드를 한번 더 입력해주세요')
    .oneOf([yup.ref('password')], '신규 패스워드와 일치하지 않습니다'),
});

const defaultValues = {
  prevPassword: '',
  password: '',
  confirmPassword: '',
};

export default function MemberChangePasswordDialog() {
  const [open, setOpen] = useState(false);
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors }, // 버전 6라면 errors라고 작성함.
  } = useForm<ChangePasswordForm>({
    defaultValues: defaultValues,
    resolver: yupResolver(schema),
  });

  const handleChangePassword = async (form: ChangePasswordForm) => {
    await requestMemberChangePassword({
      prevPassword: form.prevPassword,
      newPassword: form.password,
    })
      .then(() => {
        showMessage('비밀번호 변경 완료', '비밀번호가 변경되었습니다');
        handleClose();
      })
      .catch(errors => showError('비밀번호 변경 실패', errors));
  };

  const handleOpen = () => {
    reset(defaultValues);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Button
        type="button"
        onClick={handleOpen}
        size={'large'}
        fullWidth
        variant="contained"
      >
        비밀 번호 변경하기
      </Button>
      <Dialog
        fullScreen={fullScreen}
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle id="responsive-dialog-title">
          {'비밀번호 변경'}
        </DialogTitle>
        <DialogContent>
          <Container maxWidth="sm">
            <Box
              component="form"
              onSubmit={handleSubmit(handleChangePassword)}
              noValidate
            >
              <CustomTextField
                register={register}
                errors={errors}
                required
                fullWidth
                margin="normal"
                type="password"
                id="prevPassword"
                name="prevPassword"
                autoComplete="off"
                label="이전 패스워드"
                placeholder="영문/숫자/특수문자 혼합 8~20자"
                sx={{ mt: 1, mb: 1 }}
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
                label="신규 패스워드"
                placeholder="영문/숫자/특수문자 혼합 8~20자"
                sx={{ mt: 1, mb: 1 }}
              />
              <CustomTextField
                register={register}
                errors={errors}
                required
                fullWidth
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                label="신규 패스워드 확인"
                autoComplete="off"
                placeholder="패스워드를 한번 더 입력해주세요"
                sx={{ mt: 1, mb: 1 }}
              />
            </Box>
          </Container>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose}>
            취소
          </Button>
          <Button onClick={handleSubmit(handleChangePassword)} autoFocus>
            저장 하기
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
