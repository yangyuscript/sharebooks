package com.lin.sharebooks.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
//使用链式设置属性，set方法返回的是this对象
@Accessors(chain=true)
public class BookWithDistance  extends Book{
    private double distance;
    private User user;
    private BookType bookType;

    public BookWithDistance(int userid, int btid, String bookname, String bookpic, String description, int condi, String time, int likenum,User user,BookType bookType, double distance) {
        super(userid, btid, bookname, bookpic, description, condi, time, likenum);
        this.distance = distance;
        this.user = user;
        this.bookType = bookType;
    }
}
