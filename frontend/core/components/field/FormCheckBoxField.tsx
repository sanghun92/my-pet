import { FieldPath, FieldValues } from 'react-hook-form';
import { Checkbox, FormHelperText } from '@mui/material';
import * as React from 'react';
import StyledFormControlLabel from '@/core/components/field/StyledFormControlLabel';
import { UseFormReturn } from 'react-hook-form/dist/types';
import { getError } from '@/core/utils/FormFieldUtils';

type TControl<T extends FieldValues> = {
  name: FieldPath<T>;
  methods: UseFormReturn<T, T>;
};

type TCheckBoxFieldProps<T extends FieldValues> = TControl<T> & {
  label: string;
  required?: boolean;
};

const FormCheckBoxField = <T extends FieldValues>({ name, label, methods, ...props }: TCheckBoxFieldProps<T>) => {
  const {
    register,
    formState: { errors },
  } = methods;
  const error = getError(name, errors);
  return (
    <>
      <StyledFormControlLabel label={label} control={<Checkbox {...register(name)} {...props} />} />
      <FormHelperText disabled={error.isError} error={true}>
        {error.errorMessage}
      </FormHelperText>
    </>
  );
};
export default FormCheckBoxField;
