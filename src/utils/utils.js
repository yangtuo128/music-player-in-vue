export const getScreenHeight = () => `${document.documentElement.clientHeight}px`;

export const formatSinger = (singerList) => {
  // 拿出列表里面每一项的name，中间用'/'分隔
  let formatedStr = '';
  singerList.forEach((item) => {
    formatedStr += `${item.name}/`;
  });
  return formatedStr.slice(0, -1);
};

export const formatDate = (date) => {
  // 格式化日期为YYYY-MM-DD HH:mm:ss格式
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};
