package com.lin.sharebooks.mapper;

import com.lin.sharebooks.model.Runpic;
import com.lin.sharebooks.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RunpicMapper extends MyMapper<Runpic>{
    @Select("select rid,url,description,time from t_runpic order by time desc")
    public List<Runpic> findAll();

}
