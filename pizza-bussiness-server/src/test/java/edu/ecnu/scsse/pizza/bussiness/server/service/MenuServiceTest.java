package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.FakeFactory;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.MenuEntity;
import edu.ecnu.scsse.pizza.data.domain.MenuIngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaTag;
import edu.ecnu.scsse.pizza.data.repository.IngredientJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.MenuIngredientJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.MenuJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.OperateLoggerJpaRepository;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MenuServiceTest extends TestApplication{
    //定义JPA对象
    @Mock
    private MenuJpaRepository menuJpaRepository;

    @Mock
    private IngredientJpaRepository ingredientJpaRepository;

    @Mock
    private MenuIngredientJpaRepository menuIngredientJpaRepository;

    @Mock
    private OperateLoggerService operateLoggerService;

    //定义被测试类对象
    @InjectMocks
    private MenuService mockMenuService = new MenuService();

    private MenuDetailRequest request;

    @Before
    public void setUp(){
        request = new MenuDetailRequest(String.valueOf(1),"",null,"", Lists.emptyList(), 0.0,PizzaStatus.IN_SALE,PizzaTag.UNKNOWN);
    }

    @Test
    public void testGetMenuList(){
        List<MenuEntity> menuEntities = FakeFactory.fakeMenuEntities();
        List<IngredientEntity> ingredientEntities = FakeFactory.fakeIngredientEntities();
        when(menuJpaRepository.findAll()).thenReturn(menuEntities);
        when(ingredientJpaRepository.findAll()).thenReturn(ingredientEntities);
        when(menuIngredientJpaRepository.findByMenuIdAndIngredientId(anyInt(),anyInt())).thenReturn(FakeFactory.fakeMenuIngredientEntity(1,1));

        List<MenuDetailResponse> orders = mockMenuService.getMenuList();

        assertEquals(menuEntities.size(), orders.size());
    }

    @Test
    public void testGetTagList(){
        List<String> tagList = new ArrayList<>();
        for(PizzaTag tag:PizzaTag.values()){
            tagList.add(tag.getExpression());
        }
        assertEquals(tagList.size(), mockMenuService.getTagList().size());
    }

    @Test
    public void testEditMenuStatusFromOnToOff(){
        int menuId = 1;
        int adminId = 1;
        MenuEntity menuEntity = FakeFactory.fakeMenu();
        when(menuJpaRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        when(menuJpaRepository.saveAndFlush(menuEntity)).thenReturn(menuEntity);

        SimpleResponse response = mockMenuService.editMenuStatus(menuId,adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());

    }

    @Test
    public void testEditMenuStatusFromOffToOn(){
        int menuId = 1;
        int adminId = 1;
        MenuEntity menuEntity = FakeFactory.fakeMenu();
        menuEntity.setState(PizzaStatus.OFF_SHELF.getDbValue());
        when(menuJpaRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        when(menuJpaRepository.saveAndFlush(menuEntity)).thenReturn(menuEntity);

        SimpleResponse response = mockMenuService.editMenuStatus(menuId,adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());

    }

//    @Test
//    public void testEditMenuStatusWithNotFoundMenu(){
//        int menuId = -1;
//        MenuEntity menuEntity = FakeFactory.fakeMenu();
//        when(menuJpaRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
//        when(menuJpaRepository.saveAndFlush(menuEntity)).thenReturn(menuEntity);
//
//        SimpleResponse response = mockMenuService.editMenuStatus(menuId);
//        assertEquals(ResultType.FAILURE,response.getResultType());
//
//    }

    @Test
    public void testEditMenuDetailByName() throws BusinessServerException{
        int menuId = 1;
        int adminId = 1;
        String menuName = "newName";
        MenuEntity menuEntity = FakeFactory.fakeMenu();
        menuEntity.setName(menuName);
        List<Integer> ingredientIds = Lists.newArrayList(1,2);
        List<MenuIngredientEntity> menuIngredients = FakeFactory.fakeMenuIngredient(1,ingredientIds);
        List<Ingredient> ingredientEntities = FakeFactory.fakeIngredientsById(ingredientIds);
        request.setName(menuEntity.getName());
        request.setIngredients(ingredientEntities);

        when(menuJpaRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        when(menuJpaRepository.saveAndFlush(menuEntity)).thenReturn(menuEntity);
        when(menuIngredientJpaRepository.saveAndFlush(menuIngredients.get(0))).thenReturn(menuIngredients.get(0));

        SimpleResponse response = mockMenuService.editMenuDetail(request,adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testAddNewMenuWithNoImg() throws BusinessServerException{
        int adminId = 1;
        MenuEntity menuEntity = FakeFactory.fakeMenu();
        CopyUtils.copyProperties(menuEntity,request);
        List<Integer> ingredientIds = Lists.newArrayList(1,2);
        List<MenuIngredientEntity> menuIngredients = FakeFactory.fakeMenuIngredient(1,ingredientIds);
        List<Ingredient> ingredientEntities = FakeFactory.fakeIngredientsById(ingredientIds);
        request.setIngredients(ingredientEntities);

        when(menuJpaRepository.saveAndFlush(menuEntity)).thenReturn(menuEntity);
        when(menuJpaRepository.saveAndFlush(menuEntity)).thenReturn(menuEntity);
        when(menuIngredientJpaRepository.saveAndFlush(menuIngredients.get(0))).thenReturn(menuIngredients.get(0));

        SimpleResponse response = mockMenuService.addNewMenu(request,adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }
}
