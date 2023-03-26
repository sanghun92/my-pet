import { AxiosError } from 'axios';
import { toast } from 'react-toastify';

export const getErrorDetailMessage = error => {
  switch (true) {
    case error instanceof AxiosError:
      return error.response?.data?.data?.messages[0].message;
    case error instanceof Error:
      return error.message;
    default:
      return 'error connecting to the server';
  }
};

export const getErrorMessage = error => {
  const status = error?.response?.status;
  switch (status) {
    case 400:
      return {
        title: '요청이 잘못되었습니다.',
        content: '요청 데이터를 다시 한번 확인해주세요.',
      };
    case 401:
    case 402:
      return {
        title: '접근 권한이 없습니다.',
        content: '로그인을 해주세요.',
      };
    case 409:
    case 500:
      return {
        title: '요청에 실패하였습니다.',
        content: '새로고침을 하거나 잠시 후 다시 접속해 주시기 바랍니다.',
      };
    default:
      return {
        title: '서비스에 접속할 수 없습니다.',
        content: '새로고침을 하거나 잠시 후 다시 접속해 주시기 바랍니다.',
      };
  }
};

const Msg = ({ title, content }) => (
  <div>
    [ {title} ]
    <br />
    {content}
  </div>
);

export const showError = (title, error) => {
  const errorFields = error.response?.data?.data?.errorFields;
  if (errorFields) {
    for (const errorField of errorFields) {
      toast.error(<Msg title={title} content={errorField.message} />);
    }
  } else {
    const message = getErrorMessage(error);
    toast.error(<Msg title={message.title} content={message.content} />);
  }
};

export const showErrorMessage = (error, errorMessage) => {
  let message = errorMessage;
  if (!error.response) {
    message = getErrorMessage(error);
  }
  toast.error(<Msg title={message.title} content={message.content} />);
};

export const showMessage = (title, content) => {
  toast.success(<Msg title={title} content={content} />);
};
