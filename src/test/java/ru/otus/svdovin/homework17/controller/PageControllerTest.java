package ru.otus.svdovin.homework17.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebMvcTest({PageController.class})
public class PageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void authorPageTest() throws Exception {
        this.mvc.perform(get("/authors"))
                .andExpect(status().isOk());
    }

    @Test
    public void genrePageTest() throws Exception {
        this.mvc.perform(get("/genres"))
                .andExpect(status().isOk());
    }

    @Test
    public void bookPageTest() throws Exception {
        this.mvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void editBookPageTest() throws Exception {
        this.mvc.perform(get("/book-edit/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void createBookPageTest() throws Exception {
        this.mvc.perform(get("/book-create"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookPageTest() throws Exception {
        this.mvc.perform(get("/book-delete/1"))
                .andExpect(status().isOk());
    }
}
