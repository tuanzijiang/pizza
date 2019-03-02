import * as React from 'react';
import Spinner from '@components/Spinner';
import { gray_4 } from '@ui/base';
import './index.scss';

const style = {
  width: '30px',
  height: '30px',
};

export default () => (
  <div className="loading-wrapper" >
    <div className="loading-bg" />
    <div className="loading-animation">
      <Spinner color={gray_4} style={style} />
    </div>
  </div>
);
