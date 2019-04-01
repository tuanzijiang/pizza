import * as React from 'react';
import * as ReactDOM from 'react-dom';
import Main from '@pages/Main';

export default class App extends React.PureComponent {
  render() {
    return <div className="app">
      <Main />
    </div>;
  }
}
