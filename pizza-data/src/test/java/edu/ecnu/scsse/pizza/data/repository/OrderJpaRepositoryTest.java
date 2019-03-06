package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderJpaRepositoryTest {

    private int userId = 1;

    @Autowired
    OrderJpaRepository orderJpaRepository;


    @Test
    public void findByOrderUuidTest() {
        Optional<OrderEntity> order = orderJpaRepository.findByOrderUuid("AAA");
        Assert.assertTrue(order.isPresent());
    }

    @Test
    public void findIdByOrderUuidTest() {
        Optional<Integer> id = orderJpaRepository.findIdByOrderUuid("AAA");
        Assert.assertEquals(1, id.get().intValue());
    }


    @Test
    public void findByUserIdAndStateInByPageTest() {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(
                Sort.Order.desc("commitTime"), Sort.Order.desc("id")
        ));
        List<OrderEntity> orderEntities = orderJpaRepository.findByUserIdAndStateInAndIdGreaterThan(
                userId, Lists.newArrayList(1, 2), 0, pageRequest);
        Assert.assertEquals(1, orderEntities.size());
    }

}
