import {
  createQueryKeyStore,
  inferQueryKeyStore,
} from '@lukemorales/query-key-factory';

export const queries = createQueryKeyStore({
  members: {
    all: null,
    detail: () => ({
      queryKey: ['member'],
    }),
  },
});

export type QueryKeys = inferQueryKeyStore<typeof queries>;
