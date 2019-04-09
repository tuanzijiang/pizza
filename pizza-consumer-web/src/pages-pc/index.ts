import Loading from '@biz-components/Loading';
import * as Loadable from 'react-loadable';

export const LoginAssets = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "loginPc" */'./LoginAssets'),
  loading: Loading,
});

export const MainAssets = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "mainPc" */'./MainAssets'),
  loading: Loading,
});

export const PaySuccess = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "paySuccessPc" */'./PaySuccess'),
  loading: Loading,
});
