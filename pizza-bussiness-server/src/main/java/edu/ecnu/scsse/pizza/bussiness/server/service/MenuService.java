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
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.NewMenuResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CastEntity;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.bean.IngredientBean;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService extends SessionService {
    private static final Logger log = LoggerFactory.getLogger(MenuService.class);

    @Value("${file.uploadUrl}")
    private String UPLOAD_URL;

    @Value("${file.savePath}")
    private String SAVE_PATH;

    @Autowired
    MenuJpaRepository menuJpaRepository;

    @Autowired
    MenuIngredientJpaRepository menuIngredientJpaRepository;

    @Autowired
    IngredientJpaRepository ingredientJpaRepository;

    @Autowired
    OperateLoggerService operateLoggerService;

    public List<MenuDetailResponse> getMenuList() throws Exception{
        List<MenuDetailResponse> menuList = new ArrayList<>();
        List<MenuEntity> menuEntityList = menuJpaRepository.findAllMenuEntities();
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

    public SimpleResponse editMenuStatus(int menuId, int adminId){
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
//                    simpleResponse.setResultType(ResultType.FAILURE);
//                    simpleResponse.setErrorMsg("Status error.");
//                    log.error("Status error.");
                    break;
            }
            operateLoggerService.addOperateLogger(adminId, type, object, OperateResult.SUCCESS.getExpression());
        } else {
            NotFoundException e = new NotFoundException(String.format("menuId %s is not found.", menuId));
            simpleResponse = new SimpleResponse(e);
            log.warn("Menu {} is not found.", menuId, e);
            operateLoggerService.addOperateLogger(adminId, type, object, OperateResult.FAILURE.getExpression() + " :Menu" + menuId + " is not found.");
        }
        return simpleResponse;
    }

    @Transactional
    public SimpleResponse editMenuDetail(MenuDetailRequest request, int adminId) throws BusinessServerException {
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
                operateLoggerService.addOperateLogger(adminId, type, object, OperateResult.SUCCESS.getExpression());
            }
            else{
                NotFoundException e = new NotFoundException(String.format("menuId %s is not found.", menuId));
                response = new SimpleResponse(e);
                log.warn("Menu {} is not found.", menuId, e);
                operateLoggerService.addOperateLogger(adminId, type, object, OperateResult.FAILURE.getExpression() + " :Menu" + menuId + " is not found.");
            }
        }catch (Exception e){
            log.error("Fail to update menu.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to update menu.", e);
        }
        return response;
    }

    public NewMenuResponse addNewMenu(MenuDetailRequest request, int adminId) throws BusinessServerException{
        NewMenuResponse response;
        Menu menu = new Menu(request);
        String type = OperateType.INSERT.getExpression();//操作类型
        String object = OperateObject.MENU.getExpression();//操作对象
        try {
            //获得菜品信息
            response = new NewMenuResponse();
            MenuEntity menuEntity = new MenuEntity();
            CopyUtils.copyProperties(menu,menuEntity);
            menuEntity.setState(menu.getState().getDbValue());
            menuEntity.setTag(menu.getTagName().getDbValue());
            menuJpaRepository.saveAndFlush(menuEntity);
            int menuId = menuEntity.getId();
            response.setMenuId(menuId);
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
            operateLoggerService.addOperateLogger(adminId, type, object+menuId, OperateResult.SUCCESS.getExpression());
        }catch (Exception e){
            log.error("Fail to insert menu.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to insert menu.", e);
        }
        return response;
    }

    public SimpleResponse uploadMenuImageFile(MultipartFile file,int menuId){
        SimpleResponse response = new SimpleResponse();
        if (file==null||file.isEmpty())
            return new SimpleResponse(new NotFoundException("File not found."));
        try {
            Optional<MenuEntity> optional = menuJpaRepository.findById(menuId);
            MenuEntity entity;
            if(optional.isPresent()){
                entity = optional.get();
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                String suffix = file.getOriginalFilename();
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String dateMark = sdf.format(date);
                String fileName = dateMark+suffix;
                File dest = new File(SAVE_PATH+"menu/"+fileName);
                if(!dest.getParentFile().exists())
                    dest.getParentFile().mkdirs();
                file.transferTo(dest);
                //将图片链接保存至数据库
                entity.setImage(UPLOAD_URL+"menu/"+fileName);
                menuJpaRepository.saveAndFlush(entity);
                response.setResultType(ResultType.SUCCESS);
            }
            else{
                return new SimpleResponse(new NotFoundException("Menu not found."));
            }
        } catch (IOException e) {
            String msg = e.getMessage();
            response.setResultType(ResultType.FAILURE);
            response.setErrorMsg(msg);
            e.printStackTrace();
        }
        return response;
    }

    private MenuDetailResponse convert(MenuEntity menuEntity){
        MenuDetailResponse menu = new MenuDetailResponse();
        CopyUtils.copyProperties(menuEntity,menu);
        menu.setId(String.valueOf(menuEntity.getId()));
        menu.setState(PizzaStatus.fromDbValue(menuEntity.getState()).getExpression());
        menu.setTagName(PizzaTag.fromDbValue(menuEntity.getTag()).getExpression());
        menu.setImage(menuEntity.getImage());
        try {
            List<IngredientDetailResponse> ingredientList = new ArrayList<>();
            List<Object[]> objects = ingredientJpaRepository.findIngredientsByMenuId(menuEntity.getId());
            List<IngredientBean> ingredientBeans = new ArrayList<>();
            ingredientBeans = CastEntity.castEntityToIngredientBean(objects,IngredientBean.class);
            ingredientList = ingredientBeans.stream().map(this::convertIngredientBeanToResponse).collect(Collectors.toList());
            menu.setIngredients(ingredientList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menu;
    }

    private IngredientDetailResponse convertIngredientBeanToResponse(IngredientBean ingredientBean){
        IngredientDetailResponse response = new IngredientDetailResponse();
        CopyUtils.copyProperties(ingredientBean,response);
        if(ingredientBean.getState()!=null)
            response.setIngredientStatus(IngredientStatus.fromDbValue(ingredientBean.getState()).getExpression());
        else {
            response.setState(0);
            response.setIngredientStatus("使用中");
        }
        return response;
    }


}
