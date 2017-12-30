package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.entity.BasketStatus;
import com.moler.technicaltask.entity.BasketWithItem;
import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.repository.BasketWithItemRepository;
import com.moler.technicaltask.util.DiscountCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscountCalculatorTest {

    @Mock
    private BasketWithItemRepository mockBasketWithItemRepository;

    @InjectMocks
    private DiscountCalculator testedObject;

    @Test
    public void shouldCalculateDiscount() {
        Basket basket = getOpenBasket();
        Item itemA = getItem();
        Item itemB = getItem();

        BasketWithItem basketWithItemA = new BasketWithItem(basket, itemA, 6);
        BasketWithItem basketWithItemB = new BasketWithItem(basket, itemB, 1);

        List<BasketWithItem> items = new ArrayList<>();
        items.add(basketWithItemA);
        items.add(basketWithItemB);

        when(mockBasketWithItemRepository.findAllByBasket_Id(basket.getId())).thenReturn(items);


        double totalPrice = testedObject.calculateDiscount(basket.getId());
        double expectedPrice = 20 + 4.15;
        assertThat(totalPrice).isEqualTo(expectedPrice);
    }


    private Item getItem() {
        Item item =  new Item.Builder()
                .withName("Chocolate").withPrice(4.15).withSpecialPrice(10.0).withUnit(3).build();
        item.setId(5L);
        return item;
    }

    private Basket getOpenBasket() {
        Basket basket = new Basket();
        basket.setId(1L);
        basket.setBasketStatus(BasketStatus.OPEN);
        return basket;
    }
}
