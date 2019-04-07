package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.FakeFactory;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Shop;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.repository.IngredientJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.ShopIngredientJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ShopServiceTest extends TestApplication{
    @Mock
    private PizzaShopJpaRepository shopJpaRepository;

    @Mock
    private ShopIngredientJpaRepository shopIngredientJpaRepository;

    @Mock
    private IngredientJpaRepository ingredientJpaRepository;

    @Mock
    private OperateLoggerService operateLoggerService;

    @InjectMocks
    private ShopService shopService;

    private ShopDetailRequest request;

    @Before
    public void setUp(){
        request = new ShopDetailRequest();
    }

    @Test
    public void testGetShopList(){
        List<PizzaShopEntity> shopEntities = FakeFactory.fakeShops();
        when(shopJpaRepository.findAll()).thenReturn(shopEntities);
        when(shopIngredientJpaRepository.findByShopId(anyInt())).thenReturn(FakeFactory.fakeShopIngredients(1));
        when(ingredientJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeIngredient(1)));
        List<ShopManageResponse> shops = shopService.getShopList();
        assertEquals(shopEntities.size(),shops.size());
    }

    @Test
    public void testEditShopDetailByName() throws BusinessServerException,ParseException{
        int shopId = 1;
        String shopName = "newName";
        PizzaShopEntity shopEntity = FakeFactory.fakeShop();
        shopEntity.setName(shopName);
        CopyUtils.copyProperties(shopEntity,request);
        request.setId(String.valueOf(shopEntity.getId()));
        request.setName(shopEntity.getName());
        request.setStartTime("2019-04-07 15:16:00");
        request.setEndTime("2019-04-07 15:16:00");
        when(shopJpaRepository.findById(anyInt())).thenReturn(Optional.of(shopEntity));
        when(shopJpaRepository.saveAndFlush(any())).thenReturn(shopEntity);

        ShopDetailResponse response = shopService.editShopDetail(request);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testAddNewShop() throws BusinessServerException{
        PizzaShopEntity shopEntity = FakeFactory.fakeShop();
        CopyUtils.copyProperties(shopEntity,request);
        request.setId(String.valueOf(shopEntity.getId()));
        request.setName(shopEntity.getName());
        request.setStartTime("2019-04-07 15:16:00");
        request.setEndTime("2019-04-07 15:16:00");
        when(shopJpaRepository.saveAndFlush(shopEntity)).thenReturn(shopEntity);
        ShopDetailResponse response = shopService.addNewShop(request);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }
}
