package edu.ecnu.scsse.pizza.bussiness.server.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.ShopIngredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.BatchImportResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.BuyIngredientRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminService;
import edu.ecnu.scsse.pizza.bussiness.server.service.IngredientService;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class IngredientControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private IngredientController ingredientController;
    @Mock
    private IngredientService ingredientService;


    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testGetIngredientList() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/ingredient/getIngredientList")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(null));
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确",status==200);
    }

//    @Test
//    public void testBatchImportByExcelFile() throws Exception{
//        RequestBuilder request = MockMvcRequestBuilders.post("/ingredient/batchImportByExcelFile").param("MultipartFile","1")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(JSON.toJSONString(null));
//        MvcResult mvcResult = mockMvc.perform(request).andReturn();
//        int status = mvcResult.getResponse().getStatus();
//        String content = mvcResult.getResponse().getContentAsString();
//        Assert.assertTrue("正确",status==200);
//    }

    @Test
    public void testEditIngredientDetail() throws Exception{
        IngredientDetailRequest request = new IngredientDetailRequest();
        SimpleResponse response = new SimpleResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(ingredientService.editIngredientDetail(eq(request), anyInt())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredient/editIngredientDetail").param("adminId","1")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testAddNewIngredient() throws Exception{
        IngredientDetailRequest request=new IngredientDetailRequest();
        SimpleResponse response=new SimpleResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(ingredientService.addNewIngredient(eq(request), anyInt())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredient/addNewIngredient").param("adminId","1")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }


    @Test
    public void testEditIngredientStatus() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/ingredient/editIngredientStatus").param("ingredientId","1").param("adminId","1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(null));
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确",status==200);
    }

    @Test
    public void testGetAlarmList() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/ingredient/getAlarmList")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(null));
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确",status==200);
    }

    @Test
    public void testBuyIngredient() throws Exception{
        BuyIngredientRequest request = new BuyIngredientRequest();
        SimpleResponse response = new SimpleResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(ingredientService.buyIngredient(eq(request), anyInt())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredient/buyIngredient").param("adminId","1")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeleteIngredient() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/ingredient/deleteIngredient").param("id","1").param("adminId","1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(null));
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确",status==200);
    }
}
