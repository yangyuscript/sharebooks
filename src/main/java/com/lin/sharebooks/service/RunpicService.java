package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Runpic;

import java.util.List;

public interface RunpicService {
    void update(Runpic runpic);
    Runpic getByRid(int rid);
    List<Runpic> findAll();
}
