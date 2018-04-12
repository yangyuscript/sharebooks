package com.lin.sharebooks.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Accessors(chain=true)
@Table(name="t_runpic")
public class Runpic {
    @Id
    @Column(name="rid")
    private int rid;
    @Column(name="url")
    private String url;
    @Column(name="description")
    private String description;
    @Column(name="time")
    private String time;

    public Runpic(String url, String description, String time) {
        this.url = url;
        this.description = description;
        this.time = time;
    }
}
