import Loading from '@biz-components/Loading';
import * as Loadable from 'react-loadable';

export const LoginAssets = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "loginMobile" */'./LoginAssets'),
  loading: Loading,
});

export const MainAssets = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "mainMobile" */'./MainAssets'),
  loading: Loading,
});

export const PaySuccess = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "paySuccessMobile" */'./PaySuccess'),
  loading: Loading,
});
