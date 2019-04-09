import * as React from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';
import { Provider, connect } from 'react-redux';
import store from './store';
import history from '@utils/history';
import ToastComponent, { ToastKind } from '@biz-components/Toast';
import {
  LoginAssets as LoginPagePc,
  MainAssets as MainPagePc,
} from './pages-pc';
import {
  LoginAssets as LoginPageMobile,
  MainAssets as MainPageMobile,
} from './pages-mobile';
import { neetStatusBar, isPc } from '@utils/device';
import { open } from '@utils/db';

open();

// @ts-ignore
window.__STORE__ = store;

export interface AppProps { }

export interface AppState { }

const Toast = connect((state: any) => {
  return {
    kind: isPc ? ToastKind.PC : ToastKind.MOBILE,
    visibility: state.common.toast_isOpen,
    text: state.common.toast_text,
  };
},
)(ToastComponent);

export default class App extends React.PureComponent<AppProps, AppState> {

  constructor(props: AppProps) {
    super(props);
    this.state = {
      canBeTouch: true,
    };

    if (neetStatusBar) {
      document.documentElement.style.cssText = `height: calc(100% + 20px)`;
    }
  }

  render() {
    return (
      <Provider store={store}>
        <div className="app-router">
          <Router history={history}>
            <Switch>
              <Route component={isPc ? LoginPagePc : LoginPageMobile} path="/LoginAssets" />
              <Route component={isPc ? MainPagePc : MainPageMobile} path="/MainAssets" />
              <Redirect to={{
                pathname: '/LoginAssets',
              }} />
            </Switch>
          </Router>
        </div>
        <Toast/>
      </Provider>
    );
  }
}
