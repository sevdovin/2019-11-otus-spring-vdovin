package ru.otus.svdovin.homework29.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class PageController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/authors")
    public String authorPage(Model model) {
        return "author-list";
    }

    @GetMapping("/genres")
    public String genrePage(Model model) {
        return "genre-list";
    }

    @GetMapping("/books")
    public String bookPage(Model model) {
        return "book-list";
    }

    @GetMapping("/book-edit/{id}")
    public String editBookPage(@PathVariable("id") String id) {
        return "book-edit";
    }

    @GetMapping("/book-create")
    public String createBookPage(Model model) {
        return "book-create";
    }

    @GetMapping("/book-delete/{id}")
    public String deleteBookPage(@PathVariable("id") String id) {
        return "book-delete";
    }
}
