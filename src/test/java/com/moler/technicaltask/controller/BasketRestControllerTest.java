package com.moler.technicaltask.controller;

import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.entity.BasketStatus;
import com.moler.technicaltask.service.BasketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BasketRestController.class, secure = false)
public class BasketRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BasketService basketService;

    private Basket basket = null;

    @Before
    public void setUp() throws Exception {
        basket = new Basket();
        basket.setId(1L);
        basket.setBasketStatus(BasketStatus.OPEN);
    }

    @Test
    public void shouldOpenBasket() throws Exception {
        when(basketService.openBasket()).thenReturn(basket);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/basket/open").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();


        MockHttpServletResponse response = result.getResponse();

        String expected = "{\"id\":1,\"basketStatus\":\"OPEN\"}";

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void calculateTotalPriceAndCloseBasket() throws Exception {
        double value = 14;
        when(basketService.getTotalPrice(basket.getId())).thenReturn(value);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/basket/close/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String expected = "14.0";

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
}