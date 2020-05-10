package ru.otus.svdovin.employmenthistory.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.otus.svdovin.employmenthistory.EmploymentHistoryApplication;

@SpringBootTest
public class EmploymentHistoryApplicationTest {

    @Test
    public void starterTest() throws IOException {
        EmploymentHistoryApplication.main(new String[] {});
        assertTrue(true);
    }
    
}
