package com.example.demo.Repository;

import com.example.demo.Model.IPAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPAddressRepository extends JpaRepository<IPAddress , Long> {
    IPAddress findByAddress(String name);
    List<IPAddress> findByDirection(String name);
}
