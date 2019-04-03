import * as React from 'react';
import './index.scss';
import { Record } from '@entities/Record';
import { Response } from '@entities/Response';
import cx from 'classnames';
import autobind from 'autobind-decorator';
import Modal from '@components/Modal';
import { Input, Table, Tooltip } from 'antd';
import { send, COMMAND_FROM_RENDER, on, COMMAND_FROM_WEBLOADER } from '@utils/event';
import { Command } from '@entities/Command';

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

  // test modal
  testModalVisible: boolean;
  testNumRange: number;
  testAddInterval: number;
  testDuration: number;
  testTimeout: number;

  // test info
  pageInfos: {
    status: boolean;
    msg: null;
    detail: any;
  }[][];
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

      // test modal
      testModalVisible: false,
      testNumRange: 10, // 最大并发数
      testAddInterval: 5, // 每隔多少秒增加一个并发进程
      testDuration: 60, // 持续加压事件
      testTimeout: 5, // 超时多少秒

      // test info
      pageInfos: [],
    };
  }

  componentDidMount() {
    on((jsonParam) => {
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
        case COMMAND_FROM_WEBLOADER.TEST: {
          const { pageIdx, status, msg, detail } = args;
          const { pageInfos } = this.state;
          const newPageInfos = [...pageInfos];

          // 生成新的pageInfo
          const newPageInfo = [...(pageInfos[pageIdx] || [])];
          newPageInfo.push({
            status,
            msg,
            detail,
          });
          newPageInfos[pageIdx] = newPageInfo;

          this.setState({
            pageInfos: newPageInfos,
          });
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

  @autobind
  handleRecordItemClick(recordId: string) {
    return () => {
      this.setState({
        currId: recordId,
      });
    };
  }

  @autobind
  handlePlayClick() {
    const { currId } = this.state;
    const args = {
      recordId: currId,
    };
    send(COMMAND_FROM_RENDER.PLAY, args);
  }

  // test
  @autobind
  updateTestModal(visible: boolean) {
    return () => {
      this.setState({
        testModalVisible: visible,
      });
    };
  }

  @autobind
  handleTestAddIntervalChange(ev: React.ChangeEvent<HTMLInputElement>) {
    const value = ev.target.value;
    this.setState({
      testAddInterval: parseInt(value, 10),
    });
  }

  @autobind
  handleTestNumRangeChange(ev: React.ChangeEvent<HTMLInputElement>) {
    const testNumRange = ev.target.value;
    this.setState({
      testNumRange: parseInt(testNumRange, 10),
    });
  }

  @autobind
  handleTestDurationChange(ev: React.ChangeEvent<HTMLInputElement>) {
    const value = ev.target.value;
    this.setState({
      testDuration: parseInt(value, 10),
    });
  }

  @autobind
  handleTestTimeoutChange(ev: React.ChangeEvent<HTMLInputElement>) {
    const value = ev.target.value;
    this.setState({
      testTimeout: parseInt(value, 10),
    });
  }

  @autobind
  handleTestClick() {
    const {
      testAddInterval,
      testDuration,
      testNumRange,
      testTimeout,
      currId,
    } = this.state;
    const args = {
      testAddInterval,
      testDuration,
      testNumRange,
      testTimeout,
      recordId: currId,
    };

    send(COMMAND_FROM_RENDER.TEST, args);
    this.updateTestModal(false)();
  }

  renderLeft() {
    const { recordIds, currId } = this.state;
    return <div className="main-leftWrapper">
      <div className="main-leftHeader">
        记录列表
      </div>
      <div className="main-leftRecords">
        {
          recordIds.map(recordId => (
            <div
              key={recordId}
              className={cx({
                'main-leftRecord': true,
                'main-leftRecord_active': recordId === currId,
              })}
              onClick={this.handleRecordItemClick(recordId)}
            >
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

  renderRight() {
    const { recordMap, currId, pageInfos } = this.state;
    const currRecord = recordMap[currId];
    const { commands, successResp } = currRecord;
    const num = commands.length;
    const commandTable = [{
      title: 'name',
      key: 'name',
      render: (text: string, command: Command) => <div>{command.name}</div>,
    }, {
      title: 'selector',
      key: 'selector',
      render: (text: string, command: Command) => <div>{command.selector}</div>,
    }, {
      title: 'value',
      key: 'value',
      render: (text: string, command: Command) => <div>{command.value}</div>,
    }];

    const responseTable = [{
      title: 'url',
      key: 'url',
      render: (text: string, response: Response) =>
        <div className="main-responseCell">{response.url}</div>,
    }, {
      title: 'method',
      key: 'method',
      render: (text: string, response: Response) => <div>{response.method}</div>,
    }];

    return <div className="main-rightWrapper">
      <div className="main-rightHeader">
        <div
          className="main-rightHeaderButton"
          onClick={this.handlePlayClick}
        >
          播放
        </div>
        <div
          className="main-rightHeaderButton"
          onClick={this.updateTestModal(true)}
        >
          测试
        </div>
      </div>
      <div className="main-rightContent">
        <div className="main-contentTitle">
          指令列表
        </div>
        <div className="main-contentTable">
          <Table columns={commandTable} dataSource={commands}
            rowKey={(record, idx) => `${idx}`} pagination={false}
          />
        </div>
        <div className="main-contentTitle">
          请求列表
        </div>
        <div className="main-contentTable">
          <Table columns={responseTable} dataSource={successResp}
            rowKey={(record, idx) => `${idx}`} pagination={false}
          />
        </div>
        <div className="main-contentTitle">
          测试报告
        </div>
        <div className="main-contentReport">
          {
            pageInfos.map((pageInfo, idx) => <div className="main-contentReportRow" key={idx}>
              {
                [...Array(num).keys()].map((v, i) => {
                  const pageStatus = pageInfo[i];
                  if (!pageStatus) {
                    return (
                      <Tooltip key={i} title={'尚未执行的指令'} placement="top">
                        <div className="main-contentReportCell" key={i} />
                      </Tooltip>
                    );
                  } else {
                    const { status, msg, detail } = pageStatus;
                    const tipObj = {
                      status,
                      msg,
                      detail,
                    };
                    return (
                      <Tooltip key={i} title={JSON.stringify(tipObj)} placement="top">
                        <div className={cx({
                          'main-contentReportCell': true,
                          'main-contentReportCell_success': status,
                          'main-contentReportCell_error': !status,
                        })} />
                      </Tooltip>
                    );
                  }
                })
              }
            </div>)
          }
        </div>
      </div>
    </div>;
  }

  render() {
    const {
      recordModalVisible, recordUrl, recordMap, currId,
      testModalVisible, testAddInterval, testDuration, testNumRange, testTimeout,
    } = this.state;
    const currRecord = recordMap[currId];

    return <div className="main-wrapper">
      <div className="main-left">{this.renderLeft()}</div>
      <div className="main-right">{currRecord && this.renderRight()}</div>
      <Modal
        visible={recordModalVisible} handleClose={this.updateRecordModal(false)}
        handleOk={this.handleRecordClick} title="录制脚本"
      >
        <div className="main-modalTitle">目标页面url</div>
        <Input value={recordUrl} onChange={this.handleRecordUrlChange}
          placeholder="录制的Url，如：http://localhost:8080" className="main-input"
        />
      </Modal>
      <Modal
        visible={testModalVisible} handleClose={this.updateTestModal(false)}
        handleOk={this.handleTestClick} title="测试脚本"
      >
        <div className="main-modalTitle">加压间隔(单位：秒)</div>
        <Input value={testAddInterval} onChange={this.handleTestAddIntervalChange}
          placeholder="加压间隔，如：3" className="main-input" type="number"
        />
        <div className="main-modalTitle">测试时长(单位：秒)</div>
        <Input value={testDuration} onChange={this.handleTestDurationChange}
          placeholder="测试时长，如60" className="main-input" type="number"
        />
        <div className="main-modalTitle">最大并发数(单位：个)</div>
        <Input value={testNumRange} onChange={this.handleTestNumRangeChange}
          placeholder="最大并发数，如10" className="main-input" type="number"
        />
        <div className="main-modalTitle">超时时间(单位：毫秒)</div>
        <Input value={testTimeout} onChange={this.handleTestTimeoutChange}
          placeholder="超时时间，如5000" className="main-input" type="number"
        />
      </Modal>
    </div>;
  }
}
