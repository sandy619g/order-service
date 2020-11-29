package com.store.controller;

import com.store.exception.OrderNotFoundException;
import com.store.service.CSVService;
import com.store.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.store.common.TestConstants.*;
import static com.store.common.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest extends ControllerTest{

    @Autowired
    private MockMvc mvc;

    CSVService mockCsvService = Mockito.mock(CSVService.class);

    @MockBean
    OrderService orderService;

    @BeforeEach
    public void setUp() {
        super.setUp();
    }
    @Test
    public void when_invalid_file_is_uploaded() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file",RESOURCE_NAME, TEXT_CONTENT_TYPE, getFileBytes());
        MvcResult mockMvcResult = mvc.perform(multipart(ORDERS_API_URL+"/upload").file(multipartFile)).andReturn();
        assertEquals(400,mockMvcResult.getResponse().getStatus());
    }

    @Test
    public void when_csv_file_is_uploaded() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", RESOURCE_NAME, CSV_CONTENT_TYPE, getFileBytes());
        MvcResult mockMvcResult = mvc.perform(multipart(ORDERS_API_URL+"/upload").file(multipartFile)).andReturn();
        assertEquals(200,mockMvcResult.getResponse().getStatus());
    }

    @Test
    public void test_orders_get_api() throws Exception {
        when(orderService.getAllOrders()).thenReturn(mockOrders());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(ORDERS_API_URL).contentType("application/json")).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_orderById_get_api() throws Exception {
        when(orderService.findOrderById((long) 101)).thenReturn(getStoreOrder());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(ORDERS_API_URL+"/101").contentType("application/json")).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_orderById_get_api_noRecord() throws Exception {
        when(orderService.findOrderById((long) 101)).thenThrow(OrderNotFoundException.class);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(ORDERS_API_URL+"/101").contentType("application/json")).andReturn();
        assertEquals(404,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_order_post_api() throws Exception {
        String inputJson = super.mapToJson(getStoreOrder());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ORDERS_API_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void test_order_put_api() throws Exception {
        String inputJson = super.mapToJson(getStoreOrder());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(ORDERS_API_URL+"/101")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void test_order_put_api_invalidRecord() throws Exception {
        when(orderService.findOrderById(ID)).thenThrow(OrderNotFoundException.class);
        String inputJson = super.mapToJson(getStoreOrder());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(ORDERS_API_URL+"/"+ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void whenValidSearchInput_thenReturns200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(ORDERS_API_URL+"/search")
                .contentType("application/json")
                .param("id", String.valueOf(ID))
                .param("orderId", ORDER_ID)
                .param("productId", PRODUCT_ID)
                .param("customerId", CUSTOMER_ID)
                .content(mapToJson(getStoreOrder())))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidSearchInput_thenReturnsException() throws Exception {
        when(orderService.searchOrder(ID,ORDER_ID,PRODUCT_ID,CUSTOMER_ID)).thenThrow(OrderNotFoundException.class);
        mvc.perform(MockMvcRequestBuilders.get(ORDERS_API_URL+"/search")
                .contentType("application/json")
                .param("id", String.valueOf(ID))
                .param("orderId", ORDER_ID)
                .param("productId", PRODUCT_ID)
                .param("customerId", CUSTOMER_ID)
                .content(mapToJson(getStoreOrder())))
                .andExpect(status().isNotFound());
    }

}