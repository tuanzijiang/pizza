package edu.ecnu.scsse.pizza.consumer.server;

import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import edu.ecnu.scsse.pizza.data.repository.AddressJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableJpaRepositories("edu.ecnu.scsse.pizza.data.repository")
@EntityScan("edu.ecnu.scsse.pizza.data.domain")
public class UserServiceUT {

    @Autowired
    AddressJpaRepository addressJpaRepository;


    @Test
    public void contextLoads() {
        List<AddressEntity> addressEntity=addressJpaRepository.findAll();
        System.out.println("addressEntity size: "+addressEntity.size());
    }


}
