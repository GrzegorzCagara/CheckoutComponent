package com.moler.technicaltask.controller;

import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.service.ItemService;
import com.moler.technicaltask.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/items")
public class ItemRestController {


    ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ItemRestController.class);

    @GetMapping("")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items = itemService.findAllItems();
        if (items.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @GetMapping("/item/{name}")
    public ResponseEntity<?> findItemByName(@PathVariable("name") String name){
        logger.info("Fetching Item with name {}", name);

        Optional<Item> optionalItem = itemService.findItemByName(name);
        if (optionalItem.isPresent()){
            Item item = optionalItem.get();
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        } else {
            logger.error("Item with name {} not found.", name);
            return notFoundItem("Item with name " + name + " not found");
        }
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> findItemById(@PathVariable("id") long id){
        logger.info("Fetching Item with id {}", id);

        Optional<Item> optionalItem = itemService.findItemById(id);
        if (optionalItem.isPresent()){
            Item item = optionalItem.get();
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        } else {
            logger.error("Item with id {} not found.", id);
            return notFoundItem("Item with id " + id + " not found");
        }
    }



    @PostMapping("/add/{id}/{quantity}/")
    public ResponseEntity<String> addNewItem(@PathVariable("id") long itemId,
                                             @PathVariable("quantity") int quantity){
        Optional<Item> optionalItem = itemService.findItemById(itemId);
        if (optionalItem.isPresent()){
            Item item = optionalItem.get();
            logger.info("Inserting Item : {}, with quantity {}", item, quantity);
            itemService.addNewItem(item);
            return new ResponseEntity<>("New item has been added", HttpStatus.OK);
        } else{
            logger.error("Item with id {} not found.", itemId);
            return notFoundItem("Item with id " + itemId + " not found");
        }

    }

    private ResponseEntity<String> notFoundItem(String errorMessage){
        return new ResponseEntity(new CustomErrorType(errorMessage), HttpStatus.NOT_FOUND);
    }


}
