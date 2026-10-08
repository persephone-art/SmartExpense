package com.smartexpense.repository;

import com.smartexpense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByUserId(Integer userId);
}