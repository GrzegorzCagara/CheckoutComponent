package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.BasketStatus;
import com.moler.technicaltask.entity.Basket;

import com.moler.technicaltask.entity.BasketAndItem;
import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.repository.BasketItemsRepository;
import com.moler.technicaltask.repository.BasketRepository;
import com.moler.technicaltask.repository.ItemRepository;
import com.moler.technicaltask.exception.BasketIsClosedRunTimeException;
import com.moler.technicaltask.exception.BasketNotFoundRunTimeException;
import com.moler.technicaltask.exception.ItemNotFoundRunTimeException;
import com.moler.technicaltask.strategy.Strategy;
import com.moler.technicaltask.util.DiscountCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasketServiceImpl implements BasketService{

    private final BasketRepository basketRepository;
    private final ItemRepository itemRepository;
    private final BasketItemsRepository basketItemsRepository;
    private final DiscountCalculator discountCalculator;


    @Transactional
    public Basket saveBasket(Basket basket){
        log.info("Opening new Basket");
        basket.setBasketStatus(BasketStatus.OPEN);
        log.info("Basket with id {} opened", basket.getId());
        return basketRepository.save(basket);
    }



    @Transactional
    public void addItemToBasket(Long itemId, Integer quantity, Long basketId){
        Basket basket = returnBasketIfNotNull(basketId);
        Item item = returnItemIfNotNull(itemId);
        BasketAndItem basketItems = new BasketAndItem(basket, item, quantity);
        if (isBasketOpen(basket)){
            List<Long> basketAndItemIdAndQuantityOfItem =  returnBasketAndItemIdAndQuantityOfItemIfAlreadyExists(itemId, basketId);
            if (basketAndItemIdAndQuantityOfItem != null){
                basketItemsRepository.update(basketAndItemIdAndQuantityOfItem.get(0), (int) (quantity + basketAndItemIdAndQuantityOfItem.get(1)));
                log.info("Updating Item : {}, with quantity {}", item, quantity);
            } else{
                basketItemsRepository.save(basketItems);
                log.info("Inserting Item : {}, with quantity {}", item, quantity);
            }
        } else{
            log.error("Basket with id {} is closed.", basketId);
            throw new BasketIsClosedRunTimeException((String.format("Basket with id: %d is closed.", basketId)));
        }
    }

    @Transactional
    public double getTotalPrice(Long basketId){
        double result = calculateDiscount(discountCalculator, basketId);
        log.info("Total price of basket with id {} = {}", basketId, result);
        return result;
    }


    public void closeBasket(Long basketId){
        Basket basket = returnBasketIfNotNull(basketId);
        basket.setBasketStatus(BasketStatus.CLOSED);
        basketRepository.save(basket);
        log.info("Basket with id {} closed", basketId);
    }


    private boolean isBasketOpen(Basket basket){
        if (basket.getBasketStatus() == BasketStatus.OPEN){
            return true;
        }
        return false;
    }

    private Basket returnBasketIfNotNull(Long basketId){
        log.info("Fetching Basket with id {}", basketId);
        Optional<Basket> optionalBasket = basketRepository.findBasketById(basketId);
        if (optionalBasket.isPresent()) {
            return optionalBasket.get();
        } else {
            log.error("Basket with id {} not found.", basketId);
            throw new BasketNotFoundRunTimeException(String.format("Basket with id: %d not found.", basketId));
        }
    }

    private Item returnItemIfNotNull(long itemId){
        log.info("Fetching Item with id {}", itemId);
        Optional<Item> optionalItem = itemRepository.findItemById(itemId);
        if (optionalItem.isPresent()) {
            return optionalItem.get();
        } else {
            log.error("Item with id {} not found.", itemId);
            throw new ItemNotFoundRunTimeException(String.format("Basket with id: %d not found.", itemId));
        }
    }

    private List<Long> returnBasketAndItemIdAndQuantityOfItemIfAlreadyExists(Long itemId, Long basketId){
        List<Long> basketAndItemIdAndQuantity = new ArrayList<>();
        List<BasketAndItem> basketAndItems = basketItemsRepository.findAllByBasket_Id(basketId);
        for (BasketAndItem basketAndItem : basketAndItems) {
            if (basketAndItem.getItem().getId() == itemId){
                basketAndItemIdAndQuantity.add(basketAndItem.getId());
                basketAndItemIdAndQuantity.add(Long.valueOf(basketAndItem.getQuantity()));
                return basketAndItemIdAndQuantity;
            }
        }
        return null;
    }

    private double calculateDiscount(Strategy strategy, Long basketId){
        return strategy.calculateDiscount(basketId);
    }


}