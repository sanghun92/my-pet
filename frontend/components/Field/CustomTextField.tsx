import { FieldPath, FieldValues } from 'react-hook-form';
import { TextField, TextFieldProps } from '@mui/material';
import { UseFormRegister } from 'react-hook-form/dist/types/form';
import { FieldErrors, Message } from 'react-hook-form/dist/types/errors';
import * as React from 'react';

type TControl<T extends FieldValues> = {
  name: FieldPath<T>;
  register: UseFormRegister<T>;
  errors: FieldErrors<T>;
};

type TCustomTextFieldProps<T extends FieldValues> = TextFieldProps &
  TControl<T>;

const CustomTextField = <T extends FieldValues>({
  name,
  register,
  errors,
  ...props
}: TCustomTextFieldProps<T>) => {
  let isError = false;
  let errorMessage: Message | undefined;
  if (errors && errors.hasOwnProperty(name)) {
    isError = true;
    errorMessage = errors[name]?.message?.toString();
  }

  return (
    <TextField
      {...register(name)}
      error={isError}
      helperText={errorMessage}
      {...props}
    />
  );
};
export default CustomTextField;
