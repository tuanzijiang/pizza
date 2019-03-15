package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OrderMenuEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMenuJpaRepositoryTest {

    @Autowired
    private OrderMenuJpaRepository orderMenuJpaRepository;

    @Test
    public void updateCountTest() {
        orderMenuJpaRepository.updateCount(5, 1, 1);
        OrderMenuEntity orderMenuEntity = orderMenuJpaRepository.findByOrderIdAndMenuId(1, 1).get();
        Assert.assertTrue(orderMenuEntity.getCount() == 5);
    }
}