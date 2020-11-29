package com.store.controller;

import com.store.common.ResponseMessage;
import com.store.exception.OrderNotFoundException;
import com.store.helper.CSVHelper;
import com.store.model.StoreOrder;
import com.store.repository.StoreOrderRepository;
import com.store.service.CSVService;
import com.store.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Slf4j
public class OrderController {

    @Autowired
    private CSVService csvService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StoreOrderRepository storeOrderRepository;

    @Autowired
    private CSVHelper csvHelper;

    @PostMapping("/orders/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        log.debug("call to upload file " + file.getOriginalFilename());
        if (csvHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                log.error(e.getMessage());
                message = "Could not upload the file: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        log.debug("call to get all orders");
        List<StoreOrder> storeOrders = orderService.getAllOrders();
        return new ResponseEntity<>(storeOrders, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable(value = "id") Long id) {
        log.debug("call to get order");
        try {
            StoreOrder storeOrder = orderService.findOrderById(id);
            return new ResponseEntity<>(storeOrder, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/search")
    public ResponseEntity<?> searchAppliance(@RequestParam(required = true) long id, @RequestParam(required = false) String orderId, @RequestParam(required = false) String productId, @RequestParam(required = false) String customerId) {
        try {
            StoreOrder storeOrder = orderService.searchOrder(id, orderId, productId, customerId);
            return new ResponseEntity<>(storeOrder, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<StoreOrder> createOrder(@RequestBody StoreOrder storeOrder) {
        log.debug("post call to save order");
        StoreOrder createdStoreOrder = orderService.save(storeOrder);
        return new ResponseEntity<>(createdStoreOrder, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable(value = "id") Long id, @RequestBody StoreOrder storeOrder) throws OrderNotFoundException {
        try {
            orderService.findOrderById(id);
            final StoreOrder updatedStoreOrder = orderService.save(storeOrder);
            return ResponseEntity.ok(updatedStoreOrder);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long id) throws OrderNotFoundException {
        try {
            StoreOrder storeOrder = orderService.findOrderById(id);
            storeOrderRepository.delete(storeOrder);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage("Store Order deleted"));
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
