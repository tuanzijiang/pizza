export const isTel = (tel: string) =>
  (/^1[345678][0-9]{9}$/.test(tel));

export const isEmail = (email: string) =>
  (/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(email));

export const isPW = (pw: string) =>
  (/^([A-Za-z0-9]){6,11}$/.test(pw));
