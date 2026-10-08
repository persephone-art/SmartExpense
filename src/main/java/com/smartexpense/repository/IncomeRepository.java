package com.smartexpense.repository;

import com.smartexpense.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

    List<Income> findByUserId(Integer userId);
}
