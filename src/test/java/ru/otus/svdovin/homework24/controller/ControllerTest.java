package ru.otus.svdovin.homework24.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.svdovin.homework24.domain.Book;
import ru.otus.svdovin.homework24.domain.Genre;
import ru.otus.svdovin.homework24.service.AuthorProvider;
import ru.otus.svdovin.homework24.service.BookProvider;
import ru.otus.svdovin.homework24.service.GenreProvider;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер должен ")
@WebMvcTest(PageController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorProvider authorProvider;

    @MockBean
    private GenreProvider genreProvider;

    @MockBean
    private BookProvider bookProvider;

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("возвращать шаблон author-list.html при обращении за авторами")
    void shouldReturnAuthorWhenAccessing() throws Exception {
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(view().name("author-list"))
                .andExpect(content().string(containsString("authors.title")));
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("возвращать шаблон genre-list.html при обращении за жанрами")
    void shouldReturnGenreWhenAccessing() throws Exception {
        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(view().name("genre-list"))
                .andExpect(content().string(containsString("genres.title")));
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("возвращать шаблон book-list.html при обращении за книгами")
    void shouldReturnBookWhenAccessing() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-list"))
                .andExpect(content().string(containsString("books.title")));
    }

    @DisplayName("возвращать шаблон book-edit.html при редактировании книги")
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnBookEditWhenAccessing() throws Exception {
        Book book = new Book(1, "BookName", new Genre(1, "GenreName"));
        when(bookProvider.getBookById(1)).thenReturn(Optional.of(book));
        mockMvc.perform(get("/book-edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-edit"))
                .andExpect(content().string(containsString("book.edit.title")));
    }

    @DisplayName("возвращать ошибку доступа при редактировании книги не той ролью")
    @Test
    @WithMockUser(username = "user1", roles = "USER")
    void shouldReturnBookEditUserWhenAccessing() throws Exception {
        Book book = new Book(1, "BookName", new Genre(1, "GenreName"));
        when(bookProvider.getBookById(1)).thenReturn(Optional.of(book));
        mockMvc.perform(get("/book-edit?id=1"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("возвращать шаблон book-create.html при добавлении книги")
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnBookCreateWhenAccessing() throws Exception {
        mockMvc.perform(get("/book-create"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-create"))
                .andExpect(content().string(containsString("book.create.title")));
    }

    @DisplayName("возвращать ошибку доступа при добавлении книги не той ролью")
    @Test
    @WithMockUser(username = "user1", roles = "USER")
    void shouldReturnBookCreateUserWhenAccessing() throws Exception {
        mockMvc.perform(get("/book-create"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("возвращать шаблон book-edit.html при удалении книги")
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnBookDeleteWhenAccessing() throws Exception {
        Book book = new Book(1, "BookName", new Genre(1, "GenreName"));
        when(bookProvider.getBookById(1)).thenReturn(Optional.of(book));
        mockMvc.perform(get("/book-delete?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-delete"))
                .andExpect(content().string(containsString("book.delete.title")));
    }

    @DisplayName("возвращать ошибку доступа при удалении книги не той ролью")
    @Test
    @WithMockUser(username = "user1", roles = "USER")
    void shouldReturnBookDeleteUserWhenAccessing() throws Exception {
        Book book = new Book(1, "BookName", new Genre(1, "GenreName"));
        when(bookProvider.getBookById(1)).thenReturn(Optional.of(book));
        mockMvc.perform(get("/book-delete?id=1"))
                .andExpect(status().isForbidden());
    }
}
