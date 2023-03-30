import { FieldErrors, Message } from 'react-hook-form/dist/types/errors';
import { FieldValues } from 'react-hook-form';

export const getError = <T extends FieldValues>(name: string, errors: FieldErrors<T>) => {
  let isError = false;
  let errorMessage: Message | undefined;
  if (errors && errors.hasOwnProperty(name)) {
    isError = true;
    errorMessage = errors[name]?.message?.toString();
  }
  return {
    isError: isError,
    errorMessage: errorMessage,
  };
};
