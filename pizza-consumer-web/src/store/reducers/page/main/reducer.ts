import {
  UPDATE_NAV_IDX,
  UPDATE_ORDER_IDS,
} from './action';

const initState: {
  navIdx: number;
  orderIds: string[];
} = {
  navIdx: 2,
  orderIds: [],
};

export default (state = initState, action: any) => {
  switch (action.type) {
    case UPDATE_NAV_IDX: {
      return {
        ...state,
        navIdx: action.navIdx,
      };
    }
    case UPDATE_ORDER_IDS: {
      const currOrderIds = action.orderIds;
      const prevOrderIds = state.orderIds;

      const newOrdersId = [...prevOrderIds];
      currOrderIds.forEach((orderId: string) => {
        if (!newOrdersId.includes(orderId)) {
          newOrdersId.push(orderId);
        }
      });

      return {
        ...state,
        orderIds: newOrdersId,
      };
    }
    default:
      return state;
  }
};
