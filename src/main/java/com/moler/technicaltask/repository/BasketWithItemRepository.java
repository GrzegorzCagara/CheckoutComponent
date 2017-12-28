package com.moler.technicaltask.repository;

import com.moler.technicaltask.entity.BasketWithItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BasketWithItemRepository extends CrudRepository<BasketWithItem, Long>{

    List<BasketWithItem> findAllByBasket_Id(Long id);

    @Modifying
    @Query("UPDATE BasketWithItem c SET c.quantity = :quantity WHERE c.id = :basketAndItemId")
    void update(@Param("basketAndItemId") Long basketAndItemId, @Param("quantity") Integer quantity);
}
