package ru.otus.svdovin.homework16.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.homework16.domain.Author;
import ru.otus.svdovin.homework16.domain.Book;
import ru.otus.svdovin.homework16.domain.Genre;
import ru.otus.svdovin.homework16.service.AuthorProvider;
import ru.otus.svdovin.homework16.service.BookProvider;
import ru.otus.svdovin.homework16.service.GenreProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class PageController {

    private final AuthorProvider authorProvider;
    private final GenreProvider genreProvider;
    private final BookProvider bookProvider;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/authors")
    public String authorPage(Model model) {
        List<Author> authorList = authorProvider.getAuthorAll();
        model.addAttribute("authors", authorList);
        return "author-list";
    }

    @GetMapping("/genres")
    public String genrePage(Model model) {
        List<Genre> genreList = genreProvider.getGenreAll();
        model.addAttribute("genres", genreList);
        return "genre-list";
    }

    @GetMapping("/books")
    public String bookPage(Model model) {
        List<Book> bookList = bookProvider.getBookAll();
        model.addAttribute("books", bookList);
        return "book-list";
    }

    @GetMapping("/book-edit")
    public String editBook(@RequestParam(name = "id") Long bookId, Model model) {
        Book book = bookProvider.getBookById(bookId).orElse(null);
        populateModelWithBook(book, model);
        return "book-edit";
    }

    @PutMapping(value = "/books")
    public String editBookAction(Book book, Model model) {
        bookProvider.updateBook(book);
        List<Book> books = bookProvider.getBookAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    @PutMapping(params = "removeAuthor", path = {"/books", "/books/{id}"})
    public String removeAuthorEditBook(Book book, @RequestParam("removeAuthor") int index, Model model) {
        book.getAuthors().remove(index);
        populateModelWithBook(book, model);
        return "book-edit";
    }

    @PutMapping(params = "addAuthor", path = {"/books", "/books/{id}"})
    public String addAuthorEditBook(Book book, @RequestParam("authorId") int authorId, Model model) {
        Author author = authorProvider.getAuthorById(authorId).orElse(null);
        if (book.getAuthors() == null) {
            book.setAuthors(new ArrayList<Author>());
        }
        book.getAuthors().add(author);
        populateModelWithBook(book, model);
        return "book-edit";
    }

    private void populateModelWithBook(Book book, Model model) {
        model.addAttribute("book", book);
        model.addAttribute("genres", genreProvider.getGenreAll());
        model.addAttribute("filteredAuthors", authorProvider.getAuthorAll()
                .stream().filter(author -> !book.getAuthors().contains(author)).collect(Collectors.toList()));
    }

    @GetMapping("/book-create")
    public String createBook(Model model) {
        Genre genre = Genre.builder().genreName("").build();
        List<Author> authors = new ArrayList<>();
        Book book = Book.builder().bookName("").genre(genre).authors(authors).build();
        model.addAttribute("book", book);
        model.addAttribute("genres", genreProvider.getGenreAll());
        model.addAttribute("filteredAuthors", authorProvider.getAuthorAll());
        return "book-create";
    }

    @PostMapping(value = "/books")
    public String createBookAction(Book book, Model model) {
        bookProvider.createBook(book);
        List<Book> books = bookProvider.getBookAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    @PostMapping(params = "addAuthor", path = {"/books", "/books/{id}"})
    public String addAuthorCreateBook(Book book, @RequestParam("authorId") int authorId, Model model) {
        Author author = authorProvider.getAuthorById(authorId).orElse(null);
        if (book.getAuthors() == null) {
            book.setAuthors(new ArrayList<Author>());
        }
        book.getAuthors().add(author);
        populateModelWithBook(book, model);
        return "book-create";
    }

    @PostMapping(params = "removeAuthor", path = {"/books", "/books/{id}"})
    public String removeAuthorCreateBook(Book book, @RequestParam("removeAuthor") int index, Model model) {
        book.getAuthors().remove(index);
        populateModelWithBook(book, model);
        return "book-create";
    }

    @GetMapping("/book-delete")
    public String deleteBook(@RequestParam(name = "id") Long bookId, Model model) {
        Book book = bookProvider.getBookById(bookId).orElse(null);
        populateModelWithBook(book, model);
        return "book-delete";
    }

    @DeleteMapping(value = "/books")
    public String deleteBookAction(Book book, Model model) {
        bookProvider.deleteBookById(book.getBookId());
        List<Book> books = bookProvider.getBookAll();
        model.addAttribute("books", books);
        return "book-list";
    }


}
