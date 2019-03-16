package edu.ecnu.scsse.pizza.consumer.server.service;

import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.User;
import edu.ecnu.scsse.pizza.consumer.server.model.user.*;
import edu.ecnu.scsse.pizza.consumer.server.utils.EntityConverter;
import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserEntity;
import edu.ecnu.scsse.pizza.data.repository.AddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserAddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


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
     *
     * @param userId
     * @return
     */
    public FetchUserResponse fetchUser(int userId) {
        FetchUserResponse fetchUserResponse = new FetchUserResponse();

        Optional<UserEntity> userEntity = userJpaRepository.findById(userId);
        if (userEntity.isPresent()) {
            User user = EntityConverter.convert(userEntity.get());
            Integer addressId = userEntity.get().getDefaultUserAddressId();
            if (addressId != null) {
                Optional<UserAddressEntity> userAddressEntityOptional = userAddressJpaRepository.findByUserIdAndAddressId(
                        user.getId(),
                        addressId);
                Optional<AddressEntity> addressEntity = addressJpaRepository.findById(addressId);
                Address address = EntityConverter.convert(userAddressEntityOptional.get(), addressEntity.get());
                user.setAddress(address);
            }
            fetchUserResponse.setUser(user);
            fetchUserResponse.setResultType(ResultType.SUCCESS);
        } else {
            fetchUserResponse.setResultType(ResultType.FAILURE);
        }

        return fetchUserResponse;
    }


    /**
     * 更新当前用户信息
     *
     * @param updateUserRequest
     * @return
     */
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(updateUserRequest.getUserId());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            switch (updateUserRequest.getType()) {
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
                    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
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
                    break;
                case ADDRESSID:
                    userEntity.setDefaultUserAddressId(Integer.valueOf(updateUserRequest.getValue()));
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
     * TODO: 手机验证码登录
     *
     * @param loginRequest
     * @return
     */
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        switch (loginRequest.getType()) {
            case PASSWORD:
                Optional<UserEntity> userEntityOptional = userJpaRepository.findByEmail(loginRequest.getAccount());

                if (userEntityOptional.isPresent()) {
                    UserEntity userEntity = userEntityOptional.get();

                    // verify password
                    if (userEntity.getPassword().equals(loginRequest.getPassword())) {
                        User user = EntityConverter.convert(userEntity);
                        loginResponse.setUser(user);
                    } else {
                        loginResponse.setResultType(ResultType.FAILURE);
                    }
                } else {
                    loginResponse.setResultType(ResultType.FAILURE);
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
     *
     * @param logoutRequest
     * @return
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        // Do nothing
        LogoutResponse logoutResponse = new LogoutResponse();
        return logoutResponse;
    }

    /**
     * 注册
     *
     * @param signUpRequest
     * @return
     */
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = new SignUpResponse();

        if (!checkPasswordFormat(signUpRequest.getPassword())) {
            signUpResponse.setResultType(ResultType.FAILURE);
            return signUpResponse;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(signUpRequest.getPhone());
        userEntity.setPassword(signUpRequest.getPassword());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setName("user" + userEntity.getPhone());
        try {
            userEntity = userJpaRepository.save(userEntity);
            User user = EntityConverter.convert(userEntity);

            signUpResponse.setUser(user);
        } catch (Exception e) {
            signUpResponse.setResultType(ResultType.FAILURE);
        }

        return signUpResponse;
    }


    /**
     * 更新当前用户的地址信息
     *
     * @param addUserAddressRequest
     * @return
     */
    public AddUserAddressResponse updateUserAddress(AddUserAddressRequest addUserAddressRequest) {
        AddUserAddressResponse response = new AddUserAddressResponse();
        Address address = addUserAddressRequest.getAddress();

        if (address.getId() == null || address.getId() <= 0) {
            response.setResultType(ResultType.FAILURE);
            response.setErrorMsg("address id not defined");
            return response;
        }
        try {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setId(address.getId());
            addressEntity.setAddress(address.getAddress());
            addressEntity = addressJpaRepository.save(addressEntity);

            UserAddressEntity userAddressEntity = new UserAddressEntity();
            userAddressEntity.setAddressId(addressEntity.getId());
            userAddressEntity.setUserId(addUserAddressRequest.getUserId());
            userAddressEntity.setAddressDetail(address.getAddressDetail());
            userAddressEntity.setName(address.getName());
            userAddressEntity.setPhone(address.getPhone());
            userAddressEntity.setSex(address.getSex().getDbValue());
            userAddressEntity.setTag(address.getTag().getDbValue());
            userAddressJpaRepository.updateByUserIdAndAddressId(userAddressEntity);
        } catch (Exception e) {
            response.setResultType(ResultType.FAILURE);
        }

        return response;
    }


    /**
     * 添加地址信息
     *
     * @param addUserAddressRequest
     * @return
     */
    public AddUserAddressResponse addUserAddress(AddUserAddressRequest addUserAddressRequest) {
        AddUserAddressResponse response = new AddUserAddressResponse();
        Address address = addUserAddressRequest.getAddress();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress(address.getAddress());
        try {
            addressEntity = addressJpaRepository.save(addressEntity);
        } catch (Exception e) {
            response.setResultType(ResultType.FAILURE);
            return response;
        }

        UserAddressEntity userAddressEntity = new UserAddressEntity();
        userAddressEntity.setAddressId(addressEntity.getId());
        userAddressEntity.setUserId(addUserAddressRequest.getUserId());
        userAddressEntity.setAddressDetail(address.getAddressDetail());
        userAddressEntity.setName(address.getName());
        userAddressEntity.setPhone(address.getPhone());
        userAddressEntity.setSex(address.getSex().getDbValue());
        userAddressEntity.setTag(address.getTag().getDbValue());

        try {
            userAddressJpaRepository.save(userAddressEntity);
        } catch (Exception e) {
            response.setResultType(ResultType.FAILURE);
        }


        return response;
    }

    /**
     * 获取当前用户的地址信息
     *
     * @param fetchUserAddressesRequest
     * @return
     */
    public FetchUserAddressesResponse fetchUserAddresses(FetchUserAddressesRequest fetchUserAddressesRequest) {
        FetchUserAddressesResponse response = new FetchUserAddressesResponse();

        try {

            List<UserAddressEntity> userAddressEntityList =
                    userAddressJpaRepository.findByUserId(fetchUserAddressesRequest.getUserId());
            List<Address> addresses = new ArrayList<>();
            for (UserAddressEntity userAddressEntity : userAddressEntityList) {
                Address address = EntityConverter.convert(userAddressEntity,
                        addressJpaRepository.findById(userAddressEntity.getAddressId()).get());
                addresses.add(address);
            }

            response.setAddresses(addresses);
        }catch (Exception e) {
            response.setResultType(ResultType.FAILURE);
        }

        return response;
    }


    public boolean checkPasswordFormat(String password) {
        return Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,11}$", password);
    }

}
