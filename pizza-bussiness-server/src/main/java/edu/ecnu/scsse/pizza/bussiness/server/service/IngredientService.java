package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.ShopIngredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateObject;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateResult;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.BatchImportResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.bussiness.server.utils.ExcelUtils;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.domain.ShopIngredientEntity;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;
import edu.ecnu.scsse.pizza.data.repository.IngredientJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.ShopIngredientJpaRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    final int DEFAULT_ALARM_NUM = 200;

    private Logger log = LoggerFactory.getLogger(IngredientService.class);
    @Autowired
    IngredientJpaRepository ingredientJpaRepository;

    @Autowired
    ShopIngredientJpaRepository shopIngredientJpaRepository;

    @Autowired
    PizzaShopJpaRepository shopJpaRepository;

    @Autowired
    OperateLoggerService operateLoggerService;

    public List<Ingredient> getIngredientList(){
//        IngredientManageResponse response;
        List<Ingredient> ingredientList = new ArrayList<>();
        List<IngredientEntity> ingredientEntityList = ingredientJpaRepository.findAll();
        if(ingredientEntityList.size()!=0){
//            response = new IngredientManageResponse();
            ingredientList = ingredientEntityList.stream().map(this::convert).collect(Collectors.toList());
//            response.setIngredientList(ingredientList);
        }
        else{
            NotFoundException e = new NotFoundException("Ingredient list is not found.");
//            response = new IngredientManageResponse(e);
            log.warn("Fail to find the ingredient list.", e);
        }

        return ingredientList;
    }

    public IngredientDetailResponse editIngredientDetail(IngredientDetailRequest request) throws BusinessServerException {
        IngredientEntity entity;
        IngredientDetailResponse response;
        int ingredientId = request.getId();
        String type = OperateType.UPDATE.getExpression();//操作类型
        String object = OperateObject.INGREDIENT.getExpression()+ingredientId;//操作对象
        Optional<IngredientEntity> optional = ingredientJpaRepository.findById(ingredientId);
        try {
            if (optional.isPresent()) {
                entity = optional.get();
                CopyUtils.copyProperties(request, entity);
                entity.setState(request.getStatus().getDbValue());
                ingredientJpaRepository.saveAndFlush(entity);
                response = new IngredientDetailResponse();
                operateLoggerService.addOperateLogger(type, object, OperateResult.SUCCESS.getExpression());
            } else {
                String msg = String.format("Ingredient %s is not found.", ingredientId);
                NotFoundException e = new NotFoundException(msg);
                response = new IngredientDetailResponse(e);
                log.warn(msg, e);
                operateLoggerService.addOperateLogger(type, object, OperateResult.FAILURE.getExpression() + " :"+msg);
            }
        }catch (Exception e){
            log.error("Fail to update shop.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to update shop.", e);
        }
        return response;

    }

    public IngredientDetailResponse editIngredientStatus(int id){
        IngredientDetailResponse response;
        Optional<IngredientEntity> optional = ingredientJpaRepository.findById(id);
        String type = OperateType.UPDATE.getExpression();//操作类型
        String object = OperateObject.INGREDIENT.getExpression()+id;//操作对象
        if (optional.isPresent()) {
            response = new IngredientDetailResponse();
            IngredientEntity entity = optional.get();
            int currentState = entity.getState();
            switch (IngredientStatus.fromDbValue(currentState)) {
                case USING:
                    entity.setState(IngredientStatus.TERMINATED.getDbValue());
                    ingredientJpaRepository.saveAndFlush(entity);
                    break;
                case TERMINATED:
                    entity.setState(IngredientStatus.USING.getDbValue());
                    ingredientJpaRepository.saveAndFlush(entity);
                    break;
                default:
                    response.setResultType(ResultType.FAILURE);
                    response.setErrorMsg("Status error.");
                    log.error("Status error.");
                    break;
            }
            operateLoggerService.addOperateLogger(type, object, OperateResult.SUCCESS.getExpression());
        } else {
            String msg = String.format("Ingredient %s is not found.", id);
            NotFoundException e = new NotFoundException(msg);
            response = new IngredientDetailResponse(e);
            log.warn(msg, e);
            operateLoggerService.addOperateLogger(type, object, OperateResult.FAILURE.getExpression() + " :"+msg);
        }
        return response;
    }

    public IngredientDetailResponse addNewIngredient(IngredientDetailRequest request) throws BusinessServerException{
        IngredientEntity entity;
        IngredientDetailResponse response;
        String type = OperateType.INSERT.getExpression();//操作类型
        String object = OperateObject.INGREDIENT.getExpression();//操作对象
        try {
            entity = new IngredientEntity();
            CopyUtils.copyProperties(request, entity);
            entity.setState(request.getStatus().getDbValue());
            ingredientJpaRepository.saveAndFlush(entity);
            response = new IngredientDetailResponse();
            operateLoggerService.addOperateLogger(type, object, OperateResult.SUCCESS.getExpression());
        }catch (Exception e){
            log.error("Fail to insert ingredient.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to insert ingredient.", e);
        }
        return response;
    }

    public BatchImportResponse batchImportByExcelFile(String path){
        String msg = "";
        BatchImportResponse response = new BatchImportResponse();
        response.setResultType(ResultType.FAILURE);
        Sheet sheet = null;
        if(path.endsWith("xls"))
            sheet = (Sheet) ExcelUtils.importXls(path).get("sheet");
        else if(path.endsWith("xlsx"))
            sheet = (Sheet) ExcelUtils.importXlsx(path).get("sheet");
        if(sheet!=null) {
            msg = getIngredientRowAndCell(sheet);
            if(msg.contains("successfully")) {
                String type = OperateType.INSERT.getExpression();//操作类型
                String object = OperateObject.INGREDIENT.getExpression()+"（通过Excel文件）";//操作对象
                response.setResultType(ResultType.SUCCESS);
                response.setSuccessMsg(msg);
                operateLoggerService.addOperateLogger(type,object,OperateResult.SUCCESS.getExpression());
            }
            else
                response.setErrorMsg(msg);
        }
        return response;
    }

    private String getIngredientRowAndCell(Sheet sheet){
        List<IngredientEntity> ingredientList = new ArrayList<>();
        String msg;
        int totalRows = sheet.getLastRowNum();
        for(int i=1;i<=totalRows;i++){
            Row row = sheet.getRow(i);
            IngredientEntity ingredient = new IngredientEntity();
            ingredient.setState(0);
            int lastCellIndex = row.getLastCellNum();
            for(int j=0;j<lastCellIndex;j++){
                try{
                    Cell cell = row.getCell(j);
                    switch (j){
                        case 0:
                            if(cell!=null)
                                ingredient.setName(cell.getStringCellValue());
                            else {
                                msg = "Name can not be empty.";
                                log.error(msg);
                                return msg;
                            }
                            break;
                        case 1:
                            if(cell!=null)
                                ingredient.setSupplierName(cell.getStringCellValue());
                            else
                                ingredient.setSupplierName("");
                            break;
                        case 2:
                            if(cell!=null)
                                ingredient.setAlermNum((int)cell.getNumericCellValue());
                            else
                                ingredient.setAlermNum(0);
                            break;
                        case 3:
                            if(cell!=null)
                                ingredient.setCount((int)cell.getNumericCellValue());
                            else
                                ingredient.setCount(0);
                            break;
                        default:
                            break;

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    msg = String.format("Fail to import at Row %s , Column %s",i,(j+1));
                    log.error(msg);
                    return msg;
                }
            }
            ingredientList.add(ingredient);
        }
        ingredientJpaRepository.saveAll(ingredientList);
        msg = String.format("%d items import successfully.",ingredientList.size());
        return msg;
    }

    public List<ShopIngredient> getAlarmList(){
        List<ShopIngredient> alarmList = new ArrayList<>();
        List<ShopIngredientEntity> shopIngredientEntityList = shopIngredientJpaRepository.findAll();
        if(shopIngredientEntityList.size()!=0){
            for(ShopIngredientEntity entity:shopIngredientEntityList){
                int count = entity.getCount(); //现有库存
                Optional<IngredientEntity> optional = ingredientJpaRepository.findById(entity.getIngredientId());
                if(optional.isPresent()){
                    IngredientEntity ingredientEntity = optional.get();
                    int alarmNum = ingredientEntity.getAlermNum()==null?DEFAULT_ALARM_NUM:ingredientEntity.getAlermNum();
                    if(count<=alarmNum) {
                        ShopIngredient ingredient = new ShopIngredient();
                        ingredient.setId(ingredientEntity.getId());
                        ingredient.setName(ingredientEntity.getName());
                        ingredient.setAlermNum(ingredientEntity.getAlermNum());
                        ingredient.setCount(entity.getCount());
                        Optional<PizzaShopEntity> shopOptional = shopJpaRepository.findPizzaShopEntityById(entity.getShopId());
                        if(shopOptional.isPresent()){
                            PizzaShopEntity pizzaShopEntity = shopOptional.get();
                            ingredient.setShopId(pizzaShopEntity.getId());
                            String shopName = pizzaShopEntity.getName();
                            ingredient.setShopName(shopName);
                        }
                        else {
                            ingredient.setShopId(0);
                            ingredient.setShopName("");
                        }
                        alarmList.add(ingredient);
                    }
                }
            }
        }
        return alarmList;
    }

    public BaseResponse buyIngredient(int shopId,int ingredientId){
        IngredientDetailResponse response = new IngredientDetailResponse();
        return response;
    }

    private Ingredient convert(IngredientEntity entity){
        Ingredient ingredient = new Ingredient();
        CopyUtils.copyProperties(entity,ingredient);
        ingredient.setIngredientStatus(IngredientStatus.fromDbValue(entity.getState()));
        return ingredient;
    }

}
