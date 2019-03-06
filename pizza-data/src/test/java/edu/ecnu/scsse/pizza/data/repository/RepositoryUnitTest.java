package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import edu.ecnu.scsse.pizza.data.repository.AddressJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RepositoryUnitTest {

    @Autowired
    AddressJpaRepository addressJpaRepository;


    @Test
    public void contextLoads() {
        List<AddressEntity> addressEntity=addressJpaRepository.findAll();
        System.out.println(addressEntity.get(0).getAddress());
    }

}
