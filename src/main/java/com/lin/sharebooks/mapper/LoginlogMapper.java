package com.lin.sharebooks.mapper;

import com.lin.sharebooks.model.Loginlog;
import com.lin.sharebooks.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LoginlogMapper extends MyMapper<Loginlog>{
    /**
     *根据userid找到用户最新登录地址
     *@params:userid
     *@return:Loginlog
     *@date: 19:27 2018/1/28
     **/
    @Select("select logid,userid,longitude,latitude,time from t_loginlog where userid=#{userid} order by time desc limit 1")
    @Results({
            @Result(id=true,column="logid",property = "logid"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "longitude",property = "longitude"),
            @Result(column = "latitude",property = "latitude"),
            @Result(column = "time",property = "time"),
            @Result(column="userid",property = "user",
                    one=@One(select="com.lin.sharebooks.mapper.UserMapper.selectById")
            )
    })
    List<Loginlog> selectByUserid(@Param("userid") int userid);
    /**
     *根据用户经纬度找到附近2km内的其他用户
     *@params:longitude、latitude
     *@return:List<Loginlog>
     *@date: 21:38 2018/2/5
     **/
    @Select("SELECT * FROM t_loginlog WHERE logid IN (SELECT MAX(logid) FROM t_loginlog WHERE longitude!=#{longitude} AND latitude!=#{latitude} AND longitude>(#{longitude}-1) AND longitude<(#{longitude}+1) AND latitude>(#{latitude}-1) AND latitude<(#{latitude}+1) AND SQRT((((#{longitude}-longitude)*PI()*12656*COS(((#{latitude}+latitude)/2)*PI()/180)/180)*((#{longitude}-longitude)*PI()*12656*COS(((#{latitude}+latitude)/2)*PI()/180)/180))+(((#{latitude}-latitude)*PI()*12656/180)*((#{latitude}-latitude)*PI()*12656/180)))<200 GROUP BY userid)")
    @Results({
            @Result(id=true,column="logid",property = "logid"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "longitude",property = "longitude"),
            @Result(column = "latitude",property = "latitude"),
            @Result(column = "time",property = "time"),
            @Result(column="userid",property = "user",
                    one=@One(select="com.lin.sharebooks.mapper.UserMapper.selectById")
            )
    })
    List<Loginlog> select2kmLoginlog(@Param("longitude")double longitude,@Param("latitude")double latitude);
}
