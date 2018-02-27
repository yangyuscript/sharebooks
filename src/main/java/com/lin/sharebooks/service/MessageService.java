package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {
    void addMessage(Message message);
    void deleteMessage(int mid);
    void updateMessage(Message message);
    List<Message> findAllByTouserid(int touserid);
    List<Message> findAllByTouseridAndFromuserid(int touserid, int fromuserid);
    /**
     *获取小程序personal页面所需的初始化数据(用户未读消息)
     *@params:touserid
     *@return:Integer
     *@date: 15:40 2018/2/25
     **/
    int getPersonalIniaData(int touserid);
    /**
     *找到两用户间所有未读信息
     *@params:touserid,fromuserid
     *@return:List<Message>
     *@date: 19:37 2018/2/27
     **/
    List<Message> findAllNotReadedBetween(int touserid,int fromuserid);
}
