package com.petstore.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag {
    private long id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(long id) {
        this.id = id;
    }
}
