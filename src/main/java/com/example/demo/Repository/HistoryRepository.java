package com.example.demo.Repository;

import com.example.demo.Model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History , Long> {
}
