package com.lin.sharebooks;

import com.lin.sharebooks.mapper.BookMapper;
import com.lin.sharebooks.mapper.MessageMapper;
import com.lin.sharebooks.mapper.UserMapper;

import com.lin.sharebooks.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.lin.appapidemo.mapper")
public class UserMapperTest {
    @Autowired(required = false)
    private UserMapper mapper;
    @Autowired(required = false)
    private BookMapper bookMapper;
    @Autowired(required = false)
    private MessageMapper messageMapper;
    @Test
    public void testInsert(){
//        User user=new User("lin2","123","123","123",123 ,1);
//        int i=mapper.insert(user);
        //bookMapper.insert(new Book(1,"testbook","test.jpg","test",1, DateTimeUtil.getDate()));
        //readerMapper.insert(new Reader("B14041526","123","林光翔","男",DateTimeUtil.getDate(),0));
        //albumMapper.insert(new Album("测试书名","测试作者","工业出版社",DateTimeUtil.getDate(),0,"测试描述",DateTimeUtil.getDate()));
        //subalbumMapper.insert(new Subalbum(1,DateTimeUtil.getDateNumber(),0,DateTimeUtil.getDate()));
        //borrowrecordMapper.insert(new Borrowrecord(1,"B14041526",1,1,DateTimeUtil.getDate(),DateTimeUtil.getDateNumber()));
    }
    @Test
    public void testSelect(){
        //User user=mapper.selectByName("test@qq.com");
        //System.out.println(user.getNickname());
        //System.out.println(readerMapper.selectByAccount("B"));
        System.out.println(messageMapper.getUserMessagesNotReadedNum(32));
    }
}
