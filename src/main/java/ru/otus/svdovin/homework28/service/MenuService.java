package ru.otus.svdovin.homework28.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.svdovin.homework28.domain.MenuItem;

@Service
public interface MenuService {
    
    List<MenuItem> getMenu();
    
    MenuItem findById(Integer id);

}
