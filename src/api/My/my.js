import axios from 'axios';

// 获取用户信息
export const getUserInfo = async (uid) => {
  try {
    // 这里需要替换为实际的后端API地址
    const response = await axios.get(`http://localhost:8080/api/user/${uid}`);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// 更新用户信息
export const updateUserInfo = async (uid, userInfo) => {
  try {
    // 这里需要替换为实际的后端API地址
    const response = await axios.put(`http://localhost:8080/api/user/${uid}`, userInfo);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

// 获取用户播放列表
export const getUserPlaylist = async (uid) => {
  try {
    // 这里需要替换为实际的后端API地址
    const response = await axios.get(`http://localhost:8080/api/user/${uid}/playlists`);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};
