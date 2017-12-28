package com.moler.technicaltask.util;

import com.moler.technicaltask.entity.BasketAndItem;
import com.moler.technicaltask.repository.BasketItemsRepository;
import com.moler.technicaltask.strategy.Strategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DiscountCalculator implements Strategy {

    private final BasketItemsRepository basketItemsRepository;

    public DiscountCalculator(BasketItemsRepository basketItemsRepository) {
        this.basketItemsRepository = basketItemsRepository;
    }

    public double calculateDiscount(Long basketId) {
        List<BasketAndItem> basketAndItems = basketItemsRepository.findAllByBasket_Id(basketId);
        BigDecimal result = new BigDecimal(0.0);
        for (BasketAndItem basketAndItem : basketAndItems) {
            int unit = basketAndItem.getItem().getUnit();
            double specialPrice = basketAndItem.getItem().getSpecialPrice();
            int numberOfItemsWithSpecialPrice = basketAndItem.getQuantity() / unit;
            int numberOfItemsWithNormalPrice = basketAndItem.getQuantity() % unit;
            result = result.add(BigDecimal.valueOf((numberOfItemsWithSpecialPrice * specialPrice)
                    + (numberOfItemsWithNormalPrice * basketAndItem.getItem().getPrice())));
        }
        return result.doubleValue();
    }
}
