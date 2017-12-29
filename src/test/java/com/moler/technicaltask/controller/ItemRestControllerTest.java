package com.moler.technicaltask.controller;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.entity.BasketStatus;
import com.moler.technicaltask.entity.BasketWithItem;
import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.service.BasketService;
import com.moler.technicaltask.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ItemRestController.class, secure = false)
public class ItemRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private BasketService basketService;

    private Item mockItem = null;

    @Before
    public void setUp() throws Exception{
        mockItem = new Item.Builder()
                .withName("Pepsi").withPrice(4.0).withSpecialPrice(10.0).withUnit(4).build();
        mockItem.setId(20L);
    }


    @Test
    public void shouldRetrieveItems() throws Exception {
        List<Item> mockItems = getItems();
        when(itemService.findAll()).thenReturn(mockItems);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/items").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Beer\",\n" +
                "        \"price\": 2.55,\n" +
                "        \"unit\": 4,\n" +
                "        \"specialPrice\": 8\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Chocolate\",\n" +
                "        \"price\": 4.15,\n" +
                "        \"unit\": 3,\n" +
                "        \"specialPrice\": 10\n" +
                "    }]";

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void shouldRetrieveDetailsForItem() throws Exception {
        when(itemService.findItemById(20)).thenReturn(mockItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/items/20").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":20,\"name\":\"Pepsi\",\"price\":4.0,\"unit\":4,\"specialPrice\":10.0}";

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void shouldAddItemToBasket() throws Exception {
        Basket basket = new Basket();
        basket.setId(1L);
        basket.setBasketStatus(BasketStatus.OPEN);

        BasketWithItem exampleBasketWithItem = new BasketWithItem(basket, mockItem, 5);
        exampleBasketWithItem.setId(1L);

        when(basketService.addItemToBasket(mockItem.getId(), 5, basket.getId())).thenReturn(exampleBasketWithItem);

        String expected = "{\n" +
                "    \"id\": 1,\n" +
                "    \"basket\": {\n" +
                "        \"id\": 1,\n" +
                "        \"basketStatus\": \"OPEN\"\n" +
                "    },\n" +
                "    \"item\": {\n" +
                "        \"id\": 20,\n" +
                "        \"name\": \"Pepsi\",\n" +
                "        \"price\": 4,\n" +
                "        \"unit\": 4,\n" +
                "        \"specialPrice\": 10\n" +
                "    },\n" +
                "    \"quantity\": 5\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/items/add/20/5/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    private List<Item> getItems() {
        Item beer = new Item.Builder()
                .withName("Beer").withPrice(2.55).withSpecialPrice(8.0).withUnit(4).build();
        beer.setId(1L);
        Item chocolate = new Item.Builder()
                .withName("Chocolate").withPrice(4.15).withSpecialPrice(10.0).withUnit(3).build();
        chocolate.setId(2L);

        return Arrays.asList(beer, chocolate);
    }
}