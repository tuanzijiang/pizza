import * as React from 'react';
import history from '@src/utils/history';

interface PayProps { }

interface PayState { }

export default class Pay extends React.PureComponent<PayProps, PayState> {
  componentDidMount() {
    history.push('./MainAssets');
  }

  componentDidUpdate() {
    history.push('./MainAssets');
  }

  render() {
    return <div />;
  }
}
