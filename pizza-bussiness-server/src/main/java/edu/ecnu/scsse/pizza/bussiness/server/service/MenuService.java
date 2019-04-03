package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateObject;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateResult;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.MenuEntity;
import edu.ecnu.scsse.pizza.data.domain.MenuIngredientEntity;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaTag;
import edu.ecnu.scsse.pizza.data.repository.IngredientJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.MenuIngredientJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.MenuJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService extends SessionService {
    private static final Logger log = LoggerFactory.getLogger(MenuService.class);

    //private static String UPLOADED_FOLDER = "./resources/img/";

    @Autowired
    MenuJpaRepository menuJpaRepository;

    @Autowired
    MenuIngredientJpaRepository menuIngredientJpaRepository;

    @Autowired
    IngredientJpaRepository ingredientJpaRepository;

    @Autowired
    OperateLoggerService operateLoggerService;

    public List<MenuDetailResponse> getMenuList(){
        List<MenuDetailResponse> menuList = new ArrayList<>();
        List<MenuEntity> menuEntityList = menuJpaRepository.findAll();
        if(menuEntityList.size()!=0){
            menuList = menuEntityList.stream().map(this::convert).collect(Collectors.toList());
        }
        else{
            NotFoundException e = new NotFoundException("Menu list is not found.");
            log.warn("Fail to find the menu list.", e);
        }

        return menuList;
    }

    public List<String> getTagList(){
        List<String> tagNames = new ArrayList<>();
        for(PizzaTag tag:PizzaTag.values()){
            tagNames.add(tag.getExpression());
        }
        return tagNames;
    }

    public SimpleResponse editMenuStatus(int menuId){
        SimpleResponse simpleResponse;
        Optional<MenuEntity> menuEntityOptional = menuJpaRepository.findById(menuId);
        String type = OperateType.UPDATE.getExpression();//操作类型
        String object = OperateObject.MENU.getExpression()+menuId;//操作对象
        if (menuEntityOptional.isPresent()) {
            simpleResponse = new SimpleResponse();
            MenuEntity menuEntity = menuEntityOptional.get();
            int currentState = menuEntity.getState();
            switch (PizzaStatus.fromDbValue(currentState)) {
                case IN_SALE:
                    menuEntity.setState(PizzaStatus.OFF_SHELF.getDbValue());
                    menuJpaRepository.saveAndFlush(menuEntity);
                    break;
                case OFF_SHELF:
                    menuEntity.setState(PizzaStatus.IN_SALE.getDbValue());
                    menuJpaRepository.saveAndFlush(menuEntity);
                    break;
                default:
                    simpleResponse.setResultType(ResultType.FAILURE);
                    simpleResponse.setErrorMsg("Status error.");
                    log.error("Status error.");
                    break;
            }
            operateLoggerService.addOperateLogger(type, object, OperateResult.SUCCESS.getExpression());
        } else {
            NotFoundException e = new NotFoundException(String.format("menuId %s is not found.", menuId));
            simpleResponse = new SimpleResponse(e);
            log.warn("Menu {} is not found.", menuId, e);
            operateLoggerService.addOperateLogger(type, object, OperateResult.FAILURE.getExpression() + " :Menu" + menuId + " is not found.");
        }
        return simpleResponse;
    }

    @Transactional
    public SimpleResponse editMenuDetail(MenuDetailRequest request) throws BusinessServerException {
        SimpleResponse response;
        Menu menu = new Menu(request);
        int menuId = Integer.parseInt(request.getId());
        String type = OperateType.UPDATE.getExpression();//操作类型
        String object = OperateObject.MENU.getExpression()+menuId;//操作对象
        try {
            //更新菜品信息
            Optional<MenuEntity> menuEntityOptional = menuJpaRepository.findById(menuId);
            if(menuEntityOptional.isPresent()){
                response = new SimpleResponse();
                MenuEntity menuEntity = menuEntityOptional.get();
                CopyUtils.copyProperties(menu,menuEntity);
                menuEntity.setState(menu.getState().getDbValue());
                menuEntity.setTag(menu.getTagName().getDbValue());
                menuJpaRepository.saveAndFlush(menuEntity);
                //更新菜品原料信息
                List<Ingredient> ingredientList = request.getIngredients();//披萨的原料列表
                for(Ingredient ingredient:ingredientList) {
                    int ingredientId = ingredient.getId();
                    int count = ingredient.getMenuNeedCount();//修改后的所需原料数
                    Optional<MenuIngredientEntity> optional = menuIngredientJpaRepository.findByMenuIdAndIngredientId(menuId, ingredientId);
                    if(optional.isPresent()){
                        MenuIngredientEntity menuIngredientEntity = optional.get();
                        if(count!=0) {// 修改原料数量
                            menuIngredientEntity.setCount(count);
                            menuIngredientJpaRepository.saveAndFlush(menuIngredientEntity);
                        }
                        else{//删除原有原料
                            menuIngredientJpaRepository.deleteByMenuIdAndIngredientId(menuId, ingredientId);
                        }
                    }
                    else{
                        //新增披萨所需原料种类并设置数量
                        MenuIngredientEntity entity = new MenuIngredientEntity();
                        entity.setMenuId(menuId);
                        entity.setIngredientId(ingredientId);
                        entity.setCount(count);
                        menuIngredientJpaRepository.saveAndFlush(entity);
                    }
                }
                operateLoggerService.addOperateLogger(type, object, OperateResult.SUCCESS.getExpression());
            }
            else{
                NotFoundException e = new NotFoundException(String.format("menuId %s is not found.", menuId));
                response = new SimpleResponse(e);
                log.warn("Menu {} is not found.", menuId, e);
                operateLoggerService.addOperateLogger(type, object, OperateResult.FAILURE.getExpression() + " :Menu" + menuId + " is not found.");
            }
        }catch (Exception e){
            log.error("Fail to update menu.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to update menu.", e);
        }
        return response;
    }

    public SimpleResponse addNewMenu(MenuDetailRequest request) throws BusinessServerException{
        SimpleResponse response;
        Menu menu = new Menu(request);
        String type = OperateType.INSERT.getExpression();//操作类型
        String object = OperateObject.MENU.getExpression();//操作对象
        try {
            //获得菜品信息
            response = new SimpleResponse();
            MenuEntity menuEntity = new MenuEntity();
            CopyUtils.copyProperties(menu,menuEntity);
            menuEntity.setState(menu.getState().getDbValue());
            menuEntity.setTag(menu.getTagName().getDbValue());

            //获得菜品图片信息
            MultipartFile imageFile = (MultipartFile)request.getImage();
            String msg = uploadMenuImageFile(imageFile,menuEntity);
            if(!msg.contains("Success")) {
                response.setResultType(ResultType.FAILURE);
                response.setErrorMsg(msg);
                return response;
            }
            menuJpaRepository.saveAndFlush(menuEntity);
            int menuId = menuEntity.getId();
            //获得菜品原料信息
            List<Ingredient> ingredientList = request.getIngredients();//披萨的原料列表
            for(Ingredient ingredient:ingredientList) {
                int ingredientId = ingredient.getId();
                int count = ingredient.getMenuNeedCount();//修改后的所需原料数
                Optional<MenuIngredientEntity> optional = menuIngredientJpaRepository.findByMenuIdAndIngredientId(menuId, ingredientId);
                if(optional.isPresent()){
                    MenuIngredientEntity menuIngredientEntity = optional.get();
                    if(count!=0) {// 修改原料数量
                        menuIngredientEntity.setCount(count);
                        menuIngredientJpaRepository.saveAndFlush(menuIngredientEntity);
                    }
                    else{//删除原有原料
                        menuIngredientJpaRepository.deleteByMenuIdAndIngredientId(menuId, ingredientId);
                    }
                }
                else{
                    //新增披萨所需原料种类并设置数量
                    MenuIngredientEntity entity = new MenuIngredientEntity();
                    entity.setMenuId(menuId);
                    entity.setIngredientId(ingredientId);
                    entity.setCount(count);
                    menuIngredientJpaRepository.saveAndFlush(entity);
                }
            }
            operateLoggerService.addOperateLogger(type, object+menuId, OperateResult.SUCCESS.getExpression());
        }catch (Exception e){
            log.error("Fail to insert menu.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to insert menu.", e);
        }
        return response;
    }

    public String uploadImage(MultipartFile file){
        String msg;
        if (file.isEmpty())
            msg = "Failed: empty file.";
        else{
            try {
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path currentDir = Paths.get(".");
                String p = currentDir.toAbsolutePath().toString()+"\\pizza-bussiness-server\\src\\main\\resources\\img\\";
                Path path = Paths.get(p + file.getOriginalFilename());
                Files.write(path, bytes);
                //entity.setImage(path.toString());
                msg = "Success: upload file successfully.";

            } catch (IOException e) {
                msg = e.getMessage();
                e.printStackTrace();
            }
        }
        return msg;
    }

    private String uploadMenuImageFile(MultipartFile file, MenuEntity entity){
        String msg;
        if (file.isEmpty()) {
            msg = "Failed: empty file.";
        }
        else{
            try {
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path currentDir = Paths.get(".");
                String p = currentDir.toAbsolutePath().toString()+"\\pizza-bussiness-server\\src\\main\\resources\\img\\";
                Path path = Paths.get(p + file.getOriginalFilename());
                Files.write(path, bytes);
                //将图片名称保存至数据库
                entity.setImage(file.getOriginalFilename());
                msg = "Success: upload file successfully.";

            } catch (IOException e) {
                msg = e.getMessage();
                e.printStackTrace();
            }
        }
        return msg;
    }

    private MenuDetailResponse convert(MenuEntity menuEntity){
        MenuDetailResponse menu = new MenuDetailResponse();
        CopyUtils.copyProperties(menuEntity,menu);
        menu.setId(String.valueOf(menuEntity.getId()));
        menu.setState(PizzaStatus.fromDbValue(menuEntity.getState()).getExpression());
        menu.setTagName(PizzaTag.fromDbValue(menuEntity.getTag()).getExpression());
        List<IngredientDetailResponse> ingredientList = new ArrayList<>();
        List<IngredientEntity> allIngredientEntityList = ingredientJpaRepository.findAll();
        for(IngredientEntity ingredientEntity:allIngredientEntityList){
            IngredientDetailResponse ingredient = new IngredientDetailResponse(ingredientEntity);
            int ingredientId = ingredientEntity.getId();
            ingredient.setIngredientStatus(IngredientStatus.fromDbValue(ingredientEntity.getState()).getExpression());
            Optional<MenuIngredientEntity> menuIngredientEntityOptional = menuIngredientJpaRepository.findByMenuIdAndIngredientId(menuEntity.getId(),ingredientId);
            if(menuIngredientEntityOptional.isPresent()){
                MenuIngredientEntity menuIngredientEntity = menuIngredientEntityOptional.get();
                ingredient.setMenuNeedCount(menuIngredientEntity.getCount());
            }
            ingredientList.add(ingredient);
        }
        menu.setIngredients(ingredientList);
        return menu;
    }
}
