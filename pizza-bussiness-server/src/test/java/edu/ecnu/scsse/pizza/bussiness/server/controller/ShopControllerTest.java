package edu.ecnu.scsse.pizza.bussiness.server.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Shop;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminService;
import edu.ecnu.scsse.pizza.bussiness.server.service.ShopService;
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

import java.text.ParseException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class ShopControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ShopController shopController;
    @Mock
    private ShopService shopService;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shopController).build();
    }

    @Test
    public void testGetShopList() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/shop/getShopList")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(null));
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确",status==200);
    }

    @Test
    public void testGetIngredientListByShopId() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/shop/getIngredientListByShopId").param("shopId","1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(null));
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确",status==200);
    }



    @Test
    public void testEditShopDetailAdminNotLogin()throws Exception{
        ShopDetailRequest request=new ShopDetailRequest();
        ShopDetailResponse response=new ShopDetailResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(shopService.editShopDetail(eq(request), anyInt())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/shop/editShopDetail").param("adminId","1")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


    @Test
    public void testAddNewShop()throws Exception{
        ShopDetailRequest request=new ShopDetailRequest();
        ShopDetailResponse response=new ShopDetailResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(shopService.addNewShop(eq(request), anyInt())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/shop/addNewShop").param("adminId","1")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

}
