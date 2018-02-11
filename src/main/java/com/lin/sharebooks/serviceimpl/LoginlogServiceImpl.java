package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.LoginlogMapper;
import com.lin.sharebooks.model.Loginlog;
import com.lin.sharebooks.service.LoginlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginlogServiceImpl implements LoginlogService{
    @Autowired(required = false)
    private LoginlogMapper loginlogMapper;

    @Override
    public void addLoginlog(Loginlog loginlog) {
        loginlogMapper.insert(loginlog);
    }

    @Override
    public void updateLoginlog(Loginlog loginlog) {
        loginlogMapper.updateByPrimaryKey(loginlog);
    }

    @Override
    public List<Loginlog> getByUserid(int userid) {
        return loginlogMapper.selectByUserid(userid);
    }

    @Override
    public Loginlog getByLogid(int logid) {
        return loginlogMapper.selectByPrimaryKey(logid);
    }

    @Override
    public List<Loginlog> find2kmLoginlogs(double longitude, double latitude) {
        return loginlogMapper.select2kmLoginlog(longitude,latitude);
    }
}
