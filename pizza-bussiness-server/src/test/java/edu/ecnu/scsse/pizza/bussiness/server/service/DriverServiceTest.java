package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.FakeFactory;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Driver;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.DriverEntity;
import edu.ecnu.scsse.pizza.data.domain.MenuEntity;
import edu.ecnu.scsse.pizza.data.domain.MenuIngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.repository.DriverJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DriverServiceTest extends TestApplication {
    @Mock
    private DriverJpaRepository driverJpaRepository;

    @Mock
    private PizzaShopJpaRepository shopJpaRepository;

    @Mock
    private OperateLoggerService operateLoggerService;

    @InjectMocks
    private DriverService mockDriverService;

    private DriverDetailRequest request;

    @Before
    public void setUp(){
        request = new DriverDetailRequest();
    }

    @Test
    public void testGetDriverList(){
        List<DriverEntity> driverEntities = FakeFactory.fakeDriverEntities();
        PizzaShopEntity shop = FakeFactory.fakeShop();
        when(driverJpaRepository.findAll()).thenReturn(driverEntities);
        when(shopJpaRepository.findPizzaShopEntityById(1)).thenReturn(Optional.of(shop));
        List<Driver> drivers = mockDriverService.getDriverList();
        assertEquals(driverEntities.size(),drivers.size());
    }

    @Test
    public void testGetDriverListWithNoData(){
        when(driverJpaRepository.findAll()).thenReturn(new ArrayList<>());
        List<Driver> drivers = mockDriverService.getDriverList();
        assertEquals(0,drivers.size());
    }

    @Test
    public void testEditDriverWithWrongId() throws BusinessServerException{
        int driverId = 1;
        String driverName = "newName";
        DriverEntity driverEntity = FakeFactory.fakeDriver(driverId);
        driverEntity.setName(driverName);
        CopyUtils.copyProperties(driverEntity,request);
        request.setDriverId(driverEntity.getId());
        request.setName(driverEntity.getName());

        when(driverJpaRepository.findById(driverId)).thenReturn(Optional.empty());

        DriverDetailResponse response = mockDriverService.editDriverDetail(request);
        assertEquals(ResultType.FAILURE,response.getResultType());
    }

    @Test
    public void testEditDriverDetailByName() throws BusinessServerException{
        int driverId = 1;
        String driverName = "newName";
        DriverEntity driverEntity = FakeFactory.fakeDriver(driverId);
        driverEntity.setName(driverName);
        CopyUtils.copyProperties(driverEntity,request);
        request.setDriverId(driverEntity.getId());
        request.setName(driverEntity.getName());

        when(driverJpaRepository.findById(driverId)).thenReturn(Optional.of(driverEntity));
        when(driverJpaRepository.saveAndFlush(driverEntity)).thenReturn(driverEntity);

        DriverDetailResponse response = mockDriverService.editDriverDetail(request);
        assertEquals(ResultType.SUCCESS,response.getResultType());
        assertEquals(response.getDriverId(),driverId);
    }

    @Test
    public void testAddNewDriver() throws BusinessServerException{
        int driverId = 1;
        DriverEntity driverEntity = FakeFactory.fakeDriver(driverId);
        CopyUtils.copyProperties(driverEntity,request);
        request.setDriverId(driverId);

        when(driverJpaRepository.saveAndFlush(driverEntity)).thenReturn(driverEntity);

        DriverDetailResponse response = mockDriverService.addNewDriver(request);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }
}
