package com.lin.sharebooks.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DataShow {
    private String name;
    private Object value;

    public DataShow(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
