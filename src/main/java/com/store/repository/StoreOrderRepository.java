package com.store.repository;

import com.store.model.StoreOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreOrderRepository extends JpaRepository<StoreOrder, Long> {

    @Query("select s from StoreOrder s where s.id = :id and s.orderId = :orderId and s.productId = :productId and s.customerId = :customerId")
    Optional<StoreOrder> findOrder(@Param("id") long id, @Param("orderId") String orderId, @Param("productId") String productId, @Param("customerId") String customerId);

    @Query("select s from StoreOrder s where s.id = :id")
    Optional<StoreOrder> findOrderById(@Param("id") long id);
}