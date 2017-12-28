package com.moler.technicaltask.entity;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ItemTest {

    private static final Long ID = 2L;
    private static final String NAME = "Pepsi";
    private static final double PRICE = 4.50;
    private static final int UNIT = 3;
    private static final double SPECIAL_PRICE = 10;

    private Item testedObject;

    @Before
    public void setUp() throws Exception {
        testedObject = new Item.Builder()
                .withName(NAME).withPrice(PRICE).withSpecialPrice(SPECIAL_PRICE).withUnit(UNIT).build();
        testedObject.setId(ID);

    }

    @Test
    public void shouldCreateObject() throws Exception {
        assertThat(testedObject).isNotNull();
        assertThat(testedObject.getId()).isEqualTo(2);
        assertThat(testedObject.getName()).isEqualTo("Pepsi");
        assertThat(testedObject.getPrice()).isEqualTo(4.50);
        assertThat(testedObject.getUnit()).isEqualTo(3);
        assertThat(testedObject.getSpecialPrice()).isEqualTo(10);
    }
}