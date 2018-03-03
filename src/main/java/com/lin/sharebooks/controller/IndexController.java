package com.lin.sharebooks.controller;

import com.github.pagehelper.PageHelper;
import com.lin.sharebooks.model.Book;
import com.lin.sharebooks.model.User;
import com.lin.sharebooks.service.BookService;
import com.lin.sharebooks.service.BookTypeService;
import com.lin.sharebooks.service.UserService;
import com.lin.sharebooks.util.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookTypeService bookTypeService;
    @Autowired
    private BookService bookService;
    @ApiOperation(value="流书api首页",notes="")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String index(){
        return "hello world!s";
    }

    @ApiOperation(value="获取所有书籍种类",notes="获取所有书籍种类")
    @RequestMapping(value="/getBookTypes",method = RequestMethod.GET)
    public Map<String,Object> getBookTypes(){
        Map<String,Object> map=new HashMap();
        map.put("bookTypes",bookTypeService.findAll());
        return map;
    }
    /**
     *微信小程序加载首页所需数据，附近热门书籍与推荐书籍
     *@params:token
     *@return:
     *@date: 19:51 2018/1/28
     **/
    @ApiOperation(value="微信小程序",notes = "加载首页所需数据，附近热门书籍与推荐书籍")
    @RequestMapping(value="/getInitialData",method = RequestMethod.GET)
    public Map<String,Object> getInitialData(){
        Map map=new HashMap();
        PageHelper.startPage(ResultMsg.CURRENTPAGE,5);
        List<Book> recomendBooks=bookService.findRecomendBooks();
        PageHelper.startPage(ResultMsg.CURRENTPAGE,5);
        List<Book> newestBooks=bookService.findNewestBooks();
        map.put("recomendBooks",recomendBooks);
        map.put("newestBooks",newestBooks);
        return map;
    }
    /**
     *微信小程序用户关键词查看书籍
     *@params:currPage,searchKey
     *@return:Map
     *@date: 21:29 2018/2/28
     **/
    @ApiOperation(value="微信小程序",notes = "关键词查看书籍")
    @RequestMapping(value="/searchBooks",method = RequestMethod.GET)
    public Map<String,Object> searchBooks(@RequestParam("searchKey")String searchKey,@RequestParam("currPage")String currPage){
        Map map=new HashMap();
        PageHelper.startPage(Integer.valueOf(currPage),ResultMsg.PAGESIZE);
        List<Book> searchBooks=bookService.findAllWithTerms(searchKey,0,2,null);
        map.put("searchBooks",searchBooks);
        map.put("result",ResultMsg.OK);
        return map;
    }
}
