package edu.ecnu.scsse.pizza.consumer.server.model.entity;

import edu.ecnu.scsse.pizza.data.enums.OrderStatus;

import java.util.Collections;
import java.util.List;

public class Order {
    private String id;
    private Long startTime;
    private List<Pizza> pizzas = Collections.emptyList();
    private Address address;
    private OrderStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
