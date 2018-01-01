package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Item;

import java.util.List;

public interface ItemService {
    Item save(Item item);
    List<Item> findAll();
    Item findItemById(long id);
}
