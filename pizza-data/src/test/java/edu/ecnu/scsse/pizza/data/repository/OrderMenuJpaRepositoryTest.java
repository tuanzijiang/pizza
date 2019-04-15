package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.bean.PizzaBean;
import edu.ecnu.scsse.pizza.data.domain.OrderMenuEntity;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMenuJpaRepositoryTest {

    @Autowired
    private OrderMenuJpaRepository orderMenuJpaRepository;

    @Autowired
    private MenuJpaRepository menuJpaRepository;

    @Test
    public void test() {
        List<PizzaBean> pizzaBeans = menuJpaRepository.findPizzaBeansByStateAndOrderId(0,1);
        System.out.println(pizzaBeans.size());
    }

    @Test
    public void tests() {
        List<PizzaBean> pizzaBeans = menuJpaRepository.findPizzaBeansByOrderIds(Lists.newArrayList(1,2,5));
        System.out.println(pizzaBeans.size());
    }
}