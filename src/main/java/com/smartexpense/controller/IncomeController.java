
        package com.smartexpense.controller;

import com.smartexpense.model.Income;
import com.smartexpense.repository.IncomeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IncomeController {

    private final IncomeRepository incomeRepository;

    public IncomeController(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    // Display Add Income page
    @GetMapping("/income")
    public String showIncomePage(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/";
        }

        return "income";
    }

    // Save income for the logged-in user
    @PostMapping("/income")
    public String addIncome(
            @ModelAttribute Income income,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/";
        }

        // Associate this income with the logged-in user
        income.setUserId(userId);

        incomeRepository.save(income);

        redirectAttributes.addFlashAttribute(
                "message", "Income added successfully!");

        return "redirect:/income";
    }
}

