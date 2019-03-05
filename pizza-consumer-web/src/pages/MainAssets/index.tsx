import * as React from 'react';
import PageAssets, { OpenType } from '@biz-components/PageAssets';
import './index.scss';
import Main from './Main';
import { connect } from 'react-redux';
import { page as pageActionCreator } from '@store/action';
import autobind from 'autobind-decorator';

interface LoginAssetsProps {
  mainStore: any;
  entityStore: any;
  updateNavIdx: (idx: number) => void;
}

interface LoginAssetsState { }

export enum MainAssetName {
  Main = 'Main',
}

const config = {
  [MainAssetName.Main]: 0,
};

const handleTouchMove = (e: TouchEvent) => {
  e.preventDefault();
};

export class MainAssets extends React.PureComponent<LoginAssetsProps, LoginAssetsState> {
  private pageAssetsEl: React.RefObject<PageAssets> = React.createRef();

  constructor(props: LoginAssetsProps) {
    super(props);
    this.state = { };
  }

  @autobind
  handlePageChange(name: MainAssetName, openType?: OpenType, ...extraInfo: any[]) {
    if (this.pageAssetsEl && this.pageAssetsEl.current) {
      this.pageAssetsEl.current.openByName(config[name], openType, ...extraInfo);
    }
  }

  componentDidMount() {
  }

  componentWillUnmount() {
  }

  render() {
    const { mainStore, updateNavIdx, entityStore } = this.props;
    const { navIdx, orderIds } = mainStore;
    return (
      <div className="loginAssets">
        <PageAssets ref={this.pageAssetsEl}>
          <Main
            onPageChange={this.handlePageChange}
            navIdx={navIdx}
            orderIds={orderIds}
            updateNavIdx={updateNavIdx}
            entityStore={entityStore}
          />
        </PageAssets>
      </div>
    );
  }
}

export default connect((state: any) => {
  return {
    mainStore: state.page.main,
    entityStore: state.entity,
  };
}, (dispatch) => {
  return {
    updateNavIdx: (idx: number) => {
      dispatch(pageActionCreator.main.updateNavIdx(idx));
    },
  };
})(MainAssets);
