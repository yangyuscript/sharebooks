package com.lin.sharebooks.mapper;

import com.lin.sharebooks.model.Message;
import com.lin.sharebooks.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper extends MyMapper<Message> {
    /**
     *找到所有向touserid用户发送信息的用户向其发送的最新message
     *@params:touserid
     *@return:List<Message>
     *@date: 21:54 2017/12/28
     **/
    @Select("select * from t_message m where m.mid in (select max(m2.mid) from t_message m2 where m2.touserid=#{touserid} group by m2.fromuserid) order by m.time desc")
    @Results({
            @Result(id=true,column="mid",property = "mid"),
            @Result(column = "fromuserid",property = "fromuserid"),
            @Result(column = "touserid",property = "touserid"),
            @Result(column = "content",property = "content"),
            @Result(column = "condi",property = "condi"),
            @Result(column = "time",property = "time"),
            @Result(column="fromuserid",property = "fromuser",
                    one=@One(select="com.lin.sharebooks.mapper.UserMapper.selectById")
            ),
            @Result(column = "touserid",property = "touser",
                    one=@One(select="com.lin.sharebooks.mapper.UserMapper.selectById")
            )
    })
    List<Message> findAllByTouserid(int touserid);
    /**
     *找到用户与用户之间的所有message（按发送时间排序）
     *@params:touserid,fromuserid
     *@return:List<Message>
     *@date: 21:54 2017/12/28
     **/
    @Select("select * from t_message where (touserid=#{touserid} and fromuserid=#{fromuserid}) or (touserid=#{fromuserid} and fromuserid=#{touserid}) order by time asc")
    @Results({
            @Result(id=true,column="mid",property = "mid"),
            @Result(column = "fromuserid",property = "fromuserid"),
            @Result(column = "touserid",property = "touserid"),
            @Result(column = "content",property = "content"),
            @Result(column = "condi",property = "condi"),
            @Result(column = "time",property = "time"),
            @Result(column="fromuserid",property = "fromuser",
                    one=@One(select="com.lin.sharebooks.mapper.UserMapper.selectById")
            ),
            @Result(column = "touserid",property = "touser",
                    one=@One(select="com.lin.sharebooks.mapper.UserMapper.selectById")
            )
    })
    List<Message> findAllByTouseridAndFromuserid(@Param("touserid") int touserid, @Param("fromuserid") int fromuserid);
    /**
     *获取小程序personal页面的初始数据（未读消息数及未读通知数）
     *@params:touserid
     *@return:
     *@date: 15:38 2018/2/25
     **/
    @Select("select count(*) from t_message where touserid=#{touserid} and condi=1")
    Integer getUserMessagesNotReadedNum(@Param("touserid")int touserid);
}
