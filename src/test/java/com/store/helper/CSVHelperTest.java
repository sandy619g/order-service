package com.store.helper;

import com.store.mapper.StoreOrderMapper;
import com.store.model.StoreOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import static com.store.common.TestConstants.*;
import static com.store.common.TestUtils.getFileBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CSVHelperTest {

    @Autowired
    CSVHelper csvHelper;

    @MockBean
    StoreOrderMapper storeOrderMapper;

    @Test
    public void when_file_is_not_csv() throws FileNotFoundException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("foo", "foo.txt", TEXT_CONTENT_TYPE,
                "Hello World".getBytes());
        boolean isType = csvHelper.hasCSVFormat(mockMultipartFile);
        assertEquals(false,isType);
    }


    @Test
    public void when_file_is_csv_() throws FileNotFoundException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("foo", "foo.csv", CSV_CONTENT_TYPE,
                "Hello World".getBytes());
        boolean isType = csvHelper.hasCSVFormat(mockMultipartFile);
        assertEquals(true,isType);
    }

    @Test
    public void when_empty_file_is_parsed() throws IOException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("file", RESOURCE_NAME, CSV_CONTENT_TYPE,
                "".getBytes());
        List<StoreOrder> storeOrders = csvHelper.convert(mockMultipartFile.getInputStream());
        assertEquals(0,storeOrders.size());
    }

    @Test
    public void when_csv_file_is_parsed() throws IOException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("file", RESOURCE_NAME, CSV_CONTENT_TYPE,getFileBytes());
        List<StoreOrder> storeOrders = csvHelper.convert(mockMultipartFile.getInputStream());
        assertEquals(11,storeOrders.size());
    }

    @Test
    public void when_null_object_is_parsed() throws IOException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("file", RESOURCE_NAME, CSV_CONTENT_TYPE,getFileBytes());
        assertThrows(NullPointerException.class, () -> {
            csvHelper.convert(null);
        });
    }

}
