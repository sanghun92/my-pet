import { NextPageContext } from 'next/dist/shared/lib/utils';
import { removeTokens } from '@/utils/TokenManager';
import { useRouter } from 'next/router';
import { useEffect } from 'react';
import NavigationPaths from '@/navigation/NavigationPaths';
import { useSetRecoilState } from 'recoil';
import { authState } from '@/core/recoil/auth/atoms';

const LogOutPage = () => {
  const router = useRouter();
  const setAuthRecoilState = useSetRecoilState(authState);

  useEffect(() => {
    const logout = async () => {
      await router.push(NavigationPaths.ROOT);
    };

    setAuthRecoilState({
      accessToken: undefined,
      isLoggedIn: false,
    });
    logout();
  }, [router]);

  return <></>;
};
export default LogOutPage;

export const getServerSideProps = async ({ req, res }: NextPageContext) => {
  removeTokens({ req, res });
  return {
    props: {},
  };
};
