// base

// @ts-ignore
export const isWebApp = navigator.standalone;

export const isiOS = (/iphone|ipod|ipad/gi).test(navigator.userAgent);

export const isAndroid = (/Android/gi).test(navigator.userAgent);

export const isSafari = (/Safari/i).test(navigator.appVersion);

export let isPc: boolean;
if (process.env.NODE_ENV === 'development') {
  isPc = !!process.env.isPc;
} else {
  isPc = !isiOS && !isAndroid;
}

// total
export const neetStatusBar = isWebApp && isiOS;

// env
export const ip_4 = process.env.IP_4 || 'localhost';
