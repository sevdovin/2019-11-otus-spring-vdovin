package ru.otus.svdovin.homework28.service;

import org.springframework.stereotype.Service;

import ru.otus.svdovin.homework28.domain.WorkDone;
import ru.otus.svdovin.homework28.domain.MenuItem;

@Service
public class WashingServiceImpl implements WashingService {

    @Override
    public WorkDone process(MenuItem menuItem) {
        return WorkDone.builder().name(menuItem.getName()).build();
    }

}
