package com.moler.technicaltask.integration;

import com.moler.technicaltask.TechnicaltaskApplication;
import com.moler.technicaltask.entity.Basket;
import com.moler.technicaltask.entity.BasketStatus;
import com.moler.technicaltask.entity.BasketWithItem;
import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.repository.BasketRepository;
import com.moler.technicaltask.repository.ItemRepository;
import com.moler.technicaltask.service.BasketService;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

import static org.mockito.Mockito.when;
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

    @Mock
    BasketRepository basketRepository;

    @Mock
    ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void retrieveDetailsForItem() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/items/20"),
                HttpMethod.GET, entity, String.class);

        String expected = "{id:20,name:Pepsi,price:4.0,unit:4,specialPrice:10.0}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void openNewBasket() throws Exception {
        openNewBasket2();
    }

//    @Test
//    public void retrievieItems() throws JSONException {
//        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//        ResponseEntity<String> response = restTemplate.exchange(
//                createURLWithPort("/items"),
//                HttpMethod.GET, entity, String.class);
//
//        String expected = "[{id:1,name:Beer,price:2.55,unit:4,specialPrice:8.0}," +
//                "{id:2,name:Chocolate,price:4.15,unit:3,specialPrice:10.0}]";
//        JSONAssert.assertEquals(expected, response.getBody(), false);
//    }

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

        int baksetId = openNewBasket2();


        String insertNewItem = "/items/add/{itemId}/{quantity}/{basketId}";

        //when(itemRepository.findItemById(1)).thenReturn(java.util.Optional.ofNullable(beer));
        mockMvc.perform(post(insertNewItem,20, 5, baksetId))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private Integer openNewBasket2() throws Exception {
        String openNewBasket = "/basket/open";
        MvcResult result = mockMvc.perform(post(openNewBasket))
                .andExpect(status().isCreated())
                .andDo(print()).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String resultList[] = response.getContentAsString().split(",");
        String basketId = resultList[0].substring(6);

        return Integer.valueOf(basketId);
    }
}
