import '@/styles/globals.css';
import 'react-toastify/dist/ReactToastify.css';

import App, { AppContext, AppProps } from 'next/app';
import { Box, CssBaseline } from '@mui/material';
import { ToastContainer } from 'react-toastify';
import createEmotionCache from '@/styles/createEmotionCache';
import { EmotionCache } from '@emotion/react';
import AppLayout from '@/layouts/AppLayout';
import Providers from '@/providers';
import {
  getAccessToken,
  getRefreshToken,
  hasRefreshToken,
  hasTokens,
} from '@/utils/TokenManager';
import { AuthModel } from '@/models/AuthModels';
import { requestReIssueToken } from '@/apis/AuthApi';

const clientSideEmotionCache = createEmotionCache();

export interface MyAppProps extends AppProps {
  authState: AuthModel;
  emotionCache?: EmotionCache;
}

const MyApp = (props: MyAppProps) => {
  const {
    Component,
    emotionCache = clientSideEmotionCache,
    pageProps,
    authState,
  } = props;

  return (
    <>
      <Providers emotionCache={emotionCache} authInitState={authState}>
        <CssBaseline />
        <AppLayout>
          <Box height="100vh" display="flex" flexDirection="column">
            <ToastContainer
              position="top-right" // 알람 위치 지정
              autoClose={3000} // 자동 off 시간
              hideProgressBar={false} // 진행시간바 숨김
              closeOnClick // 클릭으로 알람 닫기
              rtl={false} // 알림 좌우 반전
              pauseOnFocusLoss // 화면을 벗어나면 알람 정지
              pauseOnHover // 마우스를 올리면 알람 정지
              theme="dark"
            />
            <Component {...pageProps} />
          </Box>
        </AppLayout>
      </Providers>
    </>
  );
};
export default MyApp;

MyApp.getInitialProps = async (context: AppContext) => {
  const appProps = await App.getInitialProps(context);
  const { req, res } = context.ctx;
  let authState: AuthModel = {
    accessToken: undefined,
    isLoggedIn: false,
  };

  if (hasTokens({ req, res })) {
    // Access, Refresh Token 두개다 있는 경우
    const accessToken = getAccessToken({ req, res });
    authState = {
      accessToken: accessToken,
      isLoggedIn: true,
    };
  } else if (hasRefreshToken({ req, res })) {
    // Refresh Token만 있는 경우 Access Token 재발급
    const refreshToken = getRefreshToken({ req, res });
    const accessToken = await requestReIssueToken(refreshToken).then(
      res => res.data.accessToken,
    );
    authState = {
      accessToken: accessToken,
      isLoggedIn: true,
    };
  }

  return {
    ...appProps,
    authState: authState,
  };
};
