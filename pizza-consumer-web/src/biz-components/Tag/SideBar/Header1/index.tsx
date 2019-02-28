import * as React from 'react';
import TagComponent, {
  TagProps, TagState,
} from '@biz-components/Tag';
import { getPX } from '../index';
import autobind from 'autobind-decorator';
export { config } from './config';

interface CurrentTagProps extends TagProps {
  width: number;
  height: number;
  overflow: string;
}

export default class Header1 extends React.PureComponent<CurrentTagProps, TagState> {
  render() {
    const { tag_props, tag_children, behavior, overflow, onClick } = this.props;
    return (
      <div
        style={{
          overflow: overflow || 'hidden',
          width: getPX(tag_props.width || 100),
          height: getPX(tag_props.height || 100),
          position: 'relative',
        }}
        onClick={onClick}
      >
        {tag_children.map((tag_child: TagProps, key: number) => {
          return <TagComponent key={key} behavior={behavior} {...tag_child} />;
        })}
      </div>
    );
  }
}
