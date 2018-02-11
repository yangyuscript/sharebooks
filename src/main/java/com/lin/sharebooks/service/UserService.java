package com.lin.sharebooks.service;

import com.lin.sharebooks.model.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    public void addUser(User user);

    /**
     * 判断注册邮箱是否已被注册
     * @param email
     * @return
     */
    public boolean isUsedEmail(String email);

    /**
     * 判断用户登录时的账号和密码是否正确
     * @param email
     * @param password
     * @return
     */
    public User checkEmailAndPassword(String email, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    /**
     *获得所有用户信息
     *@params:
     *@return:List
     *@date: 18:24 2017/12/11
     **/
    public List<User> findAll();

    /**
     *通过openid获取用户信息
     *@params:openid
     *@return:
     *@date: 20:14 2018/1/27
     **/
    public User getByOpenid(String openid);

    /**
     *根据用户id找到基本信息
     *@params:userid
     *@return:User
     *@date: 20:17 2018/2/8
     **/
    public User getByUserid(int userid);
    
    /**
     *更新用户数据
     *@params:user
     *@return:null
     *@date: 22:16 2018/2/8
     **/
    public void update(User user);
}
