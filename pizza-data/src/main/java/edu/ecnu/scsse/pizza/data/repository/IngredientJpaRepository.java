package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IngredientJpaRepository extends JpaRepository<IngredientEntity,Integer> {
    @Query("select alermNum from IngredientEntity where id=?1")
    int findAlarmNumById(int id);

    @Query(value = "select count from ingredient where id=?1",nativeQuery = true)
    int findCountById(int id);

    @Transactional
    @Modifying
    @Query(value="update ingredient set ingredient.count=?1 where id=?2",nativeQuery = true)
    int updateCountByIngredientId(int count, int ingredientId);

    @Query(value = "select ingredient.id,ingredient.name,ingredient.supplier_name as supplierName,ingredient.state," +
            "ingredient.alerm_num as alertNum,ingredient.count,menu_ingredient.count as menuNeedCount\n" +
            "from menu_ingredient left join ingredient\n" +
            "on ingredient.id = menu_ingredient.ingredient_id\n" +
            "where menu_ingredient.menu_id = ?1",nativeQuery = true)
    List<Object[]> findIngredientsByMenuId(int menuId);

    @Query(value = "select * from ingredient",nativeQuery = true)
    List<IngredientEntity> findAllIngredients();

    @Query(value = "select ingredient.*\n" +
            "from ingredient\n" +
            "where ingredient.id in\n" +
            "(select shop_ingredient.ingredient_id\n" +
            "from shop_ingredient\n" +
            "where shop_id = ?1\n" +
            ")",nativeQuery = true)
    List<IngredientEntity> findIngredientsByShopId(int shopId);

    @Query(value = "select ingredient.id,ingredient.name,pizza_shop.id as shopId,pizza_shop.`name` as shopName,ingredient.alerm_num as alertNum,shop_ingredient.count\n" +
            "from shop_ingredient,pizza_shop,ingredient\n" +
            "where shop_ingredient.ingredient_id = ingredient.id \n" +
            "and shop_ingredient.shop_id = pizza_shop.id\n" +
            "and shop_ingredient.count<ingredient.alerm_num",nativeQuery = true)
    List<Object[]> findAlarmList();
}