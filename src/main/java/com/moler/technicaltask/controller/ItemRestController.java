package com.moler.technicaltask.controller;

import com.moler.technicaltask.entity.BasketWithItem;
import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.service.BasketService;
import com.moler.technicaltask.service.BasketServiceImpl;
import com.moler.technicaltask.service.ItemService;
import com.moler.technicaltask.service.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemRestController {

    private final ItemService itemService;
    private final BasketService basketService;

    @GetMapping("")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items = itemService.findAll();

        if (items == null || items.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


    @GetMapping("/{itemId}")
    public ResponseEntity<Item> findItemById(@PathVariable("itemId") Long id){
        Item item = itemService.findItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/add/{itemId}/{quantity}/{basketId}")
    public ResponseEntity<BasketWithItem> addItemToBasket(@PathVariable("itemId") Long itemId,
                                                  @PathVariable("quantity") Integer quantity,
                                                  @PathVariable("basketId") Long basketId){
        BasketWithItem result = basketService.addItemToBasket(itemId, quantity, basketId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
