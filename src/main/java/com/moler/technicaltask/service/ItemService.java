package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {


    ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void addNewItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    @Transactional
    public Item findByName(String name) {
        return itemRepository.findByName(name);
    }
}
