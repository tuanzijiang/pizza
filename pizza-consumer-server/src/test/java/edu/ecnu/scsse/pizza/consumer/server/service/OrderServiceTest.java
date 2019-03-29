package edu.ecnu.scsse.pizza.consumer.server.service;

import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.exception.PayFailureException;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.repository.OrderJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void paid() {
        double price = 20.12;
        String orderUuid = "AAA";
        when(orderJpaRepository.updateStateAndTotalPriceByOrderUuid(OrderStatus.PAID.getDbValue(), price, orderUuid))
                .thenReturn(1);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("out_trade_no")).thenReturn(orderUuid);
        when(request.getParameter("total_amount")).thenReturn(String.valueOf(price));

        boolean result = orderService.paid(request);
        assertTrue(result);
        verify(orderJpaRepository).updateStateAndTotalPriceByOrderUuid(OrderStatus.PAID.getDbValue(), price, orderUuid);
    }

    @Test
    public void payRequest() throws PayFailureException, IllegalArgumentException {
        double price = 20.12;
        String orderUuid = "AAA";

        String form = orderService.payRequest(orderUuid, price);

        assertNotNull(form);
        assertTrue(form.startsWith("<form"));
    }

}