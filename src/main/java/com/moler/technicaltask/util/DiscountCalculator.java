package com.moler.technicaltask.util;

import com.moler.technicaltask.entity.BasketWithItem;
import com.moler.technicaltask.repository.BasketWithItemRepository;
import com.moler.technicaltask.strategy.Strategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DiscountCalculator implements Strategy {

    private final BasketWithItemRepository basketWithItemRepository;

    public DiscountCalculator(BasketWithItemRepository basketWithItemRepository) {
        this.basketWithItemRepository = basketWithItemRepository;
    }

    public double calculateDiscount(Long basketId) {
        List<BasketWithItem> basketWithItems = basketWithItemRepository.findAllByBasket_Id(basketId);
        BigDecimal result = new BigDecimal(0.0);
        for (BasketWithItem basketWithItem : basketWithItems) {
            int unit = basketWithItem.getItem().getUnit();
            double specialPrice = basketWithItem.getItem().getSpecialPrice();
            int numberOfItemsWithSpecialPrice = basketWithItem.getQuantity() / unit;
            int numberOfItemsWithNormalPrice = basketWithItem.getQuantity() % unit;
            result = result.add(BigDecimal.valueOf((numberOfItemsWithSpecialPrice * specialPrice)
                    + (numberOfItemsWithNormalPrice * basketWithItem.getItem().getPrice())));
        }
        return result.doubleValue();
    }
}
