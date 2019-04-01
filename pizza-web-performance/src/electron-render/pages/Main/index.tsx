import * as React from 'react';
import './index.scss';
import { Record } from '@entities/Record';
import cx from 'classnames';
import autobind from 'autobind-decorator';
import Modal from '@components/Modal';
import { Input } from 'antd';
import { send, COMMAND_FROM_RENDER, on, COMMAND_FROM_WEBLOADER } from '@utils/event';

export interface RecordMap {
  [key: string]: Record;
}

export interface MainProps { }

export interface MainState {
  recordMap: RecordMap;
  recordIds: string[];
  currId: string;

  // record modal
  recordModalVisible: boolean;
  recordUrl: string;
}

export default class Main extends React.PureComponent<MainProps, MainState> {
  constructor(props: MainProps) {
    super(props);
    this.state = {
      recordMap: {},
      recordIds: [],
      currId: null,

      // record modal
      recordModalVisible: false,
      recordUrl: 'http://localhost:8080',
    };
  }

  componentDidMount() {
    on((jsonParam) => {
      const { recordMap } = this.state;
      const param = JSON.parse(jsonParam);
      const { name, args } = param;
      switch (name) {
        case COMMAND_FROM_WEBLOADER.RECORD: {
          const record = new Record(args);
          const { id } = record;
          this.setState({
            recordMap: {
              ...this.state.recordMap,
              [id]: record,
            },
            recordIds: [
              ...this.state.recordIds,
              id,
            ],
          });
          break;
        }
      }
    });
  }

  // record
  @autobind
  updateRecordModal(visible: boolean) {
    return () => {
      this.setState({
        recordModalVisible: visible,
      });
    };
  }

  @autobind
  handleRecordUrlChange(ev: React.ChangeEvent<HTMLInputElement>) {
    const recordUrl = ev.target.value;
    this.setState({
      recordUrl,
    });
  }

  @autobind
  handleRecordClick() {
    const { recordUrl } = this.state;
    const args = {
      url: recordUrl,
    };
    this.updateRecordModal(false)();
    send(COMMAND_FROM_RENDER.RECORD, args);
  }

  // @autobind
  // handlePlayClick() {
  //   send(SEND_COMMAND_NAME.PLAY);
  // }

  // @autobind
  // handleTestClick() {
  //   send(SEND_COMMAND_NAME.TEST);
  // }

  renderLeft() {
    const { recordIds, currId } = this.state;
    return <div className="main-leftWrapper">
      <div className="main-leftHeader">
        记录列表
      </div>
      <div className="main-leftRecords">
        {
          recordIds.map(recordId => (
            <div key={recordId} className={cx({
              'main-leftRecord': true,
              'main-leftRecord_active': recordId === currId,
            })}>
              {recordId}
            </div>
          ))
        }
        <div
          className="main-leftRecord main-leftRecord_add"
          onClick={this.updateRecordModal(true)}
        >+</div>
      </div>
    </div>;
  }

  render() {
    const { recordModalVisible, recordUrl } = this.state;
    return <div className="main-wrapper">
      <div className="main-left">{this.renderLeft()}</div>
      <div className="main-right"></div>
      <Modal
        visible={recordModalVisible} handleClose={this.updateRecordModal(false)}
        handleOk={this.handleRecordClick} title="录制脚本"
      >
        <Input value={recordUrl} onChange={this.handleRecordUrlChange}
          placeholder="录制的Url，如：http://localhost:8080"
        />
      </Modal>
    </div>;
  }
}
