package com.lin.sharebooks.service;

import com.lin.sharebooks.model.BookType;

import java.util.List;

public interface BookTypeService {
    void addBookType(BookType bookType);
    void deleteByBtid(int btid);
    void updateBookType(BookType bookType);
    List<BookType> findAllWithTerms(String name, String time);
    List<BookType> findAll();
}
