package com.moler.technicaltask.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BasketWithItemTest {

    @Mock
    private Item item;

    @Mock
    private Basket basket;

    @InjectMocks
    private BasketWithItem testedObject;

    private static final Long ID = 1L;
    private static final Integer QUANTITY = 13;

    @Before
    public void setUp() throws Exception {
        testedObject = new BasketWithItem(basket, item, QUANTITY);
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