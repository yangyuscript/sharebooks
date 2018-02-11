package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Message;

import java.util.List;

public interface MessageService {
    void addMessage(Message message);
    void deleteMessage(int mid);
    List<Message> findAllByTouserid(int touserid);
    List<Message> findAllByTouseridAndFromuserid(int touserid, int fromuserid);
}
