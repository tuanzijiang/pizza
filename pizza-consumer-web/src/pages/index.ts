import Loading from '@biz-components/Loading';
import * as Loadable from 'react-loadable';

export const Login = Loadable({
  // tslint:disable-next-line:space-in-parens
  loader: () => import(/* webpackChunkName: "login" */'./LoginAssets'),
  loading: Loading,
});

// export const Edit = Loadable({
//   // tslint:disable-next-line:space-in-parens
//   loader: () => import(/* webpackChunkName: "edit" */'./Edit'),
//   loading: Loading,
// });
