package edu.ecnu.scsse.pizza.consumer.server.service;

import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.User;
import edu.ecnu.scsse.pizza.consumer.server.model.user.*;
import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserEntity;
import edu.ecnu.scsse.pizza.data.enums.AddressTag;
import edu.ecnu.scsse.pizza.data.enums.Sex;
import edu.ecnu.scsse.pizza.data.repository.AddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserAddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    AddressJpaRepository addressJpaRepository;
    @Autowired
    UserAddressJpaRepository userAddressJpaRepository;

    /**
     * 获取当前用户信息
     * @param userId
     * @return
     */
    public FetchUserResponse getUserInfo(int userId){
        FetchUserResponse fetchUserResponse=new FetchUserResponse();

        Optional<UserEntity> userEntity=userJpaRepository.findById(userId);
        if(userEntity.isPresent()) {
            User user=convertUserEntity(userEntity.get());
            int addressId=userEntity.get().getDefaultUserAddressId();
            Optional<UserAddressEntity> userAddressEntityOptional = userAddressJpaRepository.findByUserIdAndAddressId (
                            user.getId(),
                            addressId);
            Optional<AddressEntity> addressEntity = addressJpaRepository.findById(addressId);
            Address address=convertAddressEntity(userAddressEntityOptional.get(), addressEntity.get());
            user.setAddress(address);
            fetchUserResponse.setUser(user);
            fetchUserResponse.setResultType(ResultType.SUCCESS);
        } else {
            fetchUserResponse.setResultType(ResultType.FAILURE);
        }

        return fetchUserResponse;
    }

    /**
     * 更新当前用户信息
     * @param updateUserRequest
     * @return
     */
    public UpdateUserResponse updateUserInfo(UpdateUserRequest updateUserRequest) {
        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(updateUserRequest.getUserId());
        if(userEntityOptional.isPresent()) {
            UserEntity userEntity=userEntityOptional.get();
            switch (updateUserRequest.getType()){
                case NAME:
                    userEntity.setName(updateUserRequest.getValue());
                    break;
                case PHONE:
                    userEntity.setPhone(updateUserRequest.getValue());
                    break;
                case EMAIL:
                    userEntity.setEmail(updateUserRequest.getValue());
                    break;
                case BIRTHDAY:
                    DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = fmt.parse(updateUserRequest.getValue());
                        userEntity.setBirthday(new java.sql.Date(date.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case CITY:
                    userEntity.setCity(updateUserRequest.getValue());
                    break;
                case IMGAE:
                    userEntity.setImage(updateUserRequest.getValue());
            }
            userJpaRepository.save(userEntity);
            updateUserResponse.setResultType(ResultType.SUCCESS);
        } else {
            updateUserResponse.setResultType(ResultType.FAILURE);
        }
        return updateUserResponse;
    }

    /**
     * 登录
     * TODO: 格式校验, 手机验证码登录
     * @param loginRequest
     * @return
     */
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse=new LoginResponse();
        switch (loginRequest.getType()) {
            case PASSWORD:
                Optional<UserEntity> userEntityOptional=userJpaRepository.findByEmail(loginRequest.getAccount());

                if(userEntityOptional.isPresent()){
                    UserEntity userEntity=userEntityOptional.get();

                    // verify password
                    if(userEntity.getPassword().equals(loginRequest.getPassword())) {
                        User user=convertUserEntity(userEntity);
                        loginResponse.setUser(user);
                    } else {
                        // password incorrect.
                    }
                } else {
                    // account not present.
                }
                break;
            case VERIFICATION:
                // TODO: 手机验证码登录
                break;
        }
        return loginResponse;
    }


    /**
     * 退出
     * // TODO
     * @param logoutRequest
     * @return
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        LogoutResponse logoutResponse=new LogoutResponse();

        return logoutResponse;
    }

    /**
     * TODO: 格式校验
     * @param signUpRequest
     * @return
     */
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse=new SignUpResponse();
        // email格式校验
        UserEntity userEntity=new UserEntity();
        userEntity.setPhone(signUpRequest.getPhone());
        userEntity.setPassword(signUpRequest.getPassword());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity=userJpaRepository.save(userEntity);

        User user =convertUserEntity(userEntity);
        signUpResponse.setUser(user);

        return signUpResponse;
    }


    private User convertUserEntity (UserEntity userEntity) {
        User user = new User(userEntity.getId(), userEntity.getName(), userEntity.getPhone(),
                userEntity.getEmail(), userEntity.getBirthday(), userEntity.getCity(), userEntity.getImage());
        return user;
    }

    private Address convertAddressEntity (UserAddressEntity userAddressEntity, AddressEntity addressEntity) {
        Address address = new Address();

        address.setId(address.getId());
        address.setAddress(addressEntity.getAddress());

        address.setAddressDetail(userAddressEntity.getAddressDetail());
        address.setName(userAddressEntity.getName());
        address.setPhone(userAddressEntity.getPhone());

        address.setSex(Sex.fromDbValue(userAddressEntity.getSex()));
        AddressTag addressTag = AddressTag.fromDbValue(userAddressEntity.getTag());
        if (addressTag != null) {
            address.setTag(addressTag.getExpression());
        }

        return address;
    }

}
