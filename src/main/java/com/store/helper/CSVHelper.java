package com.store.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.store.mapper.StoreOrderMapper;
import com.store.model.StoreOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
@Slf4j
public class CSVHelper {
  public static String TYPE = "text/csv";

  @Autowired
  StoreOrderMapper storeOrderMapper;

  public boolean hasCSVFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }

  public List<StoreOrder> convert(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         CSVParser csvParser = new CSVParser(fileReader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
      List<StoreOrder> storeOrders = new ArrayList<StoreOrder>();
      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      for (CSVRecord csvRecord : csvRecords) {
        StoreOrder storeOrder = storeOrderMapper.convertToStoreOrder(csvRecord);
        storeOrders.add(storeOrder);
      }
      return storeOrders;
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }


}
