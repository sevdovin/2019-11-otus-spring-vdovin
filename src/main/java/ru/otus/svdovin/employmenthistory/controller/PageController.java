package ru.otus.svdovin.employmenthistory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/staff")
    public String staffPage() {
        return "staff";
    }

    @GetMapping("/employeeList")
    public String employeeListPage(Model model) {
        return "employeeList";
    }

    @GetMapping("/employeeEdit")
    public String employeeEditPage() {
        return "employeeEdit";
    }

    @GetMapping("/employeeDelete")
    public String employeeDeletePage() {
        return "employeeDelete";
    }

    @GetMapping("/employeeAdd")
    public String employeeAddPage() {
        return "employeeAdd";
    }

    @GetMapping("/empBookView")
    public String empBookViewPage() {
        return "empBookView";
    }

    @GetMapping("/empBookEdit")
    public String empBookEditPage() {
        return "empBookEdit";
    }

    @GetMapping("/empBookAdd")
    public String empBookAddPage() {
        return "empBookAdd";
    }

    @GetMapping("/empBookDelete")
    public String empBookDeletePage() {
        return "empBookDelete";
    }

    @GetMapping("/companyView")
    public String companyViewPage() {
        return "companyView";
    }

    @GetMapping("/companyEdit")
    public String companyEditPage() {
        return "companyEdit";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/client")
    public String clientPage() {
        return "client";
    }
}
