
        package com.smartexpense.controller;

import com.smartexpense.model.Expense;
import com.smartexpense.model.Income;
import com.smartexpense.repository.ExpenseRepository;
import com.smartexpense.repository.IncomeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class DashboardController {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public DashboardController(
            IncomeRepository incomeRepository,
            ExpenseRepository expenseRepository) {

        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        // Get the ID of the currently logged-in user
        Integer userId = (Integer) session.getAttribute("userId");

        // If nobody is logged in, return to login page
        if (userId == null) {
            return "redirect:/";
        }

        // Get only this user's income records
        List<Income> incomes =
                new ArrayList<>(incomeRepository.findByUserId(userId));

        // Get only this user's expense records
        List<Expense> expenses =
                new ArrayList<>(expenseRepository.findByUserId(userId));

        // Calculate total income
        BigDecimal totalIncome = incomes.stream()
                .map(Income::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate total expenses
        BigDecimal totalExpense = expenses.stream()
                .map(Expense::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate remaining balance
        BigDecimal balance = totalIncome.subtract(totalExpense);

        // Sort income: newest first
        incomes.sort(
                Comparator.comparing(
                        Income::getIncomeDate,
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );

        // Sort expenses: newest first
        expenses.sort(
                Comparator.comparing(
                        Expense::getExpenseDate,
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
        );

        // Get the latest 5 income records
        List<Income> recentIncomes = incomes.stream()
                .limit(5)
                .toList();

        // Get the latest 5 expense records
        List<Expense> recentExpenses = expenses.stream()
                .limit(5)
                .toList();

        // Send values to dashboard.html
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("balance", balance);
        model.addAttribute("recentIncomes", recentIncomes);
        model.addAttribute("recentExpenses", recentExpenses);

        // Make the logged-in user's name available to the page
        model.addAttribute(
                "username",
                session.getAttribute("username")
        );

        return "dashboard";
    }
}

