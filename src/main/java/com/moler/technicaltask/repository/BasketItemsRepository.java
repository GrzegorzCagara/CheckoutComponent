package com.moler.technicaltask.repository;

import com.moler.technicaltask.entity.BasketAndItem;
import com.moler.technicaltask.entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BasketItemsRepository extends CrudRepository<BasketAndItem, Long>{

    List<BasketAndItem> findAllByBasket_Id(Long id);

    @Modifying
    @Query("UPDATE BasketAndItem c SET c.quantity = :quantity WHERE c.id = :basketAndItemId")
    void update(@Param("basketAndItemId") Long basketAndItemId, @Param("quantity") Integer quantity);
}
