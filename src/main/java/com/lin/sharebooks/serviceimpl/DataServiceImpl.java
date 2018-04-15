package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.BookMapper;
import com.lin.sharebooks.mapper.BookTypeMapper;
import com.lin.sharebooks.mapper.UserMapper;
import com.lin.sharebooks.model.DataShow;
import com.lin.sharebooks.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DataServiceImpl implements DataService{
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private BookMapper bookMapper;
    @Override
    public Map<String, Object> getDataForWechat() {
        Map<String,Object> map= new HashMap<>();
        //获取注册用户男女人数

        List<DataShow> ds1=userMapper.selectNumByGender();
        map.put("opinionData",ds1);

        //获取用户所在地分布
        List<DataShow> ds2=userMapper.selectNumByProvince();
        map.put("opinionData2",ds2);

        //书籍种类分布
        List<DataShow> ds3=bookMapper.selectNumByBtid();
        map.put("opinionData3",ds3);
        return map;
    }
}
