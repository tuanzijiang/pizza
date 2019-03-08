package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OrderState;

import java.util.Date;

public class Order {
    private int id;
    private OrderState state;
    private Point mapPoint;
    private int driver_id;
    private Date commit_time;
    private int delieve_order;
    private Date finish_time;
    private int latest_leave_time;
}
