import axios from '../api';

export const getUserInfo = uid => axios.get(`/api/user/${uid}`);

export const updateUserInfo = (uid, userInfo) => axios.put(`/api/user/${uid}`, userInfo);

export const getUserLikelist = uid => axios.get('/likelist', {
  params: {
    uid,
    random: Math.random(),
  },
  withCredentials: true,
});

export const getUserPlaylist = uid => axios.get('/user/playlist', {
  params: {
    uid,
  },
});
