import * as React from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import history from '@utils/history';
import { Login } from './pages';
import { neetStatusBar } from '@utils/device';

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
            <Route component={Login} path="/Login" />
            <Redirect to={{
              pathname: '/Login',
            }} />
          </Switch>
        </Router>
      </Provider>
    );
  }
}
