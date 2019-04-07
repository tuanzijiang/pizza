package edu.ecnu.scsse.pizza.bussiness.server.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminService;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminServiceTest;
import edu.ecnu.scsse.pizza.bussiness.server.service.DriverService;
import edu.ecnu.scsse.pizza.bussiness.server.service.OperateLoggerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class AdminControllerTest extends TestApplication{
    private MockMvc mockMvc;
    @Mock
    private AdminService adminService;
    @Mock
    private OperateLoggerService loggerService;

    @InjectMocks
    private AdminController adminController;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testAdminLoginSuccessfully() throws Exception{
        LoginRequest request=new LoginRequest();
        LoginResponse response=new LoginResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(adminService.adminLogin(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetOperateLogger() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/admin/getOperateLogger")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(null));
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确",status==200);
    }
}
