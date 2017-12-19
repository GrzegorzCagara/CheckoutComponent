package com.moler.technicaltask.repository;

import com.moler.technicaltask.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item findByName(String name);

}
