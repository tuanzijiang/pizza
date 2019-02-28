import * as React from 'react';
import _ from 'lodash';
import TagComponent, {
  TagProps, TagState,
} from '@biz-components/Tag';
import { getPX } from '../index';
export { config } from './config';
import { PER_HEIGHT } from './config';

interface CurrentTagProps extends TagProps {
  width: number;
  overflow: string;
  contents: string[];
}

export default class Font2 extends React.PureComponent<CurrentTagProps, TagState> {
  render() {
    const { tag_children, tag_props, behavior, overflow, onClick } = this.props;
    const contents = tag_props.contents;
    return (
      <div
        onClick={onClick}
        style={{
          overflow: overflow || 'hidden',
          width: getPX(tag_props.width || 100),
          height: getPX(contents.length * PER_HEIGHT || 0),
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
