package ru.otus.svdovin.homework29.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.svdovin.homework29.domain.Author;
import ru.otus.svdovin.homework29.domain.Book;
import ru.otus.svdovin.homework29.domain.Genre;
import ru.otus.svdovin.homework29.dto.BookDto;
import ru.otus.svdovin.homework29.service.BookProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebMvcTest({BookController.class})
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookProvider bookProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final long TEST_AUTHOR_ID = 1;
    private static final String TEST_AUTHOR_NAME = "Author 1";
    private static final long TEST_GENRE_ID = 1;
    private static final String TEST_GENRE_NAME = "Genre 1";
    private static final String NEW_BOOK_NAME = "Вася";

    @Test
    public void getBookAllTest() throws Exception {
        List<Author> list = Collections.singletonList(new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME));
        val genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(0, NEW_BOOK_NAME, genre, list);
        List<BookDto> bookList = new ArrayList<>();
        bookList.add(BookDto.buildDTO(book));
        when(bookProvider.getBookAll()).thenReturn(bookList);
        String expected = objectMapper.writeValueAsString(bookList.stream().collect(Collectors.toList()));
        MvcResult result = this.mvc.perform(get("/api/v1/book").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(expected,result.getResponse().getContentAsString());
        verify(bookProvider, times(1)).getBookAll();
    }

    @Test
    public void getBookByIdTest() throws Exception {
        List<Author> list = Collections.singletonList(new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME));
        val genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(0, NEW_BOOK_NAME, genre, list);
        when(bookProvider.getBookById(1)).thenReturn(Optional.of(book));
        String expected = objectMapper.writeValueAsString(book);
        MvcResult result = this.mvc.perform(get("/api/v1/book/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(expected,result.getResponse().getContentAsString());
        verify(bookProvider, times(1)).getBookById(1);
    }

    @Test
    public void updateBookTest() throws Exception {
        List<Author> list = Collections.singletonList(new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME));
        val genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(0, NEW_BOOK_NAME, genre, list);
        when(bookProvider.updateBook(book)).thenReturn(book);
        this.mvc.perform(put("/api/v1/book")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookProvider, times(1)).updateBook(book);
    }

    @Test
    public void createBookTest() throws Exception {
        List<Author> list = Collections.singletonList(new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME));
        val genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(0, NEW_BOOK_NAME, genre, list);
        when(bookProvider.createBook(book)).thenReturn(1L);
        this.mvc.perform(post("/api/v1/book")
                .content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookProvider, times(1)).createBook(book);
    }

    @Test
    public void deleteBookByIdTest() throws Exception {
        doNothing().when(bookProvider).deleteBookById(1);
        this.mvc.perform(delete("/api/v1/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));
        verify(bookProvider, times(1)).deleteBookById(1);
    }
}
