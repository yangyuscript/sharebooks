package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.PositionMapper;
import com.lin.sharebooks.model.Position;
import com.lin.sharebooks.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired(required = false)
    private PositionMapper positionMapper;
    @Override
    public void addPosition(Position position) {
        positionMapper.insert(position);
    }

    @Override
    public void deletePosition(int pid) {
        positionMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public List<Position> findAllByUserid(int userid) {
        return positionMapper.findAllByUserid(userid);
    }
}
