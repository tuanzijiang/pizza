package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.UserManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.User;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserEntity;
import edu.ecnu.scsse.pizza.data.repository.UserAddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserManageService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserAddressJpaRepository userAddressJpaRepository;

    public UserManageResponse getUserList(){
        UserManageResponse userManageResponse;
        List<UserEntity> userEntityList = userJpaRepository.getAll();
        userManageResponse = new UserManageResponse();
        List<User> userList = userEntityList.stream().map(this::convert).collect(Collectors.toList());
        userManageResponse.setUserList(userList);
        return userManageResponse;
    }

    private User convert(UserEntity userEntity){
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        user.setId(String.valueOf(userEntity.getId()));
        
        String birthdayPattern = "yyyy/MM/dd";
        DateFormat df = new SimpleDateFormat(birthdayPattern);
        user.setBirthday(df.format(userEntity.getBirthday()));

        String loginTimePattern = "yyyy/MM/dd hh/MM/ss";
        DateFormat df2 = new SimpleDateFormat(loginTimePattern);
        user.setLatestLoginTime(df2.format(userEntity.getLatestLoginTime()));

        int userId = userEntity.getId();
        int addressId = userEntity.getDefaultUserAddressId();
        Optional<UserAddressEntity> userAddressEntityOptional = userAddressJpaRepository.findByUserIdAndAddressId(userId,addressId);
        if(userAddressEntityOptional.isPresent()){
            UserAddressEntity userAddressEntity = userAddressEntityOptional.get();
            String userAddressDetail = userAddressEntity.getAddressDetail();
            user.setDefaultUserAddress(userAddressDetail);
        }
        else{
            user.setDefaultUserAddress("无");
        }
        return user;
    }


}
