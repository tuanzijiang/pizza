import Loading from '@biz-components/Loading';
import * as Loadable from 'react-loadable';

export const LoginAssets = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "login" */'./LoginAssets'),
  loading: Loading,
});

export const MainAssets = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "login" */'./MainAssets'),
  loading: Loading,
});
