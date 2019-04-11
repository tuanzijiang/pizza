package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.bean.UserAddressBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressJpaRepositoryTest {

    @Autowired
    AddressJpaRepository repository;

    @Autowired
    UserAddressJpaRepository userAddressJpaRepository;

    @Test
    public void test() {
        UserAddressBean entity = userAddressJpaRepository.findUserAddressBeanByUserIdAndAddressId(1, 9).get();

        System.out.println(entity.getAddress());
    }

}