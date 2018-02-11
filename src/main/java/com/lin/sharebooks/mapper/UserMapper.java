package com.lin.sharebooks.mapper;

import com.lin.sharebooks.model.User;
import com.lin.sharebooks.util.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends MyMapper<User> {
    @Insert("insert into t_user (email,nickname,password,head,credit,condi,openid) values(#{email},#{nickname},#{password},#{head},#{credit},#{condi},#{openid})")
    void insertUser(User user);
    @Select("select * from t_user where userid=#{userid}")
    User selectById(@Param("userid") int userid);
    @Select("select * from t_user where nickname=#{nickname}")
    User selectByName(@Param("nickname") String nickname);
    @Select("select userid,email,nickname,password,head,credit,condi from t_user where email=#{email}")
    User selectByEmail(@Param("email") String email);
    @Select("select userid,email,nickname,password,head,credit,condi,openid from t_user where openid=#{openid}")
    User selectByOpenid(@Param("openid") String openid);
}
