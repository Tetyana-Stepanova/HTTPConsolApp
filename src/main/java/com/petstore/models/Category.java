package com.petstore.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {
    private long id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category(long id) {
        this.id = id;
    }
}
