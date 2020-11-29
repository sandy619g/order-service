package com.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.store.helper.CSVHelper;
import com.store.model.StoreOrder;
import com.store.repository.StoreOrderRepository;
import com.store.service.CSVService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class CSVServiceImpl implements CSVService {
  @Autowired
  StoreOrderRepository repository;

  @Autowired
  CSVHelper csvHelper;

  public List<StoreOrder> save(MultipartFile file) {
    List<StoreOrder> storeOrderList = new ArrayList<>();
    try {
      log.debug("CSVService : save() to file "+file.getOriginalFilename());
      List<StoreOrder> storeOrders = csvHelper.convert(file.getInputStream());
      storeOrderList = repository.saveAll(storeOrders);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
    return storeOrderList;
  }

}
