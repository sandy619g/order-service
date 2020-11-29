package com.store.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@IdClass(UniqueOrder.class)
public class StoreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Id
    @Column(nullable = false,length = 20)
    private String orderId;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private LocalDate shipDate;
    @Column(length = 20)
    private String shipMode;
    @NotNull
    private int quantity;

    @Column(precision = 3,scale = 2)
    private double discount;

    @NotNull
    @Column(precision = 6,scale = 2)
    private double profit;
    @Id
    @Column(nullable = false,length = 20)
    private String productId;
    @NotNull
    private String customerName;
    @NotNull
    private String category;
    @Id
    @Column(nullable = false,length = 20)
    private String customerId;
    private String productName;
}
