/* black */
export const black_1 = '#262626';
export const black_2 = '#000';

/* gray */
export const gray_1 = '#e8e8e8';
export const gray_2 = '#d9d9d9';
export const gray_3 = '#bfbfbf';
export const gray_4 = '#8c8c8c';
export const gray_5 = '#595959';

/* white */
export const white_1 = '#fff';
export const white_2 = '#f5f5f5';

/* blue */
export const blue_1 = '#91d5ff';
export const blue_2 = '#69c0ff';
export const blue_3 = '#40a9ff';
export const blue_4 = '#1890ff';
export const blue_5 = '#096dd9';
/* green */
export const green_1 = '#b7eb8f';
export const green_2 = '#95de64';
export const green_3 = '#73d13d';
export const green_4 = '#52c41a';
export const green_5 = '#389e0d';
/* red */
export const red_1 = '#ffa39e';
export const red_2 = '#ff7875';
export const red_3 = '#ff4d4f';
export const red_4 = '#f5222d';
export const red_5 = '#cf1322';

/* tool function */
export const base_font = 16;
export const base_screen = 320;

export const rem = (px: number) => {
  const remSize = px / base_font;
  return `${remSize}rem`;
};

export const vw = (px: number) => {
  const vmSize = px * 100 / base_screen;
  return `${vmSize}vw`;
};
