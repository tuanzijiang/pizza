package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.bussiness.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.MenuEntity;
import edu.ecnu.scsse.pizza.data.domain.MenuIngredientEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    MenuJpaRepository menuJpaRepository;

    @Autowired
    MenuIngredientJpaRepository menuIngredientJpaRepository;

    @Autowired
    IngredientJpaRepository ingredientJpaRepository;

    public MenuManageResponse getMenuList(){
        MenuManageResponse menuManageResponse;
        List<MenuEntity> menuEntityList = menuJpaRepository.findAll();
        if(menuEntityList.size()!=0){
            menuManageResponse = new MenuManageResponse();
            List<Menu> menuList = menuEntityList.stream().map(this::convert).collect(Collectors.toList());
            menuManageResponse.setMenuList(menuList);
        }
        else{
            NotFoundException e = new NotFoundException("Menu list is not found.");
            menuManageResponse = new MenuManageResponse(e);
            log.warn("Fail to find the menu list.", e);
        }

        return menuManageResponse;
    }

    public MenuDetailResponse editMenuStatus(int menuId){
        MenuDetailResponse simpleResponse;
        Optional<MenuEntity> menuEntityOptional = menuJpaRepository.findById(menuId);
        if(menuEntityOptional.isPresent()){
            simpleResponse = new MenuDetailResponse();
            MenuEntity menuEntity = menuEntityOptional.get();
            int currentState = menuEntity.getState();
            switch (PizzaStatus.fromDbValue(currentState)){
                case IN_SALE:
                    menuEntity.setState(PizzaStatus.OFF_SHELF.getDbValue());
                    menuJpaRepository.saveAndFlush(menuEntity);
                    //menuJpaRepository.updateStatusById(menuId,PizzaStatus.OFF_SHELF.getDbValue());

                    break;
                case OFF_SHELF:
                    menuEntity.setState(PizzaStatus.IN_SALE.getDbValue());
                    menuJpaRepository.saveAndFlush(menuEntity);
                    //menuJpaRepository.updateStatusById(menuId,PizzaStatus.IN_SALE.getDbValue());
                    break;
                default:
                    simpleResponse.setResultType(ResultType.FAILURE);
                    simpleResponse.setErrorMsg("Status error.");
                    log.error("Status error.");
                    break;
            }
        }
        else{
            NotFoundException e = new NotFoundException(String.format("menuId %s is not found.", menuId));
            simpleResponse = new MenuDetailResponse(e);
            log.warn("Menu {} is not found.", menuId, e);
        }
        return simpleResponse;
    }

    @Transactional
    public MenuDetailResponse editMenuDetail(MenuDetailRequest request) throws BusinessServerException {
        MenuDetailResponse response;
        Menu menu = new Menu(request);
        int menuId = Integer.parseInt(request.getId());
        try {
            //更新菜品信息
            Optional<MenuEntity> menuEntityOptional = menuJpaRepository.findById(menuId);
            if(menuEntityOptional.isPresent()){
                response = new MenuDetailResponse();
                MenuEntity menuEntity = menuEntityOptional.get();
                CopyUtils.copyProperties(menu,menuEntity);
                menuEntity.setState(menu.getState().getDbValue());
                menuEntity.setTag(menu.getTagName().getDbValue());
                menuJpaRepository.saveAndFlush(menuEntity);
                //menuJpaRepository.updateByMenuEntity(menuEntity);
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
            }
            else{
                NotFoundException e = new NotFoundException(String.format("menuId %s is not found.", menuId));
                response = new MenuDetailResponse(e);
                log.warn("Menu {} is not found.", menuId, e);
            }
        }catch (Exception e){
            log.error("Fail to update menu.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to update menu.", e);
        }
        return response;
    }

    private Menu convert(MenuEntity menuEntity){
        Menu menu = new Menu();
        CopyUtils.copyProperties(menuEntity,menu);
        menu.setId(String.valueOf(menuEntity.getId()));
        menu.setState(PizzaStatus.fromDbValue(menuEntity.getState()));
        menu.setTagName(PizzaTag.fromDbValue(menuEntity.getTag()));
        List<Ingredient> ingredientList = new ArrayList<>();
        List<IngredientEntity> allIngredientEntityList = ingredientJpaRepository.findAll();
        for(IngredientEntity ingredientEntity:allIngredientEntityList){
            Ingredient ingredient = new Ingredient(ingredientEntity);
            int ingredientId = ingredientEntity.getId();
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
