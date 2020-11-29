package com.store.service;

import com.store.common.TestUtils;
import com.store.exception.OrderNotFoundException;
import com.store.model.StoreOrder;
import com.store.repository.StoreOrderRepository;
import com.store.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;
import static com.store.common.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderServiceImpl orderService;

    @MockBean
    StoreOrderRepository storeOrderRepository;

    @BeforeEach
    public void setUp(){
        when(storeOrderRepository.findAll()).thenReturn(TestUtils.mockOrders());
        when(storeOrderRepository.findOrderById((long) 101)).thenReturn(TestUtils.mockOrder());
        when(storeOrderRepository.save(any())).thenReturn(TestUtils.getStoreOrder());
    }

    @Test
    public void when_getAllOrder_thenReturnAllOrders(){
        List<StoreOrder> storeOrders = orderService.getAllOrders();
        assertEquals(storeOrders.size(),1);
    }

    @Test
    public void when_getOrderById_thenReturnSingleOrder(){
        StoreOrder storeOrder = orderService.findOrderById(Long.valueOf(101));
        assertNotNull(storeOrder);
    }

    @Test
    public void when_id_invalid_thenThrowException(){
        assertThrows(OrderNotFoundException.class, () -> {
            StoreOrder storeOrder = orderService.findOrderById(Long.valueOf(105));
        });
    }

    @Test
    public void when_save_return_savedOrder(){
        StoreOrder storeOrder = orderService.save(TestUtils.getStoreOrder());
        assertNotNull(storeOrder);
        assertEquals(ORDER_ID,storeOrder.getOrderId());
        assertEquals(CUSTOMER_ID,storeOrder.getCustomerId());
        assertEquals(PRODUCT_ID,storeOrder.getProductId());
    }

    @Test
    public void when_searchOrder_thenReturnOrder(){
        when(storeOrderRepository.findOrder(ID,ORDER_ID,PRODUCT_ID,CUSTOMER_ID)).thenReturn(TestUtils.mockOrder());
        StoreOrder storeOrder = orderService.searchOrder(ID,ORDER_ID,PRODUCT_ID,CUSTOMER_ID);
        assertNotNull(storeOrder);
        assertEquals(ORDER_ID,storeOrder.getOrderId());
        assertEquals(PRODUCT_ID,storeOrder.getProductId());
        assertEquals(CUSTOMER_ID,storeOrder.getCustomerId());
    }

    @Test
    public void when_searchOrder_withNoRecord(){
        when(storeOrderRepository.findOrder(ID,ORDER_ID,PRODUCT_ID,CUSTOMER_ID)).thenReturn(TestUtils.mockOrder());
        assertThrows(OrderNotFoundException.class, () -> {
            StoreOrder storeOrder = orderService.searchOrder(100,ORDER_ID,PRODUCT_ID,CUSTOMER_ID);
        });
    }

}
