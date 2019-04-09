package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.FakeFactory;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.ShopIngredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.BatchImportResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.BuyIngredientRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.bussiness.server.utils.ExcelUtils;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.ShopIngredientEntity;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;
import edu.ecnu.scsse.pizza.data.repository.IngredientJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.ShopIngredientJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class IngredientServiceTest extends TestApplication{
    private Logger logger = LoggerFactory.getLogger(IngredientServiceTest.class);

    @Mock
    private IngredientJpaRepository ingredientJpaRepository;

    @Mock
    private ShopIngredientJpaRepository shopIngredientJpaRepository;

    @Mock
    private PizzaShopJpaRepository shopJpaRepository;

    @Mock
    private OperateLoggerService operateLoggerService;

    @InjectMocks
    private IngredientService ingredientService;

    private IngredientDetailRequest request;

    @Before
    public void setUp(){
        request = new IngredientDetailRequest();
    }

    @Test
    public void testGetIngredientList(){
        List<IngredientEntity> ingredientEntities = FakeFactory.fakeIngredientEntities();
        when(ingredientJpaRepository.findAll()).thenReturn(ingredientEntities);
        List<IngredientDetailResponse> ingredients = ingredientService.getIngredientList();
        assertEquals(ingredientEntities.size(),ingredients.size());
    }

    @Test
    public void testEditIngredientDetailByName() throws BusinessServerException{
        int ingredientId = 1;
        int adminId = 1;
        String ingredientName = "newName";
        IngredientEntity ingredientEntity = FakeFactory.fakeIngredient(ingredientId);
        ingredientEntity.setName(ingredientName);
        CopyUtils.copyProperties(ingredientEntity,request);
        request.setId(ingredientEntity.getId());
        request.setName(ingredientEntity.getName());
        request.setStatus(IngredientStatus.fromDbValue(ingredientEntity.getState()));

        when(ingredientJpaRepository.findById(ingredientId)).thenReturn(Optional.of(ingredientEntity));
        when(ingredientJpaRepository.saveAndFlush(ingredientEntity)).thenReturn(ingredientEntity);

        SimpleResponse response = ingredientService.editIngredientDetail(request, adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testEditIngredientStatusFromOnToOff() throws BusinessServerException{
        int ingredientId = 1;
        int adminId = 1;
        IngredientEntity ingredientEntity = FakeFactory.fakeIngredient(ingredientId);
        ingredientEntity.setState(IngredientStatus.TERMINATED.getDbValue());
        when(ingredientJpaRepository.findById(ingredientId)).thenReturn(Optional.of(ingredientEntity));
        when(ingredientJpaRepository.saveAndFlush(ingredientEntity)).thenReturn(ingredientEntity);
        SimpleResponse response = ingredientService.editIngredientStatus(ingredientId, adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testEditIngredientStatusFromOffToOn() throws BusinessServerException{
        int ingredientId = 1;
        int adminId = 1;
        IngredientEntity ingredientEntity = FakeFactory.fakeIngredient(ingredientId);
        when(ingredientJpaRepository.findById(ingredientId)).thenReturn(Optional.of(ingredientEntity));
        when(ingredientJpaRepository.saveAndFlush(ingredientEntity)).thenReturn(ingredientEntity);
        SimpleResponse response = ingredientService.editIngredientStatus(ingredientId, adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testAddNewIngredient() throws BusinessServerException{
        int adminId = 1;
        IngredientEntity ingredientEntity = FakeFactory.fakeIngredient(2);
        CopyUtils.copyProperties(ingredientEntity,request);
        request.setStatus(IngredientStatus.fromDbValue(ingredientEntity.getState()));
        when(ingredientJpaRepository.saveAndFlush(ingredientEntity)).thenReturn(ingredientEntity);
        SimpleResponse response = ingredientService.addNewIngredient(request,adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testGetAlarmList(){
        List<ShopIngredientEntity> shopIngredientEntityList = FakeFactory.fakeShopIngredients();
        when(shopIngredientJpaRepository.findAll()).thenReturn(shopIngredientEntityList);
        when(ingredientJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeIngredient(1)));
        when(shopJpaRepository.findPizzaShopEntityById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeShop()));
        List<ShopIngredient> shopIngredients = ingredientService.getAlarmList();
        assertEquals(10,shopIngredients.size());
    }

    @Test
    public void testBuyIngredientWhenTotalNumSmallerThanBuyNum(){
        int ingredientId = 1;
        int adminId = 1;
        int shopId = 1;
        int buyNum = 100;
        int totalNum = 50;
        BuyIngredientRequest request = new BuyIngredientRequest(ingredientId,shopId,buyNum);

        when(ingredientJpaRepository.findCountById(ingredientId)).thenReturn(totalNum);
        when(ingredientJpaRepository.updateCountByIngredientId(buyNum+totalNum,ingredientId)).thenReturn(1);
        when(shopIngredientJpaRepository.findByShopIdAndIngredientId(shopId,ingredientId)).thenReturn(Optional.of(FakeFactory.fakeShopIngredient(shopId,ingredientId)));
        when(shopIngredientJpaRepository.updateCountByShopIdAndIngredientId(totalNum,shopId,ingredientId)).thenReturn(1);
        when(ingredientJpaRepository.updateCountByIngredientId(totalNum,ingredientId)).thenReturn(1);
        SimpleResponse response = ingredientService.buyIngredient(request,adminId);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testDeleteIngredient(){
        int ingredientId = 1;
        doNothing().doThrow(new RuntimeException()).when(ingredientJpaRepository).deleteById(ingredientId);
        when(shopIngredientJpaRepository.deleteByIngredientId(ingredientId)).thenReturn(1);

        SimpleResponse response = ingredientService.deleteIngredient(ingredientId);
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testBatchImportByExcelFile(){
        try {
            int adminId = 1;
            File file = new File("src/main/resources/img/1494783328004.jpeg");
            MultipartFile testFile = new MockMultipartFile("1494783328004.jpeg", new FileInputStream(file));
            BatchImportResponse response = ingredientService.batchImportByExcelFile(testFile, adminId);
            assertEquals(ResultType.SUCCESS,response.getResultType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
