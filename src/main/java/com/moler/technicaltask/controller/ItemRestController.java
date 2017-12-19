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

    @GetMapping("/{name}")
    public ResponseEntity<?> findItemByName(@PathVariable("name") String name){
        logger.info("Fetching Item with name {}", name);
        Item item = itemService.findByName(name);
        if (item == null){
            logger.error("Item with name {} not found.", name);
            return new ResponseEntity(new CustomErrorType("Item with name " + name + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }



//    @GetMapping("/test")
//    public void add(){
//        itemService.addNewItem(item);
//        itemService.addNewItem(item2);
//    }

    @PostMapping("/item")
    public ResponseEntity<String> addItem(@RequestBody Item item){
        logger.info("Creating Item : {}", item);
        itemService.addNewItem(item);
        return new ResponseEntity<>("New item has been added", HttpStatus.CREATED);
    }


}
