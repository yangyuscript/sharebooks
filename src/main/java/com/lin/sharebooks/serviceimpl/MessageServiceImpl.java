package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.MessageMapper;
import com.lin.sharebooks.model.Message;
import com.lin.sharebooks.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired(required = false)
    private MessageMapper messageMapper;
    @Override
    public void addMessage(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public void deleteMessage(int mid) {
        messageMapper.deleteByPrimaryKey(mid);
    }

    @Override
    public List<Message> findAllByTouserid(int touserid) {
        return messageMapper.findAllByTouserid(touserid);
    }

    @Override
    public List<Message> findAllByTouseridAndFromuserid(int touserid, int fromuserid) {
        return messageMapper.findAllByTouseridAndFromuserid(touserid,fromuserid);
    }

    @Override
    public int getPersonalIniaData(int touserid) {
        return messageMapper.getUserMessagesNotReadedNum(touserid);
    }

    @Override
    public List<Message> findAllNotReadedBetween(int touserid, int fromuserid) {
        return messageMapper.findAllNotReadedNewsBetween(touserid,fromuserid);
    }

    @Override
    public void updateMessage(Message message) {
        messageMapper.updateByPrimaryKey(message);
    }
}
