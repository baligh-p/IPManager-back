package com.example.demo.Repository;

import com.example.demo.Model.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
    Direction findByDirectionName(String name);
}
