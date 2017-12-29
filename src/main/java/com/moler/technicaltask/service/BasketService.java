package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.entity.BasketWithItem;

public interface BasketService {
    Basket saveBasket(Basket basket);
    BasketWithItem addItemToBasket(Long itemId, Integer quantity, Long basketId);
    double getTotalPrice(Long basketId);
    void closeBasket(Long basketId);
}
