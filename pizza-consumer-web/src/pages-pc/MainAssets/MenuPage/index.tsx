import * as React from 'react';
import './index.scss';
import { Order, Pizza, Address, User } from '@src/entity';
import { fetchAddressApi } from '@src/services/api-fetch-address';
import Menu from './Menu';
import Pay from './Pay';
import autobind from 'autobind-decorator';
import { fetchUserApi } from '@src/services/api-fetch-user';
import { fetchMenuApi } from '@src/services/api-fetch-menu';

interface MenuPageProps {
  menu: Order;
  pizzas: Pizza[];
  addresses: Address[];
  user: User;
  cartId: any;
}

interface MenuPageState {
  navIdx: number;
}

export default class MenuPage extends React.PureComponent<MenuPageProps, MenuPageState> {
  constructor(props: MenuPageProps) {
    super(props);
    this.state = {
      navIdx: 0,
    };
  }

  componentDidMount() {
    const { user } = this.props;
    fetchUserApi({
      userId: user.id,
    });
    fetchAddressApi({
      userId: user.id,
    });

  }

  @autobind
  handleNavChange(navIdx: number) {
    return () => {
      this.setState({
        navIdx,
      });
    };
  }

  render() {
    const { menu, pizzas, addresses, user, cartId } = this.props;
    const { navIdx } = this.state;
    const payAddresses = user.addresses.map(id => addresses[id]);
    payAddresses.unshift(addresses[user.address]);
    return (
      <div className="menuPage-wrapper">
        {navIdx === 0 && <Menu
          menu={menu} pizzas={pizzas}
          handleToPay={this.handleNavChange(1)} cartId={cartId} userId={user.id} />
        }
        {navIdx === 1 && <Pay
          menu={menu} pizzas={pizzas}
          addresses={addresses}
          cartId={cartId}
          handleToMenu={this.handleNavChange(0)} addressIds={[
            user.address,
            ...user.addresses,
          ]} />
        }
      </div>
    );
  }
}
