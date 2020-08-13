package ru.otus.svdovin.homework28.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.svdovin.homework28.domain.MenuItem;

@Service
public class MenuServiceImpl implements MenuService {

    private static final List<MenuItem> MENU_ITEMS = Arrays.asList(
            MenuItem.builder().id(1).name("Мойка кузова").build(),
            MenuItem.builder().id(2).name("Чистка салона").build(),
            MenuItem.builder().id(3).name("Чистка багажника").build(),
            MenuItem.builder().id(4).name("Мойка двигателя").build(),
            MenuItem.builder().id(5).name("Чистка ковриков").build(),
            MenuItem.builder().id(6).name("Чернение шин").build());

    @Override
    public List<MenuItem> getMenu() {
        return MENU_ITEMS;
    }

    @Override
    public MenuItem findById(Integer id) {
        return MENU_ITEMS.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }
}
