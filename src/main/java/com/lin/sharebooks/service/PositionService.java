package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Position;

import java.util.List;

public interface PositionService {
    void addPosition(Position position);
    void deletePosition(int pid);
    List<Position> findAllByUserid(int userid);
}
