import * as React from 'react';
import './index.scss';
import BlurTri from './BlurTri';
import BlurBlock from './BlurBlock';
import BlurCircle from './BlurCircle';

export interface BlurProps {
}

export interface BlurState { }

export default class Blur extends React.PureComponent<BlurProps, BlurState> {
  render() {
    return (
      <div className="blur-wrapper">
        <BlurTri />
        <BlurBlock />
        <BlurCircle />
      </div>
    );
  }
}
