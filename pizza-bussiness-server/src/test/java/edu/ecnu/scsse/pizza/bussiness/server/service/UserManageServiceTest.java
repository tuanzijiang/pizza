package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.FakeAdmin;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.User;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserEntity;
import edu.ecnu.scsse.pizza.data.repository.UserAddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class UserManageServiceTest extends TestApplication{
    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserAddressJpaRepository userAddressJpaRepository;

    @InjectMocks
    private UserManageService userManageService;

    @Before
    public void setUp(){

    }

    @Test
    public void testGetUserList(){
        List<UserEntity> userEntities = FakeAdmin.fakeUserList();
        when(userJpaRepository.findAll()).thenReturn(userEntities);
        when(userAddressJpaRepository.findByUserIdAndAddressId(anyInt(),anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        List<User> users = userManageService.getUserList();
        assertEquals(userEntities.size(),users.size());
    }

    @Test
    public void testGetUserListWithNoData(){
        List<UserEntity> userEntities = new ArrayList<>();
        when(userJpaRepository.findAll()).thenReturn(userEntities);
        when(userAddressJpaRepository.findByUserIdAndAddressId(anyInt(),anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        List<User> users = userManageService.getUserList();
        assertEquals(userEntities.size(),users.size());
    }

    @Test
    public void testGetUserListWithoutDefaultAddressId(){
        List<UserEntity> userEntities = FakeAdmin.fakeUserList();
        when(userJpaRepository.findAll()).thenReturn(userEntities);
        when(userAddressJpaRepository.findByUserIdAndAddressId(anyInt(),anyInt())).thenReturn(Optional.empty());
        List<User> users = userManageService.getUserList();
        assertEquals(userEntities.size(),users.size());
    }
}
