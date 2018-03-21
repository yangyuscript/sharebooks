package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Notice;

import java.util.List;

public interface NoticeService {
    void addNotice(Notice notice);
    void deleteNotice(int nid);
    List<Notice> findAll();
    List<Notice> findAllWithTerms(String title,String time);
}
