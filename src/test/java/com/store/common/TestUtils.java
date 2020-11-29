package com.store.common;

import com.store.model.StoreOrder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.store.common.TestConstants.RESOURCE_NAME;

public class TestUtils {

    public static final long ID = 101;
    public final static String ORDER_ID = "GT123";
    public final static String PRODUCT_ID = "POI890";
    private final static String CATEGORY = "Food";
    public final static String CUSTOMER_ID = "CX123";
    private final static String CUSTOMER_NAME = "John Bridge";
    private static final double DISCOUNT = 5.0;

    public static byte[] getFileBytes() throws IOException {
        ClassLoader classLoader = new TestUtils().getClass().getClassLoader();
        File fileObj = new File(classLoader.getResource(RESOURCE_NAME).getFile());
        return Files.readAllBytes(fileObj.toPath());
    }

    public static List<StoreOrder> mockOrders(){
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(ID);
        storeOrder.setProductId(PRODUCT_ID);
        storeOrder.setOrderId(ORDER_ID);
        storeOrder.setCategory(CATEGORY);
        storeOrder.setCustomerId(CUSTOMER_ID);
        storeOrder.setCustomerName(CUSTOMER_NAME);
        storeOrder.setDiscount(DISCOUNT);
        List<StoreOrder> storeOrders = new ArrayList<>();
        storeOrders.add(storeOrder);
        return storeOrders;
    }

    public static Optional<StoreOrder> mockOrder(){
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(ID);
        storeOrder.setOrderId(ORDER_ID);
        storeOrder.setProductId(PRODUCT_ID);
        storeOrder.setCategory(CATEGORY);
        storeOrder.setCustomerId(CUSTOMER_ID);
        storeOrder.setCustomerName(CUSTOMER_NAME);
        storeOrder.setDiscount(DISCOUNT);
        return Optional.of((StoreOrder) storeOrder);
    }

    public static StoreOrder getStoreOrder(){
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(ID);
        storeOrder.setProductId(PRODUCT_ID);
        storeOrder.setOrderId(ORDER_ID);
        storeOrder.setCategory(CATEGORY);
        storeOrder.setCustomerId(CUSTOMER_ID);
        storeOrder.setCustomerName(CUSTOMER_NAME);
        storeOrder.setDiscount(DISCOUNT);
        return storeOrder;
    }
}
