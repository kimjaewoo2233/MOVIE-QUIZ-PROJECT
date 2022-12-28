import { Cookies } from 'react-cookie';

const cookies = new Cookies();

export const setRefreshToken = (refreshToken) => {
  const today = new Date();
  const expireDate = today.setDate(today.getDate() + 30);

  return cookies.set('refreshToken', refreshToken, {
    expires: new Date(expireDate),
  });
};

export const setAccessToken = (accessToken) => {
  const today = new Date();
  const expireDate = today.setDate(today.getDate() + 5);

  return cookies.set('accessToken', accessToken, {
    expires: new Date(expireDate),
  });
};

export const getAccessToken = () => {
  return cookies.get('accessToken');
};

export const getCookieToken = () => {
  return cookies.get('refreshToken');
};

export const removeCookieToken = () => {
  return cookies.remove('refreshToken', { sameSite: 'strict', path: '/' });
};
