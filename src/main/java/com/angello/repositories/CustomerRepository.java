package com.angello.repositories;

import com.angello.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer,Long> {

   // Customer findById(Long id);
}
