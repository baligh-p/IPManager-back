package com.example.demo.Repository;

import com.example.demo.Model.IPAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPAddressRepository extends JpaRepository<IPAddress , Long> {
}
