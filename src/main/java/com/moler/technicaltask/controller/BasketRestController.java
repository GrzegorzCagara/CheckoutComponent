package com.moler.technicaltask.controller;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasketRestController {

    private final BasketService basketService;

    @PostMapping("/open")
    public ResponseEntity<Long> openBasket(){
        Basket basket = new Basket();
        basket = basketService.saveBasket(basket);
        return new ResponseEntity<>(basket.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/close/{basketId}")
    public ResponseEntity<Double> CalculateTotalPriceAndCloseBasket(@PathVariable("basketId") Long basketId){
        double totalPrice = basketService.getTotalPrice(basketId);
        basketService.closeBasket(basketId);
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }

}
