package com.github.benjamineckstein.showcase.customer.controller;

import com.github.benjamineckstein.showcase.customer.boundary.CustomerBoundary;
import com.github.benjamineckstein.showcase.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerBoundary customerBoundary;

  @GetMapping("api/customer/{name}")
  public ResponseEntity<List<Customer>> findCarsByName(@PathVariable String name) {
    return ResponseEntity.ok(customerBoundary.findCustomerByName(name));
  }

}
