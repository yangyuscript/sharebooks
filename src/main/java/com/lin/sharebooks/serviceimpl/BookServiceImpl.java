package com.lin.sharebooks.serviceimpl;

import com.lin.sharebooks.mapper.BookMapper;
import com.lin.sharebooks.mapper.LoginlogMapper;
import com.lin.sharebooks.model.Book;
import com.lin.sharebooks.model.BookWithDistance;
import com.lin.sharebooks.model.Loginlog;
import com.lin.sharebooks.service.BookService;
import com.lin.sharebooks.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired(required = false)
    private BookMapper bookMapper;
    @Autowired(required = false)
    private LoginlogMapper loginlogMapper;
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

    @Override
    public List<BookWithDistance> findCloseBookByKeyWordAndUserLocation(String bookname, int userid) {
        List<Book> books=findAllWithTerms(bookname,0,2,null);
        //找到小程序用户当前最新的经纬度
        Loginlog loginlog_user=loginlogMapper.selectByUserid(userid).get(0);
        double latitude_user= loginlog_user.getLatitude();
        double longitude_user= loginlog_user.getLongitude();
        //存放得到距离之后的书籍容器
        List<BookWithDistance> bookWithDistances = new ArrayList<>();
        if(books!=null&&!books.isEmpty()){
            for (int i=0,len=books.size();i<len;i++) {
                Book book =books.get(i);
                Loginlog loginlog_booker=loginlogMapper.selectByUserid(book.getUserid()).get(0);
                //获得关键字搜索出来的书籍的所在经纬度
                double latitude_booker=loginlog_booker.getLatitude();
                double longitude_booker=loginlog_booker.getLongitude();
                //计算用户与当前书籍的距离
                double distance = ResultMsg.getDistance(latitude_booker,longitude_booker,latitude_user,longitude_user);
                BookWithDistance bookWithDistance=new BookWithDistance();
                bookWithDistance.setBid(book.getBid());
                bookWithDistance.setUserid(book.getUserid());
                bookWithDistance.setBtid(book.getBtid());
                bookWithDistance.setBookname(book.getBookname());
                bookWithDistance.setBookpic(book.getBookpic());
                bookWithDistance.setDescription(book.getDescription());
                bookWithDistance.setCondi(book.getCondi());
                bookWithDistance.setTime(book.getTime());
                bookWithDistance.setLikenum(book.getLikenum());
                bookWithDistance.setUser(book.getUser());
                bookWithDistance.setBookType(book.getBookType());
                bookWithDistance.setDistance(distance);
                bookWithDistances.add(bookWithDistance);
            }
            Collections.sort(bookWithDistances, new Comparator<BookWithDistance>() {
                @Override
                public int compare(BookWithDistance u1, BookWithDistance u2) {
                    //升序
                    return new Double(u1.getDistance()).compareTo(new Double(u2.getDistance()));
                    //降序
                    // return new Double(u2.getSalary()).compareTo(new Double(u2.getSalary()));
                }
            });
        }else{
            return null;
        }
        return bookWithDistances;
    }
}
