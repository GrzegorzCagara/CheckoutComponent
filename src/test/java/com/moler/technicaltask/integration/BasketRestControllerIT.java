package com.moler.technicaltask.integration;

import com.moler.technicaltask.TechnicaltaskApplication;
import com.moler.technicaltask.service.BasketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechnicaltaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasketRestControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    BasketService basketService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp(){

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void closeBasketTest() throws Exception {
        int basketId = openNewBasket();
        String closeBasket = "/basket/close/"+basketId;
        mockMvc.perform(get(closeBasket))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
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


}
