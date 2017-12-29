package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.entity.BasketStatus;
import com.moler.technicaltask.entity.BasketWithItem;
import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.exception.BasketIsClosedRunTimeException;
import com.moler.technicaltask.exception.BasketNotFoundRunTimeException;
import com.moler.technicaltask.exception.ItemNotFoundRunTimeException;
import com.moler.technicaltask.repository.BasketWithItemRepository;
import com.moler.technicaltask.repository.BasketRepository;
import com.moler.technicaltask.repository.ItemRepository;
import com.moler.technicaltask.util.DiscountCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketServiceImplTest {
    @Mock
    private BasketRepository mockBasketRepository;
    @Mock
    private ItemRepository mockItemRepository;
    @Mock
    private BasketWithItemRepository mockBasketWithItemRepository;
    @Mock
    private DiscountCalculator mockDiscountCalculator;

    @InjectMocks
    private BasketServiceImpl testedObject;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldOpenBasket() {
        Basket basketToAdd = new Basket();
        basketToAdd.setBasketStatus(BasketStatus.OPEN);
        when(mockBasketRepository.save(basketToAdd)).thenReturn(basketToAdd);

        testedObject.openBasket();

        verify(mockBasketRepository, times(1)).save(refEq(basketToAdd));
    }

    @Test
    public void shouldAddItemToBasketWhenBasketIsOpen() {
        Item itemToAdd = getItem();
        Basket basket = getOpenBasket();
        BasketWithItem basketWithItem = new BasketWithItem(basket, itemToAdd, 15);

        when(mockBasketRepository.findBasketById(basket.getId())).thenReturn(java.util.Optional.of(basket));
        when(mockItemRepository.findItemById(itemToAdd.getId())).thenReturn(java.util.Optional.of(itemToAdd));

        when(mockBasketWithItemRepository.save(basketWithItem)).thenReturn(basketWithItem);

        testedObject.addItemToBasket(itemToAdd.getId(), 15, basket.getId());

        verify(mockBasketWithItemRepository, times(1)).save(refEq(basketWithItem));
    }


    @Test(expected = BasketIsClosedRunTimeException.class)
    public void shouldThrowBasketIsClosedRunTimeExceptionWhenBasketIsClosed() {
        Item itemToAdd = getItem();
        Basket basket = getClosedBasket();
        BasketWithItem basketWithItem = new BasketWithItem(basket, itemToAdd, 11);


        when(mockBasketRepository.findBasketById(basket.getId())).thenReturn(java.util.Optional.of(basket));
        when(mockItemRepository.findItemById(itemToAdd.getId())).thenReturn(java.util.Optional.of(itemToAdd));
        when(mockBasketWithItemRepository.save(basketWithItem)).thenReturn(basketWithItem);

        testedObject.addItemToBasket(itemToAdd.getId(), 15, basket.getId());
    }

    @Test
    public void shouldReturnTotalPrice() {
        Basket basket = getOpenBasket();
        Item itemA = getItem();
        Item itemB = getItem();

        BasketWithItem basketWithItemA = new BasketWithItem(basket, itemA, 6);
        BasketWithItem basketWithItemB = new BasketWithItem(basket, itemB, 1);

        List<BasketWithItem> items = new ArrayList<>();
        items.add(basketWithItemA);
        items.add(basketWithItemB);

        when(mockBasketWithItemRepository.findAllByBasket_Id(basket.getId())).thenReturn(items);
        when(mockDiscountCalculator.calculateDiscount(basket.getId())).thenReturn(10.0);

        double result = testedObject.getTotalPrice(basket.getId());

        assertThat(result).isEqualTo(10.0);
    }


    @Test
    public void shouldCloseBasket() {
        Basket basket = getOpenBasket();

        when(mockBasketRepository.findBasketById(basket.getId())).thenReturn(java.util.Optional.ofNullable(basket));

        testedObject.closeBasket(basket.getId());

        assertThat(basket.getBasketStatus()).isEqualTo(BasketStatus.CLOSED);
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

    private Basket getClosedBasket() {
        Basket basket = new Basket();
        basket.setId(1L);
        basket.setBasketStatus(BasketStatus.CLOSED);
        return basket;
    }
}