package com.moler.technicaltask.repository;

import com.moler.technicaltask.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findItemByName(String name);
    Optional<Item> findItemById(long id);

}
