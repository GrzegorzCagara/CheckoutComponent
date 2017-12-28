package com.moler.technicaltask.service;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.repository.BasketItemsRepository;
import com.moler.technicaltask.repository.BasketRepository;
import com.moler.technicaltask.repository.ItemRepository;
import com.moler.technicaltask.util.DiscountCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
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
    private BasketItemsRepository mockBasketItemsRepository;
    @Mock
    private DiscountCalculator mockDiscountCalculator;

    @InjectMocks
    private BasketServiceImpl testedObject;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldAddNewBasket() {
        Basket basketToAdd = new Basket();
        when(mockBasketRepository.save(basketToAdd)).thenReturn(basketToAdd);

        testedObject.saveBasket(basketToAdd);

        verify(mockBasketRepository, times(1)).save(basketToAdd);
    }

    @Test
    public void addItemToBasket() {
    }

    @Test
    public void getTotalPrice() {
    }

    @Test
    public void closeBasket() {
    }
}