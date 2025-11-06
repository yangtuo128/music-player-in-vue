import axios from 'axios';

// 登录接口
export const login = async (phone, password) => {
  try {
    // 这里需要替换为实际的后端API地址
    const response = await axios.post('http://localhost:8080/api/login', {
      phone,
      password,
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// 注册接口
export const register = async (phone, password) => {
  try {
    // 这里需要替换为实际的后端API地址
    const response = await axios.post('http://localhost:8080/api/register', {
      phone,
      password,
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};