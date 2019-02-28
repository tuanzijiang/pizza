import * as React from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import history from '@utils/history';
import { Manage, Edit } from './pages';
import net from '@net/base';

// @ts-ignore
window.__STORE__ = store;

export default class App extends React.PureComponent {
  componentDidMount() {
    net.protoNet();
  }

  render() {
    return (
      <Provider store={store}>
        <Router history={history}>
          <Switch>
            <Route component={Manage} path="/Manage" />
            <Route component={Edit} path="/Edit/:pageId" />
            <Redirect to={{
              pathname: '/Edit/123',
            }}/>
          </Switch>
        </Router>
      </Provider>
    );
  }
}
