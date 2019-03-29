package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.ShopIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ShopIngredientJpaRepository extends JpaRepository<ShopIngredientEntity,Integer> {

    @Query(value = "select * from shop_ingredient where shop_id = ?1 for update",nativeQuery = true)
    List<ShopIngredientEntity> findByShopIdForUpdate(int shopId);

    @Transactional
    @Modifying
    @Query(value="update shop_ingredient set count=?1 where shop_id=?2 and ingredient_id=?3",nativeQuery = true)
    int updateCountByShopIdAndIngredientId(int count,int shopId,int ingredientId);

    List<ShopIngredientEntity> findByShopId(int shopId);

    @Query(value="select * from shop_ingredient where shop_id=?1 and ingredient_id=?2",nativeQuery = true)
    Optional<ShopIngredientEntity> findByShopIdAndIngredientId(int shopId, int ingredientId);
}