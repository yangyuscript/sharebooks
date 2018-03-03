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
@Table(name="t_user")
public class User {
    @Id
    @Column(name="userId")
    private int userId;
    @Column(name="email")
    private String email;
    @Column(name="nickname")
    private String nickname;
    @Column(name="password")
    private String password;
    @Column(name="head")
    private String head;
    @Column(name="credit")
    private int credit;
    @Column(name="condi")
    private int condi;
    @Column(name = "openid")
    private String openid;
    @Column(name = "gender")
    private String gender;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "country")
    private String country;
    @Column(name = "language")
    private String language;
    @Column(name = "time")
    private String time;

    public User(String email, String nickname, String password, String head, int credit, int condi, String openid, String gender, String city, String province, String country, String language, String time) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.head = head;
        this.credit = credit;
        this.condi = condi;
        this.openid = openid;
        this.gender = gender;
        this.city = city;
        this.province = province;
        this.country = country;
        this.language = language;
        this.time = time;
    }
}
