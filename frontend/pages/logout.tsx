import { NextPageContext } from 'next';
import { removeTokens } from '@/utils/TokenManager';
import Path from '@/constants/path';

const LogOutPage = () => {};
export default LogOutPage;

LogOutPage.getInitialProps = async (context: NextPageContext) => {
  const { req, res } = context;
  removeTokens({ req, res });
  res?.writeHead(302, {
    Location: Path.ROOT,
    'Content-Type': 'text/html; charset=utf-8',
  });
  res?.end();
  return {};
};
