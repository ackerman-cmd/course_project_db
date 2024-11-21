package org.spring_boot.course.project_db.controller;

import org.spring_boot.course.project_db.model.User;
import org.spring_boot.course.project_db.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Показать форму для регистрации
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // Создаем объект User для формы
        return "register"; // Название Thymeleaf шаблона для отображения формы
    }

    // Обработка формы регистрации
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.register(user); // Вызов регистрации
            model.addAttribute("message", "Пользователь успешно зарегистрирован");
            return "login"; // Перенаправление на страницу входа или вывод сообщения
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage()); // Отображаем ошибку
            return "register"; // Возврат на форму регистрации с ошибкой
        }
    }

    // Показать форму для входа
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Название Thymeleaf шаблона для формы входа
    }

    // Обработка формы входа
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model) {
        try {
            userService.login(user); // Вход пользователя
            return "home"; // Перенаправление на домашнюю страницу
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage()); // Отображение ошибки
            return "login"; // Возврат на форму входа с ошибкой
        }
    }
}
