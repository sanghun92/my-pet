import axios from 'axios';
import { BASE_URL } from '@/configs/environment';

const httpClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    Authorization: '',
  },
});

export default httpClient;
