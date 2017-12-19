package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasketService {
    BasketRepository basketRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Transactional
    public void addBasket(Basket basket){
        basketRepository.save(basket);
    }
}
