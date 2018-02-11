package com.lin.sharebooks.mapper;

import com.lin.sharebooks.model.Position;
import com.lin.sharebooks.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PositionMapper extends MyMapper<Position> {
    @Select("select pid,userid,address,time from t_position where userid=#{userid} order by time desc")
    List<Position> findAllByUserid(@Param("userid") int userid);
}
