package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.user.UserManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.User;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserEntity;
import edu.ecnu.scsse.pizza.data.repository.UserAddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserAddressJpaRepository userAddressJpaRepository;

    public List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        List<UserEntity> userEntityList = userJpaRepository.findAll();
        if(userEntityList.size()!=0){
            userList = userEntityList.stream().map(this::convert).collect(Collectors.toList());
        }
        else{
            NotFoundException e = new NotFoundException("User list is not found.");
            log.warn("Fail to find the user list.", e);
        }
        return userList;
    }

    private User convert(UserEntity userEntity){
        User user = new User();
        CopyUtils.copyProperties(userEntity, user);
        user.setId(String.valueOf(userEntity.getId()));
        if(userEntity.getBirthday()!=null){
            String birthdayPattern = "yyyy/MM/dd";
            DateFormat df = new SimpleDateFormat(birthdayPattern);
            user.setBirthday(df.format(userEntity.getBirthday()));
        }
        if(userEntity.getLatestLoginTime()!=null){
            String loginTimePattern = "yyyy/MM/dd hh:MM:ss";
            DateFormat df2 = new SimpleDateFormat(loginTimePattern);
            user.setLatestLoginTime(df2.format(userEntity.getLatestLoginTime()));
        }

        int userId = userEntity.getId();
        if(userEntity.getDefaultUserAddressId()!=null){
            int addressId = userEntity.getDefaultUserAddressId();
            Optional<UserAddressEntity> userAddressEntityOptional = userAddressJpaRepository.findByUserIdAndAddressId(userId,addressId);
            if(userAddressEntityOptional.isPresent()){
                UserAddressEntity userAddressEntity = userAddressEntityOptional.get();
                String userAddressDetail = userAddressEntity.getAddressDetail();
                user.setDefaultUserAddress(userAddressDetail);
            }
            else
                user.setDefaultUserAddress("无");
        }
        return user;
    }


}
