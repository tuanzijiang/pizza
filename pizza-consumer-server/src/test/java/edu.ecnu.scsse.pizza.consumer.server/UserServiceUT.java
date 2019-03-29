package edu.ecnu.scsse.pizza.consumer.server;

import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import edu.ecnu.scsse.pizza.consumer.server.model.user.*;
import edu.ecnu.scsse.pizza.consumer.server.service.UserService;
import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import edu.ecnu.scsse.pizza.data.enums.AddressTag;
import edu.ecnu.scsse.pizza.data.enums.Sex;
import edu.ecnu.scsse.pizza.data.enums.UpdateUserType;
import edu.ecnu.scsse.pizza.data.repository.AddressJpaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceUT {

    @Autowired
    AddressJpaRepository addressJpaRepository;

    @Autowired
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserServiceUT.class);


    @Test
    public void testFetchUserSuccess() {
        FetchUserResponse response=userService.fetchUser(1);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
        log.info(response.getUser().getEmail());
    }

    @Test
    public void testFetchUserFailure() {
        Assert.assertEquals(userService.fetchUser(0).getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testUpdateUserNameSuccess() {
        UpdateUserRequest request=new UpdateUserRequest();
        request.setType(UpdateUserType.NAME);
        request.setValue("updatedName");
        request.setUserId(1);

        UpdateUserResponse response=userService.updateUser(request);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testUpdateUserDefaultAddressIdSuccess() {
        UpdateUserRequest request=new UpdateUserRequest();
        request.setType(UpdateUserType.ADDRESSID);
        request.setValue("10");
        request.setUserId(1);

        UpdateUserResponse response=userService.updateUser(request);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testUpdateUserFailure() {
        UpdateUserRequest request=new UpdateUserRequest();
        request.setType(UpdateUserType.NAME);
        request.setValue("updatedName");
        request.setUserId(0);

        UpdateUserResponse response=userService.updateUser(request);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testLoginSuccess() {
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setAccount("247221925@qq.com");
        loginRequest.setPassword("111");
        loginRequest.setType(LoginRequest.Type.PASSWORD);

        LoginResponse response=userService.login(loginRequest);

        log.debug(response.getUser().getName());
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testLoginUserNotPresentFailure() {
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setAccount("xxx@qq.com");
        loginRequest.setPassword("111");
        loginRequest.setType(LoginRequest.Type.PASSWORD);

        LoginResponse response=userService.login(loginRequest);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testLoginUserPasswordIncorrectFailure() {
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setAccount("247221925@qq.com");
        loginRequest.setPassword("1111");
        loginRequest.setType(LoginRequest.Type.PASSWORD);

        LoginResponse response=userService.login(loginRequest);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testSignUpSuccess(){
        SignUpRequest signUpRequest=new SignUpRequest();
        signUpRequest.setEmail("testSignUp@qq.com");
        signUpRequest.setPassword("caomiao233");
        signUpRequest.setPhone("15317299002");

        SignUpResponse response=userService.signUp(signUpRequest);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testSignUpPasswordFormatFailure(){
        SignUpRequest signUpRequest=new SignUpRequest();
        signUpRequest.setEmail("password@qq.com");
        signUpRequest.setPassword("&&caomiao233");
        signUpRequest.setPhone("15317299001");

        SignUpResponse response=userService.signUp(signUpRequest);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testSignUpUserHasExistedFailure(){
        SignUpRequest signUpRequest=new SignUpRequest();
        signUpRequest.setEmail("867616074@qq.com");
        signUpRequest.setPassword("caomiao233");
        signUpRequest.setPhone("15317299001");

        SignUpResponse response=userService.signUp(signUpRequest);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }


    @Test
    public void testCheckPasswordFormat() {
        Assert.assertFalse(userService.checkPasswordFormat("123"));
        Assert.assertFalse(userService.checkPasswordFormat("f123"));
        Assert.assertFalse(userService.checkPasswordFormat("ffff123@@@"));
        Assert.assertFalse(userService.checkPasswordFormat("fffssssssssssssssssssssssss123"));
        Assert.assertFalse(userService.checkPasswordFormat("fffffffff"));

        Assert.assertTrue(userService.checkPasswordFormat("ffffff123"));

    }

    @Test
    public void testAddAddressSuccess() {
        AddUserAddressRequest addUserAddressRequest=new AddUserAddressRequest();
        Address address=new Address();
        address.setAddress("华东师范大学（闵行校区）");
        address.setAddressDetail("第三宿舍");
        address.setName("cao miao");
        address.setPhone("123");
        address.setSex(Sex.FEMALE);
        //address.setTag(AddressTag.COMMON);

        addUserAddressRequest.setAddress(address);
        addUserAddressRequest.setUserId(1);

        AddUserAddressResponse response=userService.addUserAddress(addUserAddressRequest);
        Assert.assertEquals(ResultType.SUCCESS, response.getResultType());
    }

    @Test
    public void testAddAddressGetLocationFailure() {
        AddUserAddressRequest addUserAddressRequest=new AddUserAddressRequest();
        Address address=new Address();
        address.setAddress("啦啦啦xxxx");
        address.setAddressDetail("第三宿舍");
        address.setName("cao miao");
        address.setPhone("123");
        address.setSex(Sex.FEMALE);
        //address.setTag(AddressTag.COMMON);

        addUserAddressRequest.setAddress(address);
        addUserAddressRequest.setUserId(1);

        AddUserAddressResponse response=userService.addUserAddress(addUserAddressRequest);
        Assert.assertEquals(ResultType.FAILURE, response.getResultType());
    }


    @Test
    public void testUpdateAddressSuccess() {
        AddUserAddressRequest request=new AddUserAddressRequest();
        Address address=new Address();
        address.setId(10);
        address.setAddress("address");
        address.setAddressDetail("detail changed!");
        address.setName("jiang");
        address.setPhone("12312312223");
        address.setSex(Sex.FEMALE);
        //address.setTag(AddressTag.COMMON);
        request.setAddress(address);
        request.setUserId(1);

        request.setAddress(address);
        AddUserAddressResponse response1=userService.updateUserAddress(request);
        Assert.assertEquals(response1.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testFetchAddressesSuccess() {
        FetchUserAddressesRequest request=new FetchUserAddressesRequest();
        request.setUserId(1);
        FetchUserAddressesResponse response=userService.fetchUserAddresses(request);
        System.out.println(response.getAddresses().size());
    }




}
