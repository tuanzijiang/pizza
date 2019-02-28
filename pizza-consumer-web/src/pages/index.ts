import Loading from '@biz-components/Loading';
import * as Loadable from 'react-loadable';

export const Manage = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "login" */'./Manage'),
  loading: Loading,
});

export const Edit = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "edit" */'./Edit'),
  loading: Loading,
});
