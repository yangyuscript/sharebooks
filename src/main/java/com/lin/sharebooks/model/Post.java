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
@Table(name = "t_post")
public class Post {
    @Id
    @Column(name = "postid")
    private Integer postid;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "condi")
    private Integer condi;
    @Column(name = "time")
    private String time;

    public Post(String title, String content, Integer condi, String time) {
        this.title = title;
        this.content = content;
        this.condi = condi;
        this.time = time;
    }
}
