package com.store.service;

import com.store.model.StoreOrder;
import com.store.repository.StoreOrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.util.List;
import static com.store.common.TestConstants.*;
import static com.store.common.TestUtils.getFileBytes;
import static com.store.common.TestUtils.mockOrders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CSVServiceTest {

    @MockBean
    StoreOrderRepository storeOrderRepository;

    @Autowired
    CSVService csvService;

    @Test
    public void test_save() throws IOException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("sales", RESOURCE_NAME, CSV_CONTENT_TYPE,getFileBytes());
        when(storeOrderRepository.saveAll(Mockito.any())).thenReturn(mockOrders());
        List<StoreOrder> storeOrders = csvService.save(mockMultipartFile);
        assertEquals(mockOrders().size(),storeOrders.size());
    }

    @Test
    public void test_save_for_null_object() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            csvService.save(null);
        });
        String expectedMessage = "fail to store csv data";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
