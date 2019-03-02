import * as React from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import history from '@utils/history';
import { Login } from './pages';

// @ts-ignore
window.__STORE__ = store;
// @ts-ignore
window.__getPageState = () => store.getState().page;
// @ts-ignore
window.__getEntityState = () => store.getState().entity;

export default class App extends React.PureComponent {
  render() {
    return (
      <Provider store={store}>
        <Router history={history}>
          <Switch>
            <Route component={Login} path="/Login" />
            <Redirect to={{
              pathname: '/Login',
            }}/>
          </Switch>
        </Router>
      </Provider>
    );
  }
}
