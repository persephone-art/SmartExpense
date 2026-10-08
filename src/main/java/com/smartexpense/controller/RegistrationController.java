
package com.smartexpense.controller;

import com.smartexpense.model.User;
import com.smartexpense.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes) {

        name = name.trim();
        email = email.trim().toLowerCase();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Please fill in all fields.");
            return "register";
        }

        if (password.length() < 8) {
            model.addAttribute(
                    "error",
                    "Password must contain at least 8 characters."
            );
            return "register";
        }

        if (userRepository.existsByEmail(email)) {
            model.addAttribute(
                    "error",
                    "This email is already registered. Please log in."
            );
            return "register";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
                "success",
                "Account created successfully! Please log in."
        );

        return "redirect:/";
    }
}
