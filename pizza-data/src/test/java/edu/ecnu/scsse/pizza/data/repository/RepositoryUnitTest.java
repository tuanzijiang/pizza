package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
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

    @Autowired
    UserAddressJpaRepository userAddressJpaRepository;


    @Test
    public void contextLoads() {
        List<AddressEntity> addressEntity=addressJpaRepository.findAll();
        System.out.println(addressEntity.get(0).getAddress());
    }

    @Test
    public void updateUserAddressTest() {
        UserAddressEntity userAddressEntity=new UserAddressEntity();
        userAddressEntity.setUserId(1);
        userAddressEntity.setAddressId(10);
        userAddressEntity.setTag(0);
        userAddressEntity.setAddressDetail("change detail!!");
        userAddressJpaRepository.updateByUserIdAndAddressId(userAddressEntity);
    }

}
