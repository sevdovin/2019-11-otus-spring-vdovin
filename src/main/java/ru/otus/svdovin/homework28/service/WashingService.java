package ru.otus.svdovin.homework28.service;

import ru.otus.svdovin.homework28.domain.WorkDone;
import ru.otus.svdovin.homework28.domain.MenuItem;

public interface WashingService {

    WorkDone process(MenuItem menuItem);
}
