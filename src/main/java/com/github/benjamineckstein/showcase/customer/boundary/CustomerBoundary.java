package com.github.benjamineckstein.showcase.customer.boundary;

import com.github.benjamineckstein.showcase.architecture.Boundary;
import com.github.benjamineckstein.showcase.customer.entity.Customer;
import com.github.benjamineckstein.showcase.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Boundary
@RequiredArgsConstructor
public class CustomerBoundary {

  private final CustomerRepository customerRepository;

  @Transactional
  public List<Customer> findCustomerByName(String name) {
    return Optional.ofNullable(name)
        .map(
            nameNotNull ->
                    customerRepository.findAll().stream()
                    .filter(customer -> nameNotNull.equals(customer.getName()))
                    .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

}
