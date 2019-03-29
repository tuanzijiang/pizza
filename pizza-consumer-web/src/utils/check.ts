export const isTel = (tel: string) =>
  (/^1[345678][0-9]{9}$/.test(tel));

export const isEmail = (email: string) =>
  (/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(email));

export const isPW = (pw: string) =>
  (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,11}$/.test(pw));

export const isVarify = (varify: string) =>
  (/^[0-9]{4,6}$/.test(varify));

export const hiddenTel = (tel: string) => {
  if (!isTel(tel)) {
    return '';
  } else {
    const telArr = tel.split('');
    telArr.splice(3, 4, '****');
    return telArr.join('');
  }

};
