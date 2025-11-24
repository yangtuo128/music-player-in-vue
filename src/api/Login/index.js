import axios from '../api';

export const login = (phone, password) => axios.post('/api/login', {
  phone,
  password,
});

export const register = (phone, password) => axios.post('/api/register', {
  phone,
  password,
});
