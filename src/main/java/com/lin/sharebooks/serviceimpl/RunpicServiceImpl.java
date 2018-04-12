package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.RunpicMapper;
import com.lin.sharebooks.model.Runpic;
import com.lin.sharebooks.service.RunpicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunpicServiceImpl implements RunpicService {
    @Autowired(required = false)
    private RunpicMapper runpicMapper;
    @Override
    public void update(Runpic runpic) {
        runpicMapper.updateByPrimaryKey(runpic);
    }

    @Override
    public Runpic getByRid(int rid) {
        return runpicMapper.selectByPrimaryKey(rid);
    }

    @Override
    public List<Runpic> findAll() {
        return runpicMapper.findAll();
    }
}
