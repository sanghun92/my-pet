// ** css
import '@/styles/globals.css';
import 'react-toastify/dist/ReactToastify.css';
import 'react-datepicker/dist/react-datepicker.css';

// ** React Imports
import * as React from 'react';
import { ReactElement, ReactNode } from 'react';
import { QueryCache, QueryClient, QueryClientProvider } from 'react-query';
import { ToastContainer } from 'react-toastify'; // ** Next Imports
import App, { AppContext, AppProps } from 'next/app';
import Head from 'next/head';
import { NextPage } from 'next'; // ** Emotion Import
import { CacheProvider, EmotionCache } from '@emotion/react'; // ** Model Import
import { AuthModel } from '@/core/models/AuthModels'; // ** API Import
import { postReIssueToken } from '@/apis/AuthApi'; // ** Mui Import
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'; // ** Layout Import
import UserLayout from '@/layouts/UserLayout';
import themeConfig from '@/configs/themeConfig';
import { SettingsConsumer, SettingsProvider } from '@/core/context/settingsContext';
import ThemeComponent from '@/core/theme/ThemeComponent'; // ** Recoil Import
import { MutableSnapshot, RecoilRoot } from 'recoil';
import { authState } from '@/core/recoil/auth/atoms'; // ** Util Import
import { createEmotionCache } from '@/core/utils/createEmotionCache';
import {
  getAccessToken,
  getJwtPayload,
  getRefreshToken,
  hasAccessToken,
  hasRefreshToken,
  setAccessToken,
} from '@/utils/TokenManager';
import { ko } from 'date-fns/locale'; // eslint-disable-next-line @typescript-eslint/ban-types

// eslint-disable-next-line @typescript-eslint/ban-types
export type NextPageWithLayout<P = {}, IP = P> = NextPage<P, IP> & {
  getLayout?: (page: ReactElement) => ReactNode;
};

type MyAppProps = AppProps & {
  authInitState: AuthModel;
  Component: NextPageWithLayout;
  emotionCache: EmotionCache;
};

const clientSideEmotionCache = createEmotionCache();

const queryClient = new QueryClient({
  queryCache: new QueryCache(),
});

const MyApp = (props: MyAppProps) => {
  const { Component, emotionCache = clientSideEmotionCache, pageProps, authInitState } = props;
  const getLayout = Component.getLayout ?? (page => <UserLayout>{page}</UserLayout>);
  const recoilInitializeState = async ({ set }: MutableSnapshot) => {
    set(authState, authInitState);
    setAccessToken(authInitState.accessToken);
  };

  return (
    <>
      <Head>
        <meta name='viewport' content='viewport-fit=cover' />
      </Head>
      <QueryClientProvider client={queryClient}>
        <RecoilRoot initializeState={recoilInitializeState}>
          <CacheProvider value={emotionCache}>
            <Head>
              <title>{`${themeConfig.templateName} - 반려동물 일상 공유`}</title>
              <meta
                name='description'
                content={`${themeConfig.templateName} – 반려동물의 일상을 공유하는 사이트입니다.`}
              />
              <meta name='keywords' content='my-pet' />
              <meta property='og:locale' content='ko_KR' />
              <meta property='og:site_name' content='마이펫' />
              <meta property='og:type' content='website' />
              <meta property='og:title' content={`${themeConfig.templateName} - 반려동물 일상 공유`} />
              <meta property='og:description' content='반려동물의 일상을 공유하는 사이트입니다.' />
              <meta property='og:url' content='https://my-pet.o-r.kr/' />
              <meta name='viewport' content='initial-scale=1, width=device-width' />
            </Head>

            <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={ko}>
              <SettingsProvider>
                <SettingsConsumer>
                  {({ settings }) => {
                    return (
                      <ThemeComponent settings={settings}>
                        <ToastContainer
                          position='top-right' // 알람 위치 지정
                          autoClose={1300} // 자동 off 시간
                          hideProgressBar={true} // 진행시간바 숨김
                          closeOnClick // 클릭으로 알람 닫기
                          rtl={false} // 알림 좌우 반전
                          pauseOnFocusLoss={false} // 화면을 벗어나면 알람 정지
                          pauseOnHover={false} // 마우스를 올리면 알람 정지
                          theme={`${themeConfig.mode}`}
                        />
                        {getLayout(<Component {...pageProps} />)}
                      </ThemeComponent>
                    );
                  }}
                </SettingsConsumer>
              </SettingsProvider>
            </LocalizationProvider>
          </CacheProvider>
        </RecoilRoot>
      </QueryClientProvider>
    </>
  );
};
export default MyApp;

MyApp.getInitialProps = async (context: AppContext) => {
  const appProps = await App.getInitialProps(context);
  const { req, res } = context.ctx;
  let authInitState: AuthModel = {
    isLoggedIn: false,
  };

  if (hasAccessToken({ req, res })) {
    const accessToken = getAccessToken({ req, res });
    authInitState = {
      accessToken: accessToken,
      isLoggedIn: true,
      jwtPayload: getJwtPayload(accessToken),
    };
  } else if (hasRefreshToken({ req, res })) {
    // Refresh Token만 있는 경우 Access Token 재발급
    const refreshToken = getRefreshToken({ req, res });
    await postReIssueToken(refreshToken)
      .then(res => {
        authInitState = {
          accessToken: res.data.accessToken,
          isLoggedIn: true,
          jwtPayload: getJwtPayload(res.data.accessToken),
        };
      })
      .catch(errors => console.error(errors));
  }

  return {
    ...appProps,
    authInitState,
  };
};
