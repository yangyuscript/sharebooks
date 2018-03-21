package com.lin.sharebooks.mapper;

import com.lin.sharebooks.model.Notice;
import com.lin.sharebooks.model.Post;
import com.lin.sharebooks.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper extends MyMapper<Notice>{
    @Select("<script> "+
            "select * from t_notice"+
            " <where> "+
            " <if test=\"title !=null\">title like concat('%',concat(#{title},'%'))</if>"+
            " <if test=\"time !=null\">and time like concat('%',concat(#{time},'%'))</if>"+
            " </where> "+
            " </script> "
    )
    @Results({
            @Result(id=true,column="nid",property = "nid"),
            @Result(column = "title",property = "title"),
            @Result(column = "content",property = "content"),
            @Result(column = "condi",property = "condi"),
            @Result(column = "time",property = "time")
    })
    List<Notice> findAllWithTerms(@Param("title") String title, @Param("time") String time);
}
