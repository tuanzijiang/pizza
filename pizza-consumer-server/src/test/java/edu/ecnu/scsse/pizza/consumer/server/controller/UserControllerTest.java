package edu.ecnu.scsse.pizza.consumer.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ecnu.scsse.pizza.consumer.server.model.user.*;
import edu.ecnu.scsse.pizza.consumer.server.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testFetchUser() throws Exception {
        FetchUserRequest fetchUserRequest = new FetchUserRequest();
        FetchUserResponse fetchUserResponse = new FetchUserResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(fetchUserRequest);
        when(userService.fetchUser(anyInt())).thenReturn(fetchUserResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/fetchUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        UpdateUserResponse response = new UpdateUserResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(userService.updateUser(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/updateUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest request = new LoginRequest();
        LoginResponse response = new LoginResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(userService.login(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/fetchLoginStatus")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testLogout() throws Exception {
        LogoutRequest request = new LogoutRequest();
        LogoutResponse response = new LogoutResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(userService.logout(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/fetchLogoutStatus")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testSignUp() throws Exception {
        SignUpRequest request = new SignUpRequest();
        SignUpResponse response = new SignUpResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(userService.signUp(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/addUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testFetchUserAddresses() throws Exception {
        FetchUserAddressesRequest request = new FetchUserAddressesRequest();
        FetchUserAddressesResponse response = new FetchUserAddressesResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(userService.fetchUserAddresses(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/fetchUserAddresses")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testAddUserAddress() throws Exception {
        AddUserAddressRequest request = new AddUserAddressRequest();
        AddUserAddressResponse response = new AddUserAddressResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(userService.addUserAddress(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/addUserAddress")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testUpdateUserAddress() throws Exception {
        AddUserAddressRequest request = new AddUserAddressRequest();
        AddUserAddressResponse response = new AddUserAddressResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(userService.addUserAddress(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/updateUserAddress")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

}
