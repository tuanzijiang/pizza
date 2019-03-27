import * as React from 'react';
import './index.scss';
import { fetchMenuApi } from '@services/api-fetch-menu';
import { updateOrderApi } from '@services/api-update-order';
import { Order, Pizza, User } from '@src/entity';
import { CART_ORDER_ID } from '@entity/Order';
import Icon from '@biz-components/Icon';
import i18n from '@src/utils/i18n';
import autobind from 'autobind-decorator';
import cx from 'classnames';

interface MenuProps {
  menu: Order;
  pizzas: Pizza[];
  user: User;
}

interface MenuState {
  currTag: number;
  tags: number[];
}

export const getTagAndCurrentTag: (menu: Order, pizzas: Pizza[]) => ({
  tags: number[],
  currTag: number;
}) = (menu: Order, pizzas: Pizza[]) => {
  if (!menu) {
    return {
      tags: [],
      currTag: 0,
    };
  }

  const { pizzas: pizzaIds } = menu;
  const nextTags: number[] = [];

  pizzaIds.forEach((pizzaId, idx, arr) => {
    const currPizza = pizzas[pizzaId];
    const lastTag = arr[idx - 1] &&
      pizzas[arr[idx - 1]].tag;
    const currTag = currPizza.tag;
    if (lastTag !== currTag) {
      nextTags.push(pizzaId);
    }
  });

  return {
    tags: nextTags,
    currTag: nextTags[0],
  };
};

export default class Menu extends React.PureComponent<MenuProps, MenuState> {
  private tagEls: { [key: string]: HTMLDivElement } = {};
  private tagElsTop: { [key: string]: number } = {};
  private menuScrollEl: React.RefObject<HTMLDivElement> = React.createRef();

  constructor(props: MenuProps) {
    super(props);
    this.state = {
      currTag: 0,
      tags: [],
    };
  }

  componentDidMount() {
    const { user } = this.props;
    fetchMenuApi({
      userId: user.id,
    });
  }

  componentDidUpdate(prevProps: MenuProps, prevState: MenuState) {
    const { pizzas, menu } = this.props;
    const { currTag } = prevState;
    const { pizzas: prevPizzas, menu: prevMenu } = prevProps;
    if (pizzas !== prevPizzas || menu !== prevMenu) {
      const { tags } = getTagAndCurrentTag(menu, pizzas);
      this.setState({
        tags,
      });
      if (currTag === 0) {
        this.setState({
          currTag: tags[0] || 0,
        });
      }
    }

    Object.entries(this.tagEls).map(([key, el]) => {
      if (el) {
        this.tagElsTop[key] = el.offsetTop;
      }
    });
  }

  @autobind
  handleMenuScroll() {
    const { currTag, tags } = this.state;
    const currScrollTop = this.menuScrollEl.current.scrollTop;
    const nextTag = tags.reduce((prev, curr) => {
      const tagScrollTop = this.tagElsTop[curr];
      if (tagScrollTop <= currScrollTop) {
        return curr;
      } else {
        return prev;
      }
    }, tags[0]);

    if (currTag !== nextTag) {
      this.setState({
        currTag: nextTag,
      });
    }

  }

  @autobind
  handleTagClick(pizzaId: number) {
    return () => {
      const currTagEl = this.tagEls[pizzaId];
      if (currTagEl) {
        currTagEl.scrollIntoView(true);
      }
    };
  }

  renderSubMenu() {
    const { tags, currTag } = this.state;
    const { pizzas } = this.props;

    return (
      <div className="menu-tags">
        {
          tags.map((tag, idx) => {
            const menuClassname = cx({
              'menu-tag': true,
              'menu-tag_active': currTag === tag,
            });
            return (
              <div
                className={menuClassname}
                key={idx}
                onClick={this.handleTagClick(tags[idx])}
              >
                {pizzas[tag].tag}
              </div>
            );
          })
        }
      </div>
    );
  }

  @autobind
  handleMenuUpdateClick(pizzaId: number, count: number) {
    return () => {
      updateOrderApi({
        pizzaId,
        count,
        orderId: CART_ORDER_ID,
      });
    };
  }

  renderMenuPizzas() {
    const { tags } = this.state;
    const { menu, pizzas } = this.props;
    const { pizzas: pizzaIds } = menu;

    return (
      <>
        {
          pizzaIds.map((pizzaId) => {
            const currPizza = pizzas[pizzaId];
            const currTag = currPizza.tag;
            const needTag = tags.includes(pizzaId);
            const {
              name: title, description: desc,
              price, id,
            } = currPizza;
            const count = menu.num[pizzaId];

            return (
              <div
                className="menu-pizzaItem"
                key={pizzaId}
              >
                {
                  needTag &&
                  <div
                    className="menu-pizzaItemTag"
                    ref={tagEl => {
                      this.tagEls[pizzaId] = tagEl;
                    }}
                  >
                    {currTag}
                  </div>
                }
                <div className="menu-pizzaItemContent">
                  <div className="menu-pizzaItemImage">
                    <Icon name="pisa" classnames="menu-pizzaItemPisa" />
                  </div>
                  <div className="menu-pizzaItemFont">
                    <div className="menu-pizzaItemTitle">
                      {title}
                    </div>
                    <div className="menu-pizzaItemDesc">
                      {desc}
                    </div>
                    <div className="menu-pizzaItemPrice">
                      <span className="menu-pizzaItemPrice_small">{i18n('¥')}</span>
                      <span>{price}</span>
                    </div>
                    <div className="menu-pizzaItemCount">
                      {
                        count !== 0 &&
                        <>
                          <Icon name="delete" onClick={this.handleMenuUpdateClick(id, count - 1)} />
                          <span className="menu-pizzaItemCountFont">
                            {count}
                          </span>
                        </>
                      }
                      <Icon name="add-fill" onClick={this.handleMenuUpdateClick(id, count + 1)} />
                    </div>
                  </div>
                </div>
              </div>
            );
          })
        }
      </>
    );
  }

  render() {
    const { menu } = this.props;
    return (
      <div className="menu-wrapper">
        {
          menu &&
          <>
            <div className="menu-subMenu">
              {this.renderSubMenu()}
            </div>
            <div
              className="menu-pizzaItems"
              onScroll={this.handleMenuScroll}
              ref={this.menuScrollEl}
            >
              {this.renderMenuPizzas()}
            </div>
          </>
        }
      </div>
    );
  }
}
