package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Item;

import java.util.List;

public interface ItemService {
    void addNewItem(Item item);
    List<Item> findAll();
    Item findItemById(long id);
}
