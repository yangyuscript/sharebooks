package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.BookMapper;
import com.lin.sharebooks.model.Book;
import com.lin.sharebooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired(required = false)
    private BookMapper bookMapper;
    @Override
    public void addBook(Book book) {
        bookMapper.insert(book);
    }

    @Override
    public void deleteBook(int bid) {
        bookMapper.deleteByBid(bid);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateByPrimaryKey(book);
    }

    @Override
    public Book getByBid(int bid) {
        return bookMapper.getBookByBid(bid);
    }

    @Override
    public List<Book> findRecomendBooks() {
        return bookMapper.selectRecomendBooks();
    }

    @Override
    public List<Book> findNewestBooks() {
        return bookMapper.selectNewestBooks();
    }

    @Override
    public List<Book> findAllWithTerms(String bookname,int btid,int condi,String time) {
        return bookMapper.selectAllWithTerms(bookname,btid,condi,time);
    }

    @Override
    public List<Book> findByUserid(int userid) {
        return bookMapper.findByUserid(userid);
    }
}
