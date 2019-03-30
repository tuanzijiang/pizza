package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserManageControllerTest extends TestApplication {
    private static final Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);
    @Autowired
    UserManageController userManageController;

    @Test
    public void testGetUserList(){
        List<User> users=userManageController.getUserList();
        logger.info("Total user number is {}",users.size());
        Assert.assertEquals(4,users.size());

    }
}
