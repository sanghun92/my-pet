import { Controller, FieldPath, FieldValues } from 'react-hook-form';
import * as React from 'react';
import { forwardRef } from 'react';
import { FieldPathValue, UseFormReturn } from 'react-hook-form/dist/types';
import { TextField } from '@mui/material';
import { getError } from '@/core/utils/FormFieldUtils';
import { TextFieldProps } from '@mui/material/TextField/TextField';
import DatePickerWrapper from '@/core/styles/libs/react-datepicker';
import DatePicker, { ReactDatePickerProps } from 'react-datepicker';
import { ko } from 'date-fns/locale';

type TControl<T extends FieldValues> = {
  name: FieldPath<T>;
  methods: UseFormReturn<T, T>;
  defaultValue?: FieldPathValue<T, FieldPath<T>>;
};

interface FormDateFieldProps extends Omit<ReactDatePickerProps, 'onChange' | 'onBlur' | 'value'> {
  dateFormat: 'yyyy-MM-dd';
  customFieldProps: TextFieldProps;
}

type TFormDateFieldProps<T extends FieldValues> = FormDateFieldProps & TControl<T>;

const FormDateField = <T extends FieldValues>(props: TFormDateFieldProps<T>) => {
  const { name, dateFormat, defaultValue, methods, customFieldProps } = props;
  const {
    control,
    formState: { errors },
  } = methods;
  const error = getError(name, errors);
  const CustomInput = forwardRef((props: TextFieldProps, ref) => {
    return (
      <TextField error={error.isError} helperText={error.errorMessage} inputRef={ref} autoComplete='off' {...props} />
    );
  });

  return (
    <Controller
      name={name}
      control={control}
      defaultValue={defaultValue}
      render={({ field: { onChange, onBlur, value } }) => (
        <DatePickerWrapper>
          <DatePicker
            showYearDropdown
            showMonthDropdown
            placeholderText={dateFormat}
            locale={ko}
            dateFormat={dateFormat}
            customInput={<CustomInput {...customFieldProps} />}
            onChange={onChange}
            onBlur={onBlur}
            selected={value}
          />
        </DatePickerWrapper>
      )}
    ></Controller>
  );
};
export default FormDateField;
