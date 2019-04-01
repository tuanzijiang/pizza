package edu.ecnu.scsse.pizza.consumer.server.service;

import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import edu.ecnu.scsse.pizza.consumer.server.model.user.*;
import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserEntity;
import edu.ecnu.scsse.pizza.data.enums.Sex;
import edu.ecnu.scsse.pizza.data.enums.UpdateUserType;
import edu.ecnu.scsse.pizza.data.repository.AddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserAddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserJpaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private AddressJpaRepository addressJpaRepository;
    @Mock
    private UserAddressJpaRepository userAddressJpaRepository;

    private UserEntity userEntity;

    private UserAddressEntity userAddressEntity;

    private AddressEntity addressEntity;

    private Optional<UserEntity> userEntityOptional;

    private Optional<AddressEntity> addressEntityOptional;

    private Optional<UserAddressEntity> userAddressEntityOptional;

    private Optional<UserEntity> nullUser;

    private String password = "passwww123";

    private Address address;


    @InjectMocks
    private UserService userService;

    @Before
    public void before() {
        nullUser = Optional.ofNullable(null);

        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("miao");
        userEntity.setPhone("12345");
        userEntity.setEmail("12345");
        userEntity.setPassword(password);
        userEntity.setDefaultUserAddressId(1);

        addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setAddress("addr");

        address = new Address();
        address.setId(1);
        address.setAddress("上海市普陀区中山北路3663号");
        address.setAddressDetail("华东师范大学理科楼");
        address.setSex(Sex.FEMALE);
        address.setName("name");
        address.setPhone("123");

        userAddressEntity = new UserAddressEntity();
        userAddressEntity.setId(1);
        userAddressEntity.setAddressId(1);
        userAddressEntity.setUserId(1);
        userAddressEntity.setName("name");
        userAddressEntity.setPhone("12345");
        userAddressEntity.setSex(0);
        userAddressEntity.setAddressDetail("detail");

        List<UserAddressEntity> userAddressEntityList = new ArrayList<>();
        userAddressEntityList.add(userAddressEntity);

        userEntityOptional = Optional.ofNullable(userEntity);
        addressEntityOptional = Optional.ofNullable(addressEntity);
        userAddressEntityOptional = Optional.ofNullable(userAddressEntity);

        when(addressJpaRepository.findById(anyInt())).thenReturn(addressEntityOptional);
        when(addressJpaRepository.save(any())).thenReturn(addressEntity);

        when(userJpaRepository.findById(anyInt())).thenReturn(userEntityOptional);
        when(userAddressJpaRepository.findByUserIdAndAddressId(anyInt(), anyInt())).thenReturn(userAddressEntityOptional);
        when(userAddressJpaRepository.findByUserId(anyInt())).thenReturn(userAddressEntityList);


        when(userJpaRepository.findByEmail(anyString())).thenReturn(userEntityOptional);
        when(userJpaRepository.findByPhone(userEntity.getPhone())).thenReturn(userEntityOptional);
        when(userJpaRepository.save(any())).thenReturn(userEntity);

    }


    @Test
    public void testFetchUserSuccess() {

        FetchUserResponse response = userService.fetchUser(1);
    }

    @Test
    public void testFetchUserFailure() {
        when(userJpaRepository.findById(anyInt())).thenReturn(nullUser);
        FetchUserResponse response = userService.fetchUser(1);
    }

    @Test
    public void testUpdateUserSuccess() {
        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserId(1);
        updateUserRequest.setType(UpdateUserType.NAME);
        updateUserRequest.setValue("name2");
        updateUserResponse = userService.updateUser(updateUserRequest);
        Assert.assertEquals(updateUserResponse.getResultType(), ResultType.SUCCESS);

        updateUserRequest.setType(UpdateUserType.PHONE);
        updateUserRequest.setValue("111111");
        updateUserResponse = userService.updateUser(updateUserRequest);
        Assert.assertEquals(updateUserResponse.getResultType(), ResultType.SUCCESS);

        updateUserRequest.setType(UpdateUserType.EMAIL);
        updateUserRequest.setValue("mail@123.com");
        updateUserResponse = userService.updateUser(updateUserRequest);
        Assert.assertEquals(updateUserResponse.getResultType(), ResultType.SUCCESS);

        updateUserRequest.setType(UpdateUserType.BIRTHDAY);
        updateUserRequest.setValue("1996-12-23");
        updateUserResponse = userService.updateUser(updateUserRequest);
        Assert.assertEquals(updateUserResponse.getResultType(), ResultType.SUCCESS);

        updateUserRequest.setType(UpdateUserType.CITY);
        updateUserRequest.setValue("shanghai");
        updateUserResponse = userService.updateUser(updateUserRequest);
        Assert.assertEquals(updateUserResponse.getResultType(), ResultType.SUCCESS);

        updateUserRequest.setType(UpdateUserType.IMGAE);
        updateUserRequest.setValue("url");
        updateUserResponse = userService.updateUser(updateUserRequest);
        Assert.assertEquals(updateUserResponse.getResultType(), ResultType.SUCCESS);


        updateUserRequest.setType(UpdateUserType.ADDRESSID);
        updateUserRequest.setValue("1");
        updateUserResponse = userService.updateUser(updateUserRequest);
        Assert.assertEquals(updateUserResponse.getResultType(), ResultType.SUCCESS);

        when(userJpaRepository.findById(anyInt())).thenReturn(nullUser);
        updateUserResponse = userService.updateUser(updateUserRequest);
        Assert.assertEquals(updateUserResponse.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setType(LoginRequest.Type.PASSWORD);
        loginRequest.setAccount("12345");
        loginRequest.setPassword(password);
        LoginResponse loginResponse = userService.login(loginRequest);
        Assert.assertEquals(loginResponse.getResultType(), ResultType.SUCCESS);

        loginRequest.setType(LoginRequest.Type.VERIFICATION);
        loginRequest.setAccount("12345");
        loginRequest.setPassword("123456");
        loginResponse = userService.login(loginRequest);
        Assert.assertEquals(loginResponse.getResultType(), ResultType.SUCCESS);

    }


    @Test
    public void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setType(LoginRequest.Type.PASSWORD);
        loginRequest.setAccount("12345");
        loginRequest.setPassword("12");
        LoginResponse loginResponse = userService.login(loginRequest);
        Assert.assertEquals(loginResponse.getResultType(), ResultType.FAILURE);

        loginRequest.setType(LoginRequest.Type.VERIFICATION);
        loginRequest.setAccount("12");
        loginRequest.setPassword("123456");
        when(userJpaRepository.findByPhone(anyString())).thenReturn(nullUser);
        loginResponse = userService.login(loginRequest);
        Assert.assertEquals(loginResponse.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testLoginUserNotExists() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setType(LoginRequest.Type.PASSWORD);
        loginRequest.setAccount("12");
        loginRequest.setPassword(password);
        when(userJpaRepository.findByEmail(anyString())).thenReturn(nullUser);
        LoginResponse loginResponse = userService.login(loginRequest);
        Assert.assertEquals(loginResponse.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testLogout() {
        userService.logout(new LogoutRequest());
    }

    @Test
    public void testSignUpSuccess() {
        SignUpResponse signUpResponse;

        // verification
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setPhone("12345");
        signUpResponse = userService.signUp(signUpRequest);
        Assert.assertEquals(signUpResponse.getResultType(), ResultType.SUCCESS);


        //password
        signUpRequest.setEmail("12345");
        signUpRequest.setPassword(password);
        signUpResponse = userService.signUp(signUpRequest);
        Assert.assertEquals(signUpResponse.getResultType(), ResultType.SUCCESS);

    }

    @Test
    public void testSignUpFailure() {
        SignUpResponse signUpResponse;

        // verification
        SignUpRequest signUpRequest = new SignUpRequest();
        when(userJpaRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        signUpResponse = userService.signUp(signUpRequest);
        Assert.assertEquals(signUpResponse.getResultType(), ResultType.FAILURE);


        //password
        signUpRequest.setEmail("12345");
        signUpRequest.setPassword("1");
        signUpResponse = userService.signUp(signUpRequest);
        Assert.assertEquals(signUpResponse.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testUpdateUserAddressSuccess() {
        AddUserAddressRequest addUserAddressRequest = new AddUserAddressRequest();
        addUserAddressRequest.setUserId(1);
        addUserAddressRequest.setAddress(address);
        AddUserAddressResponse response = userService.updateUserAddress(addUserAddressRequest);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testUpdateUserAddressFailure() {
        AddUserAddressRequest addUserAddressRequest = new AddUserAddressRequest();
        addUserAddressRequest.setUserId(1);
        Address address2 = new Address();
        addUserAddressRequest.setAddress(address2);
        AddUserAddressResponse response = userService.updateUserAddress(addUserAddressRequest);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testAddUserAddressSuccess() {
        AddUserAddressRequest addUserAddressRequest = new AddUserAddressRequest();
        addUserAddressRequest.setUserId(1);
        addUserAddressRequest.setAddress(address);
        AddUserAddressResponse response = userService.addUserAddress(addUserAddressRequest);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testAddUserAddressFailure() {
        AddUserAddressRequest addUserAddressRequest = new AddUserAddressRequest();
        addUserAddressRequest.setUserId(1);
        Address address2 = new Address();
        addUserAddressRequest.setAddress(address2);
        AddUserAddressResponse response = userService.addUserAddress(addUserAddressRequest);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);

    }

    @Test
    public void testFetchUserAddressesSuccess() {
        FetchUserAddressesRequest fetchUserAddressesRequest = new FetchUserAddressesRequest();
        fetchUserAddressesRequest.setUserId(1);
        FetchUserAddressesResponse fetchUserAddressesResponse = userService.fetchUserAddresses(fetchUserAddressesRequest);
        Assert.assertEquals(fetchUserAddressesResponse.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testFetchUserAddressesFailure() {
        FetchUserAddressesRequest fetchUserAddressesRequest = new FetchUserAddressesRequest();
        when(userAddressJpaRepository.findByUserId(fetchUserAddressesRequest.getUserId())).thenThrow(DataIntegrityViolationException.class);
        FetchUserAddressesResponse fetchUserAddressesResponse = userService.fetchUserAddresses(fetchUserAddressesRequest);
        Assert.assertEquals(fetchUserAddressesResponse.getResultType(), ResultType.FAILURE);
    }

}