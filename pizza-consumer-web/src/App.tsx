import * as React from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import history from '@utils/history';
import {
  LoginAssets as LoginPagePc,
  MainAssets as MainPagePc,
} from './pages-pc';
import {
  LoginAssets as LoginPageMobile,
  MainAssets as MainPageMobile,
} from './pages-mobile';
import { neetStatusBar, isPc } from '@utils/device';

// @ts-ignore
window.__STORE__ = store;

export interface AppProps { }

export interface AppState { }

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
        <Router history={history}>
          <Switch>
            <Route component={isPc ? LoginPagePc : LoginPageMobile} path="/LoginAssets" />
            <Route component={isPc ? MainPagePc : MainPageMobile} path="/MainAssets" />
            <Redirect to={{
              pathname: '/LoginAssets',
            }} />
          </Switch>
        </Router>
      </Provider>
    );
  }
}
