import { MutableSnapshot, RecoilRoot } from 'recoil';
import { QueryCache, QueryClient, QueryClientProvider } from 'react-query';
import { CacheProvider, EmotionCache } from '@emotion/react';
import { ThemeProvider } from '@mui/material';
import theme from '@/styles/theme';
import { ReactNode } from 'react';
import { AuthModel } from '@/models/AuthModels';
import { authState } from '@/recoil/auth/atoms';
import 'react-datepicker/dist/react-datepicker.css';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { ko } from 'date-fns/locale';
import { setAccessToken } from '@/utils/TokenManager';

type ProvidersProp = {
  emotionCache: EmotionCache;
  authInitState: AuthModel;
  children: ReactNode;
};

const Providers = ({
  children,
  emotionCache,
  authInitState,
}: ProvidersProp) => {
  const queryClient = new QueryClient({
    queryCache: new QueryCache(),
  });

  const recoilInitializeState = async ({ set }: MutableSnapshot) => {
    set(authState, authInitState);
    setAccessToken(authInitState.accessToken);
  };

  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot initializeState={recoilInitializeState}>
        <CacheProvider value={emotionCache}>
          <ThemeProvider theme={theme}>
            <LocalizationProvider
              dateAdapter={AdapterDateFns}
              adapterLocale={ko}
            >
              {children}
            </LocalizationProvider>
          </ThemeProvider>
        </CacheProvider>
      </RecoilRoot>
    </QueryClientProvider>
  );
};
export default Providers;
