package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Basket;

public interface BasketService {
    Basket saveBasket(Basket basket);
    void addItemToBasket(Long itemId, Integer quantity, Long basketId);
    double getTotalPrice(Long basketId);
    void closeBasket(Long basketId);
}
