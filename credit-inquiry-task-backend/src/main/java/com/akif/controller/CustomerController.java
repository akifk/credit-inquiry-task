package com.akif.controller;

import com.akif.Util.Lggr;
import com.akif.dto.CustomerDto;
import com.akif.response.Response;
import com.akif.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Response> add(@RequestBody CustomerDto customerDto) {
        try {
            Lggr.getInstance().info("customer post request received");
            return ResponseEntity.ok(customerService.add(customerDto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{identityNumber}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("identityNumber") long id) {
        try {
            Lggr.getInstance().info("customer delete request received");
            customerService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Response> update(@RequestBody CustomerDto customerDto) {
        try {
            Lggr.getInstance().info("customer update request received");
            return ResponseEntity.ok(customerService.update(customerDto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
