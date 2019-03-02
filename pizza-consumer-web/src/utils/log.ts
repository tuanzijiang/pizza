export const info = (tag: string, ...params: any) => {
  if (process.env.NODE_ENV === 'development') {
    console.info(`[${tag}]:`, ...params);
  }
};

export const warn = (tag: string, extraInfo: string, ...params: any) => {
  console.warn(`[${tag}]:${extraInfo}`, ...params);
};

export const error = (tag: string, extraInfo: string, ...params: any) => {
  console.error(`[${tag}]:${extraInfo}`, ...params);
};

export enum LogTag {
  NET = 'NET',
}
