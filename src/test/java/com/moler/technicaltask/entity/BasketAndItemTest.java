package com.moler.technicaltask.entity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;


public class BasketAndItemTest {

    private BasketAndItem testedObject;
    private Item item;
    private Basket basket;

    private static final Long ID = 1L;
    private static final Integer QUANTITY = 13;

    @Before
    public void setUp() throws Exception {
        item = Mockito.mock(Item.class);
        basket = Mockito.mock(Basket.class);
        testedObject = new BasketAndItem(basket, item, QUANTITY);
        testedObject.setId(ID);
    }

    @Test
    public void shouldCreateObject() throws Exception {
        assertThat(testedObject).isNotNull();
        assertThat(testedObject.getId()).isEqualTo(1);
        assertThat(testedObject.getQuantity()).isEqualTo(13);
        assertThat(testedObject.getItem()).isEqualTo(item);
        assertThat(testedObject.getBasket()).isEqualTo(basket);
    }
}