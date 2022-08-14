package com.example.demo.Repository;

import com.example.demo.Model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark , Long> {
    Mark findByMarkName(String name);
}
