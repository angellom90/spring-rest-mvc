package com.angello.repositories;

import com.angello.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository  extends JpaRepository<Vendor, Long> {
}
