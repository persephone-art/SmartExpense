
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
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportsController {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public ReportsController(
            IncomeRepository incomeRepository,
            ExpenseRepository expenseRepository) {

        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/reports")
    public String reports(Model model, HttpSession session) {

        // Get the currently logged-in user's ID
        Integer userId = (Integer) session.getAttribute("userId");

        // Redirect to login if no user is logged in
        if (userId == null) {
            return "redirect:/";
        }

        // Retrieve only this user's records
        List<Income> incomes =
                incomeRepository.findByUserId(userId);

        List<Expense> expenses =
                expenseRepository.findByUserId(userId);

        // Calculate totals
        BigDecimal totalIncome = incomes.stream()
                .map(Income::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = expenses.stream()
                .map(Expense::getAmount)
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = totalIncome.subtract(totalExpense);

        // Prepare monthly income and expense data
        Map<String, BigDecimal> monthlyIncome =
                new LinkedHashMap<>();

        Map<String, BigDecimal> monthlyExpense =
                new LinkedHashMap<>();

        for (Month month : Month.values()) {

            String monthName = month.toString().substring(0, 1)
                    + month.toString().substring(1).toLowerCase();

            monthlyIncome.put(monthName, BigDecimal.ZERO);
            monthlyExpense.put(monthName, BigDecimal.ZERO);
        }

        // Add this user's income to the corresponding months
        for (Income income : incomes) {

            if (income.getIncomeDate() != null
                    && income.getAmount() != null) {

                String monthName = income.getIncomeDate()
                        .getMonth().toString().substring(0, 1)
                        + income.getIncomeDate().getMonth()
                        .toString().substring(1).toLowerCase();

                monthlyIncome.put(
                        monthName,
                        monthlyIncome.get(monthName).add(income.getAmount())
                );
            }
        }

        // Add this user's expenses to the corresponding months
        for (Expense expense : expenses) {

            if (expense.getExpenseDate() != null
                    && expense.getAmount() != null) {

                String monthName = expense.getExpenseDate()
                        .getMonth().toString().substring(0, 1)
                        + expense.getExpenseDate().getMonth()
                        .toString().substring(1).toLowerCase();

                monthlyExpense.put(
                        monthName,
                        monthlyExpense.get(monthName).add(expense.getAmount())
                );
            }
        }

        // Calculate expense category totals
        Map<String, BigDecimal> categoryTotals =
                new LinkedHashMap<>();

        for (Expense expense : expenses) {

            String category = expense.getCategory();

            if (category != null && !category.isEmpty()
                    && expense.getAmount() != null) {

                categoryTotals.put(
                        category,
                        categoryTotals.getOrDefault(
                                category, BigDecimal.ZERO
                        ).add(expense.getAmount())
                );
            }
        }

        // Send data to reports.html
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("balance", balance);
        model.addAttribute("monthlyIncome", monthlyIncome);
        model.addAttribute("monthlyExpense", monthlyExpense);
        model.addAttribute("categoryTotals", categoryTotals);

        model.addAttribute(
                "username",
                session.getAttribute("username")
        );

        return "reports";
    }
}

