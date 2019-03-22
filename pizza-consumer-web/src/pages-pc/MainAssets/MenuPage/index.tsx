import * as React from 'react';
import './index.scss';
import { Order, Pizza } from '@src/entity';
import Menu from './Menu';
import Pay from './Pay';
import autobind from 'autobind-decorator';

interface MenuPageProps {
  menu: Order;
  pizzas: Pizza[];
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

  @autobind
  handleNavChange(navIdx: number) {
    return () => {
      this.setState({
        navIdx,
      });
    };
  }

  render() {
    const { menu, pizzas } = this.props;
    const { navIdx } = this.state;
    return (
      <div className="menuPage-wrapper">
        {navIdx === 0 && <Menu
          menu={menu} pizzas={pizzas} handleToPay={this.handleNavChange(1)} />
        }
        {navIdx === 1 && <Pay
          menu={menu} pizzas={pizzas} handleToMenu={this.handleNavChange(0)} />
        }
      </div>
    );
  }
}
