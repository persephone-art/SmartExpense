
        package com.smartexpense.controller;

import com.smartexpense.model.Expense;
import com.smartexpense.repository.ExpenseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Display Add Expense page
    @GetMapping("/expense")
    public String showExpensePage(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/";
        }

        return "expense";
    }

    // Save expense for the logged-in user
    @PostMapping("/expense")
    public String addExpense(
            @ModelAttribute Expense expense,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/";
        }

        // Associate this expense with the logged-in user
        expense.setUserId(userId);

        expenseRepository.save(expense);

        redirectAttributes.addFlashAttribute(
                "message", "Expense added successfully!");

        return "redirect:/expense";
    }
}
