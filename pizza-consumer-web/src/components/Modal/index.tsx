import * as React from 'react';
import './index.scss';
import Icon from '@biz-components/Icon';
import i18n from '@src/utils/i18n';

export interface ModalProps {
  title?: string;
  okText?: string;
  cancelText?: string;
  visible: boolean;
  handleClose?: () => void;
  handleOk?: () => void;
}

export interface ModalState { }

export default class Modal extends React.PureComponent<ModalProps, {}> {
  static defaultProps = {
    okText: i18n('确定'),
    cancelText: i18n('取消'),
    handleOk: () => { },
    handleClose: () => { },
  };

  render() {
    const {
      title, okText, cancelText, children, visible,
      handleClose, handleOk,
    } = this.props;
    return (
      <div>
        {
          visible && <div className="modal-wrapper">
            <div className="modal-main">
              <div className="modal-header">
                <span>{title}</span>
                <span onClick={handleClose}>
                  <Icon name="close" />
                </span>
              </div>
              <div className="modal-body">{children}</div>
              <div className="modal-footer">
                <div className="modal-ok" onClick={handleOk}>{okText}</div>
                <div className="modal-cancel" onClick={handleClose}>{cancelText}</div>
              </div>
            </div>
          </div>
        }
      </div>
    );
  }
}
