package ru.otus.svdovin.homework24.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import ru.otus.svdovin.homework24.service.BookProviderImpl;

@SpringBootTest
@DisplayName("Доступ к объектам через ACL")
public class AccessTest {
    
    @Autowired
    private BookProviderImpl bookProvider;
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void findAllBooksUsingAdminTest() {
        assertThat(bookProvider.getBookAll()).hasSize(6);
    }

    @Test
    @WithMockUser(roles = "ROMAN")
    public void findAllBooksUsingRomanUserTest() {
        assertThat(bookProvider.getBookAll()).hasSize(2);
    }

    @Test
    @WithMockUser(roles = "COMEDY")
    public void findAllBooksUsingComedyUserTest() {
        assertThat(bookProvider.getBookAll()).hasSize(1);
    }
    
    
}
