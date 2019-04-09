package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateObject;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateResult;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.repository.IngredientJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.ShopIngredientJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService{
    private static final Logger log = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    PizzaShopJpaRepository shopJpaRepository;

    @Autowired
    ShopIngredientJpaRepository shopIngredientJpaRepository;

    @Autowired
    IngredientJpaRepository ingredientJpaRepository;

    @Autowired
    OperateLoggerService operateLoggerService;

    public List<ShopManageResponse> getShopList() throws Exception{
        List<ShopManageResponse> shopList = new ArrayList<>();
        List<PizzaShopEntity> shopEntityList = shopJpaRepository.findAllShops();
        if(shopEntityList.size()!=0){
            shopList = shopEntityList.stream().map(this::convert).collect(Collectors.toList());
        }
        else{
            NotFoundException e = new NotFoundException("Shop list is not found.");
            log.warn("Fail to find the shop list.", e);
        }

        return shopList;
    }

    public List<IngredientEntity> getIngredientListByShopId(int shopId){
        return ingredientJpaRepository.findIngredientsByShopId(shopId);
    }

    @Transactional
    public ShopDetailResponse editShopDetail(ShopDetailRequest request, int adminId) throws ParseException,BusinessServerException{
        PizzaShopEntity shopEntity;
        ShopDetailResponse response;
        int shopId = Integer.parseInt(request.getId());
        String type = OperateType.UPDATE.getExpression();//操作类型
        String object = OperateObject.SHOP.getExpression()+shopId;//操作对象
        Optional<PizzaShopEntity> optional = shopJpaRepository.findById(shopId);
        try {
            if (optional.isPresent()) {
                shopEntity = optional.get();
                CopyUtils.copyProperties(request, shopEntity);
                Timestamp startTime = Timestamp.valueOf(request.getStartTime());
                Timestamp endTime = Timestamp.valueOf(request.getEndTime());
                shopEntity.setStartTime(new Timestamp(startTime.getTime()));
                shopEntity.setEndTime(new Timestamp(endTime.getTime()));
                shopJpaRepository.saveAndFlush(shopEntity);
                response = new ShopDetailResponse();
                response.setShopId(shopId);
                operateLoggerService.addOperateLogger(adminId, type, object, OperateResult.SUCCESS.getExpression());
            } else {
                NotFoundException e = new NotFoundException(String.format("shopId %s is not found.", shopId));
                response = new ShopDetailResponse(e);
                log.warn("Shop {} is not found.", shopId, e);
                operateLoggerService.addOperateLogger(adminId, type, object, OperateResult.FAILURE.getExpression() + " :Shop" + shopId + " is not found.");
            }
        }catch (Exception e){
            log.error("Fail to update shop.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to update shop.", e);
        }
        return response;

    }

    public ShopDetailResponse addNewShop(ShopDetailRequest request, int adminId) throws BusinessServerException{
        PizzaShopEntity shopEntity;
        ShopDetailResponse response;
        String type = OperateType.INSERT.getExpression();//操作类型
        String object = OperateObject.SHOP.getExpression();//操作对象
        try {
            shopEntity = new PizzaShopEntity();
            CopyUtils.copyProperties(request, shopEntity);
            Timestamp startTime = Timestamp.valueOf(request.getStartTime());
            Timestamp endTime = Timestamp.valueOf(request.getEndTime());
            shopEntity.setStartTime(new Timestamp(startTime.getTime()));
            shopEntity.setEndTime(new Timestamp(endTime.getTime()));
            shopEntity.setLat(new BigDecimal(request.getLat()));
            shopEntity.setLon(new BigDecimal(request.getLon()));
            shopJpaRepository.saveAndFlush(shopEntity);
            response = new ShopDetailResponse();
            operateLoggerService.addOperateLogger(adminId, type, object, OperateResult.SUCCESS.getExpression());
        }catch (Exception e){
            log.error("Fail to insert shop.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to insert shop.", e);
        }
        return response;
    }

    private ShopManageResponse convert(PizzaShopEntity entity){
        ShopManageResponse shop = new ShopManageResponse();
        CopyUtils.copyProperties(entity,shop);
        shop.setLat(entity.getLat().doubleValue());
        shop.setLon(entity.getLon().doubleValue());
        String commitTimePattern = "yyyy/MM/dd hh:MM:ss";
        DateFormat df = new SimpleDateFormat(commitTimePattern);
        String open = df.format(entity.getStartTime()).split(" ")[1];
        String close = df.format(entity.getEndTime()).split(" ")[1];
        shop.setOpenHours(open+"-"+close);
        return shop;
    }
}
