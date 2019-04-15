package edu.ecnu.scsse.pizza.consumer.server.utils;

import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.User;
import edu.ecnu.scsse.pizza.data.bean.PizzaBean;
import edu.ecnu.scsse.pizza.data.bean.UserAddressBean;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaTag;
import edu.ecnu.scsse.pizza.data.enums.Sex;

import java.math.BigDecimal;

public class EntityConverter {

    /**
     * Assemble basic order info.
     *
     * @param entity order db entity.
     * @return Order
     */
    public static Order convert(OrderEntity entity) {
        Order order = new Order();
        order.setId(entity.getOrderUuid());
        order.setStatus(OrderStatus.fromDbValue(entity.getState()));

        if (entity.getCommitTime() != null) {
            order.setStartTime(entity.getCommitTime().getTime());
        }
        return order;
    }

    /**
     * Construct Address.
     *
     * @param userAddressEntity userAddressEntity
     * @param addressEntity addressEntity
     * @return Address
     */
    public static Address convert(UserAddressEntity userAddressEntity, AddressEntity addressEntity) {
        Address address = new Address();

        address.setId(userAddressEntity.getId());
        address.setAddressDetail(userAddressEntity.getAddressDetail());
        address.setName(userAddressEntity.getName());
        address.setSex(Sex.fromDbValue(userAddressEntity.getSex()));
        address.setPhone(userAddressEntity.getPhone());

        address.setAddress(addressEntity.getAddress());


        return address;
    }

    /**
     * Construct Address.
     *
     * @param userAddressBean userAddressBean
     * @return Address
     */
    public static Address convert(UserAddressBean userAddressBean) {
        Address address = new Address();

        address.setAddress(userAddressBean.getAddress());

        address.setId(userAddressBean.getId());
        address.setAddressDetail(userAddressBean.getAddressDetail());
        address.setName(userAddressBean.getName());
        address.setPhone(userAddressBean.getPhone());

        address.setSex(Sex.fromDbValue(userAddressBean.getSex()));

        return address;
    }

    /**
     * Construct Pizza Menu.
     *
     * @param menuEntity menuEntity
     * @return Pizza
     */
    public static Pizza convert(MenuEntity menuEntity) {
        return convert(null, menuEntity);
    }

    /**
     * Construct Pizza.
     *
     * @param orderMenuEntity orderMenuEntity
     * @param menuEntity menuEntity
     * @return Pizza
     */
    public static Pizza convert(OrderMenuEntity orderMenuEntity, MenuEntity menuEntity) {
        Pizza pizza = new Pizza();

        if (orderMenuEntity != null) {
            pizza.setCount(orderMenuEntity.getCount());
        }

        if (menuEntity != null) {
            pizza.setId(menuEntity.getId());
            pizza.setName(menuEntity.getName());
            pizza.setDescription(menuEntity.getDescription());
            pizza.setImg(menuEntity.getImage());
            pizza.setPrice(menuEntity.getPrice());
            pizza.setTag(PizzaTag.fromDbValue(menuEntity.getTag()).getExpression());
            pizza.setState(PizzaStatus.fromDbValue(menuEntity.getState()));
        }

        return pizza;
    }

    /**
     * Construct Pizza.
     *
     * @param pizzaBean pizzaBean
     * @return Pizza
     */
    public static Pizza convert(PizzaBean pizzaBean) {
        Pizza pizza = new Pizza();
        pizza.setCount(pizzaBean.getCount());
        pizza.setId(pizzaBean.getId());
        pizza.setName(pizzaBean.getName());
        pizza.setDescription(pizzaBean.getDescription());
        pizza.setImg(pizzaBean.getImg());
        pizza.setPrice(pizzaBean.getPrice());
        pizza.setState(PizzaStatus.fromDbValue(pizzaBean.getState()));
        pizza.setTag(PizzaTag.fromDbValue(pizzaBean.getTag()).getExpression());

        return pizza;
    }

    public static User convert(UserEntity userEntity) {
        User user = new User(userEntity.getId(), userEntity.getName(), userEntity.getPhone(),
                userEntity.getEmail(), userEntity.getBirthday(), userEntity.getCity(), userEntity.getImage());
        return user;
    }

    public static AddressEntity convert(AmapLocation.Geocode geocode) {
        AddressEntity addressEntity=new AddressEntity();
        addressEntity.setAddress(geocode.getFormattedAddress());
        addressEntity.setLon(new BigDecimal(geocode.getLocation().split(",")[0]));
        addressEntity.setLat(new BigDecimal(geocode.getLocation().split(",")[1]));
        return addressEntity;
    }
}
