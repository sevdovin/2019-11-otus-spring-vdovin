package ru.otus.svdovin.homework17.controller;

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
import ru.otus.svdovin.homework17.domain.Genre;
import ru.otus.svdovin.homework17.service.GenreProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebMvcTest({GenreController.class})
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreProvider genreProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String GENRE_NEW_NAME = "New Genre";

    @Test
    public void getGenreAllTest() throws Exception {
        val genre = new Genre(0, GENRE_NEW_NAME);
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre);
        when(genreProvider.getGenreAll()).thenReturn(genreList);
        String expected = objectMapper.writeValueAsString(genreList.stream().collect(Collectors.toList()));
        MvcResult result = this.mvc.perform(get("/api/v1/genre").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(expected,result.getResponse().getContentAsString());
        verify(genreProvider, times(1)).getGenreAll();
    }
}
