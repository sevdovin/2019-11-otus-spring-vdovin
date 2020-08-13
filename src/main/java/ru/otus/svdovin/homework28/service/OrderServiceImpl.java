package ru.otus.svdovin.homework28.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jline.reader.LineReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ru.otus.svdovin.homework28.domain.MenuItem;
import ru.otus.svdovin.homework28.domain.WorkDone;
import ru.otus.svdovin.homework28.integration.CarWashingGateway;

@Service
public class OrderServiceImpl implements OrderService {

    private final MenuService menuService;

    private final CarWashingGateway gateway;

    private final LineReader reader;

    public OrderServiceImpl(MenuService menuService, CarWashingGateway gateway, @Lazy LineReader reader) {
        this.menuService = menuService;
        this.gateway = gateway;
        this.reader = reader;
    }

    @Override
    public void processOrder() {
        System.out.println("Перечень оказываемых услуг: ");
        menuService.getMenu()
                .forEach(item -> System.out.println("id = " +
                        item.getId() +
                        " name = " +
                        item.getName()));
        String orderedWorks = reader
                .readLine("Укажите необходимые виды работ по их идентификаторам через пробел, например 1 2 5): ");

        if (orderedWorks.isEmpty()) {
            System.out.println("Вы не выбрали ни одного вида работ!");
            return;
        }
        if (!orderedWorks.matches("[\\d ]*")) {
            System.out.println("Можно вводить только цифры и пробелы!");
            return;
        }

        List<MenuItem> items = Arrays.<String>stream(orderedWorks.split(" "))
                .map(s -> menuService.findById(Integer.valueOf(s)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (items.isEmpty()) {
            System.out.println("Вы не выбрали ни одного вида работ!");
            return;
        }

        List<WorkDone> works = gateway.processOrder(items);
        System.out.println("Выполнены работы:");
        works.forEach(System.out::println);
        System.out.println("Ваш автомобиль готов, счатливого пути!");
    }
}
