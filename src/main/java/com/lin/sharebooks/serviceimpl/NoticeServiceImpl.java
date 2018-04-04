package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.NoticeMapper;
import com.lin.sharebooks.model.Notice;
import com.lin.sharebooks.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired(required = false)
    private NoticeMapper noticeMapper;
    @Override
    public void addNotice(Notice notice) {
        noticeMapper.insert(notice);
    }

    @Override
    public void deleteNotice(int nid) {
        noticeMapper.deleteByPrimaryKey(nid);
    }

    @Override
    public List<Notice> findAll() {
        return noticeMapper.selectAll();
    }

    @Override
    public List<Notice> findAllWithTerms(String title, String time) {
        return noticeMapper.findAllWithTerms(title,time);
    }

    @Override
    public Notice getByNid(int nid) {
        return noticeMapper.selectByPrimaryKey(nid);
    }
}
