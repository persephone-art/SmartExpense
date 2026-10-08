
        package com.smartexpense.controller;

import com.smartexpense.model.Income;
import com.smartexpense.model.Expense;
import com.smartexpense.repository.IncomeRepository;
import com.smartexpense.repository.ExpenseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
public class TaxController {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public TaxController(
            IncomeRepository incomeRepository,
            ExpenseRepository expenseRepository) {

        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/tax")
    public String taxPage(Model model, HttpSession session) {

        // Get the logged-in user's database ID
        Integer userId = (Integer) session.getAttribute("userId");

        // Redirect to login if nobody is logged in
        if (userId == null) {
            return "redirect:/";
        }

        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);
        int currentYear = today.getYear();

        // Retrieve only this user's saved records
        List<Income> incomes =
                incomeRepository.findByUserId(userId);

        List<Expense> expenses =
                expenseRepository.findByUserId(userId);

        BigDecimal monthlyIncome = BigDecimal.ZERO;
        BigDecimal yearToDateIncome = BigDecimal.ZERO;
        BigDecimal monthlyExpenses = BigDecimal.ZERO;

        // Calculate this month's income and this year's income
        for (Income item : incomes) {

            if (item.getIncomeDate() == null
                    || item.getAmount() == null) {
                continue;
            }

            LocalDate date = item.getIncomeDate();
            BigDecimal amount = item.getAmount();

            if (YearMonth.from(date).equals(currentMonth)) {
                monthlyIncome = monthlyIncome.add(amount);
            }

            if (date.getYear() == currentYear
                    && !date.isAfter(today)) {
                yearToDateIncome =
                        yearToDateIncome.add(amount);
            }
        }

        // Calculate this month's expenses
        for (Expense item : expenses) {

            if (item.getExpenseDate() == null
                    || item.getAmount() == null) {
                continue;
            }

            if (YearMonth.from(item.getExpenseDate())
                    .equals(currentMonth)) {

                monthlyExpenses =
                        monthlyExpenses.add(item.getAmount());
            }
        }

        // Estimate annual income from this month's income
        BigDecimal projectedAnnualIncome =
                monthlyIncome.multiply(BigDecimal.valueOf(12));

        // Simplified educational estimates, not final tax advice
        double annualIncome =
                projectedAnnualIncome.doubleValue();

        double oldTax = calculateOldRegimeTax(annualIncome);
        double newTax = calculateNewRegimeTax(annualIncome);

        String recommended;

        if (oldTax < newTax) {
            recommended = "Old Tax Regime";
        } else if (newTax < oldTax) {
            recommended = "New Tax Regime";
        } else {
            recommended = "Both have the same estimated tax";
        }

        double monthlyOldTax = oldTax / 12.0;
        double monthlyNewTax = newTax / 12.0;
        double annualSavings = Math.abs(oldTax - newTax);

        // Send values to tax.html
        model.addAttribute("monthlyIncome", monthlyIncome);
        model.addAttribute("yearToDateIncome", yearToDateIncome);
        model.addAttribute("monthlyExpenses", monthlyExpenses);
        model.addAttribute("projectedAnnualIncome",
                projectedAnnualIncome);

        model.addAttribute("oldTax", oldTax);
        model.addAttribute("newTax", newTax);
        model.addAttribute("monthlyOldTax", monthlyOldTax);
        model.addAttribute("monthlyNewTax", monthlyNewTax);
        model.addAttribute("annualSavings", annualSavings);
        model.addAttribute("recommended", recommended);

        model.addAttribute(
                "username",
                session.getAttribute("username")
        );

        model.addAttribute("taxDisclaimer",
                "Illustrative estimate only. Not a final tax calculation.");

        return "tax";
    }

    // Simplified estimates retained from your original code.
    // These are not authoritative calculations of Indian income tax.

    private double calculateOldRegimeTax(double income) {

        if (income <= 250000) return 0;
        if (income <= 500000)
            return (income - 250000) * 0.05;
        if (income <= 1000000)
            return 12500 + (income - 500000) * 0.20;

        return 112500 + (income - 1000000) * 0.30;
    }

    private double calculateNewRegimeTax(double income) {

        if (income <= 400000) return 0;
        if (income <= 800000)
            return (income - 400000) * 0.05;
        if (income <= 1200000)
            return 20000 + (income - 800000) * 0.10;
        if (income <= 1600000)
            return 60000 + (income - 1200000) * 0.15;
        if (income <= 2000000)
            return 120000 + (income - 1600000) * 0.20;
        if (income <= 2400000)
            return 200000 + (income - 2000000) * 0.25;

        return 300000 + (income - 2400000) * 0.30;
    }
}

