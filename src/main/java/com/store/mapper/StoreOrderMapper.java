package com.store.mapper;

import com.store.model.StoreOrder;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.store.common.CsvConstants.*;
@Component
public class StoreOrderMapper {

    public static  final String DATE_FORMAT = "dd.MM.yy";
    public StoreOrder convertToStoreOrder(CSVRecord csvRecord){
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setCategory(csvRecord.get(CATEGORY));
        storeOrder.setCustomerId(csvRecord.get(CUST_ID));
        storeOrder.setCustomerName(csvRecord.get(CUST_NAME));
        storeOrder.setDiscount(Double.valueOf(csvRecord.get(DISCOUNT)));
        storeOrder.setOrderDate(convertStringToDate(csvRecord.get(ORDER_DATE)));
        storeOrder.setProductId(csvRecord.get(PRODUCT_ID));
        storeOrder.setOrderId(csvRecord.get(ORDER_ID));
        storeOrder.setProductName(csvRecord.get(PRODUCT_NAME));
        storeOrder.setProfit(Double.valueOf(csvRecord.get(PROFIT)));
        storeOrder.setQuantity(Integer.valueOf(csvRecord.get(QUANTITY)));
        storeOrder.setShipDate(convertStringToDate(csvRecord.get(SHIP_DATE)));
        storeOrder.setShipMode(csvRecord.get(SHIP_MODE));
        return storeOrder;
    }

    private LocalDate convertStringToDate(String dateStr){
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
        return date;
    }

}
