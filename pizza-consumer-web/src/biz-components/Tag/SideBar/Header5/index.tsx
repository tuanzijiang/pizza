import * as React from 'react';
import TagComponent, {
  TagProps, TagState,
} from '@biz-components/Tag';
import { getPX } from '../index';
export { config } from './config';

interface CurrentTagProps extends TagProps {
  width: number;
  height: number;
  overflow: string;
}

export default class Header5 extends React.PureComponent<CurrentTagProps, TagState> {
  render() {
    const { tag_props, tag_children, behavior, overflow, onClick } = this.props;
    return (
      <div
        onClick={onClick}
        style={{
          overflow: overflow || 'hidden',
          width: getPX(tag_props.width || 100),
          height: getPX(tag_props.height || 100),
          position: 'relative',
        }}
      >
        {tag_children.map((tag_child: TagProps, key: number) => {
          return <TagComponent key={key} behavior={behavior} {...tag_child} />;
        })}
      </div>
    );
  }
}
