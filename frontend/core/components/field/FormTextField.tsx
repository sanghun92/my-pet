import { FieldPath, FieldValues } from 'react-hook-form';
import { TextField, TextFieldProps } from '@mui/material';
import * as React from 'react';
import { UseFormReturn } from 'react-hook-form/dist/types';
import { getError } from '@/core/utils/FormFieldUtils';

type TControl<T extends FieldValues> = {
  name: FieldPath<T>;
  methods: UseFormReturn<T, T>;
};

type TCustomTextFieldProps<T extends FieldValues> = TextFieldProps & TControl<T>;

const FormTextField = <T extends FieldValues>({ name, methods, ...props }: TCustomTextFieldProps<T>) => {
  const {
    register,
    formState: { errors },
  } = methods;
  const error = getError(name, errors);
  return <TextField {...register(name)} error={error.isError} helperText={error.errorMessage} {...props} />;
};
export default FormTextField;
