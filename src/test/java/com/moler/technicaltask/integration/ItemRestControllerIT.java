package com.moler.technicaltask.integration;

import com.moler.technicaltask.TechnicaltaskApplication;
import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.entity.BasketStatus;
import com.moler.technicaltask.entity.BasketWithItem;
import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.service.ItemService;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechnicaltaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemRestControllerIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @Autowired
    ItemService itemService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        addItemsToDatabase();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void retrieveDetailsForItem() throws JSONException {
        List<Item> items = itemService.findAll();
        Item item = items.get(0);
        Long itemId = item.getId();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/items/"+itemId),
                HttpMethod.GET, entity, String.class);

        String expected = String.format("{id:%d,name:%s,price:%f,unit:%d,specialPrice:%f}",
                item.getId(), item.getName(), item.getPrice(), item.getUnit(), item.getSpecialPrice());
        System.out.println(expected);
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


    @Test
    public void openBasket() throws Exception {
        openNewBasket();
    }

    @Test
    public void shouldReturnCorrectHttpResponseWhenRetrieveAllItems() throws Exception {
        String getAllItems = "/items";
        mockMvc.perform(get(getAllItems))
                .andExpect(status().isOk());
    }

    @Test
    public void addItemToBasket() throws Exception {

        Basket basket = new Basket();
        basket.setId(1L);
        basket.setBasketStatus(BasketStatus.OPEN);
        Item beer = new Item.Builder()
                .withName("Beer").withPrice(2.55).withSpecialPrice(8.0).withUnit(4).build();
        beer.setId(22L);

        BasketWithItem exampleBasketWithItem = new BasketWithItem(basket, beer, 5);
        exampleBasketWithItem.setId(1L);

        int baksetId = openNewBasket();

        String insertNewItem = "/items/add/{itemId}/{quantity}/{basketId}";

        mockMvc.perform(post(insertNewItem,22, 5, baksetId))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private Integer openNewBasket() throws Exception {
        String openNewBasket = "/basket/open";
        MvcResult result = mockMvc.perform(post(openNewBasket))
                .andExpect(status().isCreated())
                .andDo(print()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String resultList[] = response.getContentAsString().split(",");
        String basketId = resultList[0].substring(6);

        return Integer.valueOf(basketId);
    }

    private void addItemsToDatabase() {
        Item pepsi = new Item.Builder()
                .withName("Pepsi").withPrice(4.0).withSpecialPrice(10.0).withUnit(4).build();
        Item chips = new Item.Builder()
                .withName("Chips").withPrice(2.45).withSpecialPrice(4.0).withUnit(2).build();
        Item beer = new Item.Builder()
                .withName("Beer").withPrice(2.55).withSpecialPrice(8.0).withUnit(4).build();
        Item chocolate = new Item.Builder()
                .withName("Chocolate").withPrice(4.15).withSpecialPrice(10.0).withUnit(3).build();
        itemService.save(pepsi);
        itemService.save(chips);
        itemService.save(beer);
        itemService.save(chocolate);
    }
}
