import { config as Header1 } from '@biz-components/Tag/SideBar/Header1/';
import { config as Header2 } from '@biz-components/Tag/SideBar/Header2/';
import { config as Header3 } from '@biz-components/Tag/SideBar/Header3/';
import { config as Header4 } from '@biz-components/Tag/SideBar/Header4/';
import { config as Header5 } from '@biz-components/Tag/SideBar/Header5/';
import { config as Font1 } from '@biz-components/Tag/SideBar/Font1/';
import { config as Font2 } from '@biz-components/Tag/SideBar/Font2/';
import i18n from '@src/utils/i18n';

export default [{
  group_name: i18n('标题'),
  group_items: [
    Header1,
    Header2,
    Header3,
    Header4,
    Header5,
  ],
}, {
  group_name: i18n('文字'),
  group_items: [
    Font1,
    Font2,
  ],
}];
