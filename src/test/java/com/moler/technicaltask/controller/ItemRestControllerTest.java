package com.moler.technicaltask.controller;

import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.service.BasketService;
import com.moler.technicaltask.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
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

    Item mockItem = new Item.Builder()
            .withName("Chocolate").withPrice(4.15).withSpecialPrice(10.0).withUnit(3).build();

//    String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";
//
//    @Test
//    public void retrieveDetailsForCourse() throws Exception {
//
//        Mockito.when(
//                studentService.retrieveCourse(Mockito.anyString(),
//                        Mockito.anyString())).thenReturn(mockCourse);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
//                "/students/Student1/courses/Course1").accept(
//                MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        System.out.println(result.getResponse());
//        String expected = "{id:Course1,name:Spring,description:10 Steps}";
//
//        // {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}
//
//        JSONAssert.assertEquals(expected, result.getResponse()
//                .getContentAsString(), false);
//    }
//
//}

    @Test
    public void getAllItems() {
    }

    @Test
    public void findItemById() throws Exception {
        mockItem.setId(1L);
        when(itemService.findItemById(1)).thenReturn(mockItem);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/items/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{\"id\":20,\"name\":\"Pepsi\",\"price\":4.0,\"unit\":4,\"specialPrice\":10.0}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void addItemToBasket() {
    }
}