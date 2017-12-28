package com.moler.technicaltask.controller;

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


    @GetMapping("/{id}")
    public ResponseEntity<Item> findItemById(@PathVariable("id") Long id){
        Item item = itemService.findItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/add/{id}/{quantity}/{basketId}")
    public ResponseEntity<String> addItemToBasket(@PathVariable("id") Long itemId,
                                                  @PathVariable("quantity") Integer quantity,
                                                  @PathVariable("basketId") Long basketId){
        basketService.addItemToBasket(itemId, quantity, basketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }









    Item item2 = new Item.Builder()
            .withName("Pepsi").withPrice(4.0).withSpecialPrice(10.0).withUnit(4).build();
    Item item3 = new Item.Builder()
            .withName("Chips").withPrice(2.45).withSpecialPrice(4.0).withUnit(2).build();
    Item item4 = new Item.Builder()
            .withName("Beer").withPrice(2.55).withSpecialPrice(8.0).withUnit(4).build();
    Item item5 = new Item.Builder()
            .withName("Chocolate").withPrice(4.15).withSpecialPrice(10.0).withUnit(3).build();

    @GetMapping("/addItem")
    public void addItem(){
        itemService.addNewItem(item2);
        itemService.addNewItem(item3);
        itemService.addNewItem(item4);
        itemService.addNewItem(item5);
    }
}
