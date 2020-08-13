package ru.otus.svdovin.homework28.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MenuItem {

    private Integer id;
    
    private String name;
}
