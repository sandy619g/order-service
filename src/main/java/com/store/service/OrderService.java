package com.store.service;

import com.store.model.StoreOrder;

import java.util.List;

public interface OrderService {
    List<StoreOrder> getAllOrders();
    StoreOrder save(StoreOrder storeOrder);
    StoreOrder findOrderById(long id);
    StoreOrder searchOrder(long id, String orderId, String productId, String customerId);
}
