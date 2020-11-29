package com.store.service;

import com.store.model.StoreOrder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CSVService {

    List<StoreOrder> save(MultipartFile file);
}
