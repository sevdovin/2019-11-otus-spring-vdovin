package ru.otus.svdovin.homework31.controller;

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
import ru.otus.svdovin.homework31.domain.Author;
import ru.otus.svdovin.homework31.service.AuthorProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebMvcTest({AuthorController.class})
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorProvider authorProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String AUTHOR_NEW_NAME = "New Author";

    @Test
    public void getAuthorAllTest() throws Exception {
        val author = new Author(0, AUTHOR_NEW_NAME);
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);
        when(authorProvider.getAuthorAll()).thenReturn(authorList);
        String expected = objectMapper.writeValueAsString(authorList.stream().collect(Collectors.toList()));
        MvcResult result = this.mvc.perform(get("/api/v1/author").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(expected,result.getResponse().getContentAsString());
        verify(authorProvider, times(1)).getAuthorAll();
    }
}
