package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {
    void addMessage(Message message);
    void deleteMessage(int mid);
    List<Message> findAllByTouserid(int touserid);
    List<Message> findAllByTouseridAndFromuserid(int touserid, int fromuserid);
    /**
     *获取小程序personal页面所需的初始化数据(用户未读消息)
     *@params:touserid
     *@return:Integer
     *@date: 15:40 2018/2/25
     **/
    public int getPersonalIniaData(int touserid);
}
