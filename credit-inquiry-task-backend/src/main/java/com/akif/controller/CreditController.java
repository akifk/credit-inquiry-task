package com.akif.controller;

import com.akif.Util.Lggr;
import com.akif.dto.CreditDto;
import com.akif.dto.CustomerDto;
import com.akif.response.Response;
import com.akif.service.CreditService;
import com.akif.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
public class CreditController {
    private final CreditService creditService;

    @GetMapping("getCreditStatus/{identityNumber}")
    public ResponseEntity<Response> getCreditStatus(@PathVariable("identityNumber") long id) {
        try {
            Lggr.getInstance().info("credit/getCreditStatus get request received");
            return ResponseEntity.ok(creditService.getCreditStatus(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("applyForCredit")
    public ResponseEntity<Response> add(@RequestBody CustomerDto customerDto) {
        try {
            Lggr.getInstance().info("getCreditStatus post request received");
            return ResponseEntity.ok(creditService.applyForCredit(customerDto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
