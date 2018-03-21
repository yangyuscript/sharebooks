package com.lin.sharebooks.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "t_notice")
public class Notice {
    @Id
    private int nid;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "condi")
    private int condi;
    @Column(name = "time")
    private String time;

    public Notice(String title, String content, int condi, String time) {
        this.title = title;
        this.content = content;
        this.condi = condi;
        this.time = time;
    }
}
