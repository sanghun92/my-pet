import { NextPageContext } from 'next';
import Error from 'next/error';

interface Props {
  statusCode: number;
}

const ErrorPage = ({ statusCode }: Props) => {
  if (statusCode === 404) {
    return <h1>404 Error</h1>;
  }
  return <Error statusCode={statusCode} />;
};

ErrorPage.getInitialProps = ({ res, err }: NextPageContext) => {
  const statusCode = res ? res.statusCode : err ? err.statusCode : 404;
  return { statusCode };
};

export default ErrorPage;
