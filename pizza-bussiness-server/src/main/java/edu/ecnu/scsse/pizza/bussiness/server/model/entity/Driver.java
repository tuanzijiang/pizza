package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import edu.ecnu.scsse.pizza.bussiness.server.model.enums.DriverState;

import java.util.Date;
import java.util.List;

public class Driver {
    private int id;
    private DriverState state;
    private List<Order> orderList;
    private int max_delieve_num;
    private int shop_id;
    private Date latest_leave_time;
    private Date back_time;

    public int getDelieveScoreByAddNewOrder(Order order){
        //将order加入骑手队列的得分，判读每个骑手是否接单的凭证
        int score=0;
        return score;
    }
}
