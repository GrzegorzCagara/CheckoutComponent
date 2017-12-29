package com.moler.technicaltask.entity;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

    private Basket testedObject;

    @Before
    public void setUp() throws Exception{
        testedObject = new Basket();
        testedObject.setId(1L);
        testedObject.setBasketStatus(BasketStatus.OPEN);
    }

    @Test
    public void shouldCreateObject() throws Exception{
        assertThat(testedObject).isNotNull();
        assertThat(testedObject.getId()).isEqualTo(1);
        assertThat(testedObject.getBasketStatus()).isEqualTo(BasketStatus.OPEN);
    }
}
