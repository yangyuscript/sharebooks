package com.lin.sharebooks.mapper;

import com.lin.sharebooks.model.DataShow;
import com.lin.sharebooks.model.User;
import com.lin.sharebooks.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends MyMapper<User> {
    @Select("select gender,count(*) as num from t_user group by gender")
    @Results({
            @Result(column = "gender", property = "name"),
            @Result(column = "num", property = "value")
    })
    List<DataShow> selectNumByGender();
    @Select("select province,count(*) as num from t_user group by province")
    @Results({
            @Result(column = "province", property = "name"),
            @Result(column = "num", property = "value")
    })
    List<DataShow> selectNumByProvince();
    @Select("select * from t_user where userid=#{userid}")
    User selectById(@Param("userid") int userid);
    @Select("select * from t_user where nickname=#{nickname}")
    User selectByName(@Param("nickname") String nickname);
    @Select("select userid,email,nickname,password,head,credit,condi from t_user where email=#{email}")
    User selectByEmail(@Param("email") String email);
    @Select("select userid,email,nickname,password,head,credit,condi,openid from t_user where openid=#{openid}")
    User selectByOpenid(@Param("openid") String openid);
    /**
     *模糊查询use
     *@params:nickname,condi,time
     *@return:List<User>
     *@date: 21:01 2017/12/21
     **/
    @Select("<script> "+
            "select * from t_user"+
            " <where> "+
            " <if test=\"nickname !=null\">nickname like concat('%',concat(#{nickname},'%'))</if>"+
            " <if test=\"condi != 3\">and condi = #{condi}</if>"+
            " <if test=\"time !=null\">and time like concat('%',concat(#{time},'%'))</if>"+
            " </where> "+
            " </script> "
    )
    @Results({
            @Result(id=true,column="userid",property = "userId"),
            @Result(column = "email",property = "email"),
            @Result(column = "nickname",property = "nickname"),
            @Result(column = "password",property = "password"),
            @Result(column = "head",property = "head"),
            @Result(column = "credit",property = "credit"),
            @Result(column = "condi",property = "condi"),
            @Result(column = "openid",property = "openid"),
            @Result(column = "gender",property = "gender"),
            @Result(column="city",property = "city"),
            @Result(column="province",property = "province"),
            @Result(column="country",property = "country"),
            @Result(column="language",property = "language"),
            @Result(column="time",property = "time")
    })
    List<User> selectAllWithTerms(@Param("nickname") String bookname,@Param("condi") int condi, @Param("time") String time);
}
