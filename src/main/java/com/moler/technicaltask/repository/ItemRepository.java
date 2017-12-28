package com.moler.technicaltask.repository;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findItemById(long id);
    List<Item> findAll();

}
