package com.store.service.impl;

import com.store.exception.OrderNotFoundException;
import com.store.model.StoreOrder;
import com.store.repository.StoreOrderRepository;
import com.store.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StoreOrderRepository storeOrderRepository;

    public List<StoreOrder> getAllOrders() {
        log.debug("call to get all orders");
        return storeOrderRepository.findAll();
    }


    @Override
    public StoreOrder searchOrder(long id, String orderId, String productId, String customerId) {
        StoreOrder storeOrder = storeOrderRepository.findOrder(id, orderId, productId, customerId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for this id :: " + id));
        return storeOrder;
    }

    @Override
    public StoreOrder findOrderById(long id) {
        StoreOrder storeOrder = storeOrderRepository.findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for this id :: " + id));
        return storeOrder;
    }

    public StoreOrder save(StoreOrder storeOrder) {
        return storeOrderRepository.save(storeOrder);
    }
}

