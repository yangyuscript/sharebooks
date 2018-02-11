package com.lin.sharebooks.service;

import com.lin.sharebooks.model.Book;

import java.util.List;

public interface BookService {
    void addBook(Book book);
    void deleteBook(int bid);
    void updateBook(Book book);
    Book getByBid(int bid);
    List<Book> findRecomendBooks();
    List<Book> findNewestBooks();
    /**
     *根据书名、书籍种类、状态、发布时间模糊查找书籍
     *@params:bookname,btid,condi,time
     *@return:List<Book>
     *@date: 10:09 2017/12/28
     **/
    List<Book> findAllWithTerms(String bookname, int btid, int condi, String time);
    /**
     *根据用户id找到用户发布的书籍
     *@params:userid
     *@return:List<Book>
     *@date: 20:20 2018/2/8
     **/
    List<Book> findByUserid(int userid);
}
