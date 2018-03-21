package com.lin.sharebooks.mapper;

import com.lin.sharebooks.model.BookType;
import com.lin.sharebooks.model.Post;
import com.lin.sharebooks.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper extends MyMapper<Post>{
    @Select("<script> "+
            "select * from t_post"+
            " <where> "+
            " <if test=\"title !=null\">title like concat('%',concat(#{title},'%'))</if>"+
            " <if test=\"time !=null\">and time like concat('%',concat(#{time},'%'))</if>"+
            " </where> "+
            " </script> "
    )
    @Results({
            @Result(id=true,column="postid",property = "postid"),
            @Result(column = "title",property = "title"),
            @Result(column = "content",property = "content"),
            @Result(column = "condi",property = "condi"),
            @Result(column = "time",property = "time")
    })
    List<Post> findAllWithTerms(@Param("title") String name, @Param("time") String time);
}
