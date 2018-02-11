package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Loginlog;

import java.util.List;

public interface LoginlogService {
    void addLoginlog(Loginlog loginlog);
    void updateLoginlog(Loginlog loginlog);
    Loginlog getByLogid(int logid);
    List<Loginlog> getByUserid(int userid);
    List<Loginlog> find2kmLoginlogs(double longitude,double latitude);
}
