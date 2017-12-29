package com.moler.technicaltask.service;

import com.moler.technicaltask.controller.ItemRestController;
import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.repository.ItemRepository;
import com.moler.technicaltask.exception.ItemNotFoundRunTimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;


    @Transactional
    public void save(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    @Transactional
    public Item findItemById(long id){
        return returnItemIfNotNull(id);
    }

    private Item returnItemIfNotNull(Long itemId){
        log.info("Fetching Item with id {}", itemId);
        Optional<Item> optionalItem = itemRepository.findItemById(itemId);

        if (optionalItem.isPresent()) {
            return optionalItem.get();
        } else {
            log.error("Item with id {} not found.", itemId);
            throw new ItemNotFoundRunTimeException(String.format("Basket with id: %d not found.", itemId));
        }
    }
}
