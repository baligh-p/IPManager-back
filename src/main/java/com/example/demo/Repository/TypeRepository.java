package com.example.demo.Repository;


import com.example.demo.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type , Long> {
    Type findByTypeName(String name);
}
