// base

// @ts-ignore
export const isWebApp = navigator.standalone;

export const isiOS = (/iphone|ipod|ipad/gi).test(navigator.platform);

export const isSafari = (/Safari/i).test(navigator.appVersion);

// total
export const neetStatusBar = isWebApp && isiOS;
