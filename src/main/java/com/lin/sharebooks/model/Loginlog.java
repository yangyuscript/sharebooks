package com.lin.sharebooks.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "t_loginlog")
public class Loginlog {
    @Id
    @Column(name = "logid")
    private int logid;
    @Column(name = "userid")
    private int userid;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "time")
    private String time;
    @Transient
    private User user;

    public Loginlog(int userid, double longitude, double latitude, String time) {
        this.userid = userid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }
}
