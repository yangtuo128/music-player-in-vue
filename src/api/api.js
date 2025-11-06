import axios from 'axios';

axios.defaults.baseURL = ' http://localhost:8080/';
axios.interceptors.response.use(res => res.data, err => Promise.reject(err.response.data));

export default axios;
