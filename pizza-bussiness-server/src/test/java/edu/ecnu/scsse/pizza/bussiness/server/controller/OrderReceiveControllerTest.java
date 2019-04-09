package edu.ecnu.scsse.pizza.bussiness.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderReceiveService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class OrderReceiveControllerTest extends TestApplication{
    private MockMvc mockMvc;
    @Mock
    private OrderReceiveService orderReceiveService;
    @InjectMocks
    private OrderReceiveController orderReceiveController;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderReceiveController).build();
    }

    @Test
    public void testGetReceiveShopId() throws Exception {
        OrderReceiveRequest request = new OrderReceiveRequest();
        OrderReceiveResponse response = new OrderReceiveResponse();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(request);
        when(orderReceiveService.getReceiveShopId(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/orderReceive/getReceiveShopId")
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
