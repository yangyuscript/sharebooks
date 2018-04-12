package com.lin.sharebooks.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.sharebooks.model.*;
import com.lin.sharebooks.service.*;
import com.lin.sharebooks.util.DateTimeUtil;
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
@RequestMapping(value="/super")
public class SuperController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookTypeService bookTypeService;
    @Autowired
    private PostService postService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private RunpicService runpicService;
    /**
     *管理员获得所有用户信息
     *@params:
     *@return:List
     *@date: 18:27 2017/12/11
     **/
    @ApiOperation(value = "获得所有用户信息",notes = "管理员权限,一页默认10个记录")
    @RequestMapping(value="/getUserInfoByPage",method = RequestMethod.POST)
    public Map<String,Object> getUserInfoByPage(@RequestParam("nickname")String nickname,@RequestParam("condi")String condi,@RequestParam("time")String time,@RequestParam("currentPage")String currentPage){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(Integer.valueOf(currentPage),ResultMsg.PAGESIZE);
        List<User> list=userService.findUsersWithTerms(nickname,Integer.valueOf(condi),time);
        PageInfo<User> pageInfo=new PageInfo<>(list);
        map.put("users",list);
        map.put("pageInfo",pageInfo);
        map.put("status", ResultMsg.OK);
        return map;
    }

    /**
     *管理员获取所有书籍（模糊查询，分页)
     *@params:bookname,condi,time
     *@return:
     *@date: 18:34 2018/3/3
     **/
    @ApiOperation(value = "管理员获取所有书籍（分页)",notes = "管理员权限,一页默认10个记录")
    @RequestMapping(value="/getBookInfoByPage",method = RequestMethod.POST)
    public Map<String,Object> getBookInfoByPage(@RequestParam("bookname")String bookname,@RequestParam("condi")String condi,@RequestParam("time")String time,@RequestParam("currentPage")String currentPage){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(Integer.valueOf(currentPage),ResultMsg.PAGESIZE);
        List<Book> list=bookService.findAllWithTerms(bookname,0,2,time);
        PageInfo<Book> pageInfo=new PageInfo<>(list);
        map.put("books",list);
        map.put("pageInfo",pageInfo);
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *管理员获取书籍（模糊查询，分页）
     *@params:name,currentPage
     *@return:List<Booktype>
     *@date: 19:58 2018/3/3
     **/
    @ApiOperation(value = "管理员获取书籍（模糊查询，分页）",notes = "管理员权限,一页默认10个记录")
    @RequestMapping(value="/getAllBookTypes",method = RequestMethod.POST)
    public Map<String,Object> getAllBookTypes(@RequestParam("name")String name,@RequestParam("currentPage")String currentPage){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(Integer.valueOf(currentPage),ResultMsg.PAGESIZE);
        List<BookType> list=bookTypeService.findAllWithTerms(name,null);
        PageInfo<BookType> pageInfo=new PageInfo<>(list);
        map.put("bookTypes",list);
        map.put("pageInfo",pageInfo);
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *管理员新增书籍种类
     *@params:btname
     *@return:Map
     *@date: 20:10 2018/3/3
     **/
    @ApiOperation(value = "管理员新增书籍种类",notes = "管理员新增书籍种类")
    @RequestMapping(value="/addBookType",method = RequestMethod.POST)
    public Map<String,Object> addBookType(@RequestParam("btname")String btname){
        Map<String,Object> map=new HashMap<>();
        bookTypeService.addBookType(new BookType(btname, DateTimeUtil.getDate()));
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *管理员删除书籍种类
     *@params:btid
     *@return:map
     *@date: 20:25 2018/3/3
     **/
    @ApiOperation(value = "管理员删除书籍种类",notes = "管理员删除书籍种类")
    @RequestMapping(value="/delBookType",method = RequestMethod.POST)
    public Map<String,Object> delBookType(@RequestParam("btid")String btid){
        Map<String,Object> map=new HashMap<>();
        bookTypeService.deleteByBtid(Integer.valueOf(btid));
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *管理员获取帖子（根据帖子标题模糊查询）
     *@params:title,currentPage
     *@return:Map
     *@date: 22:22 2018/3/7
     **/
    @ApiOperation(value = "管理员获取帖子（根据帖子标题模糊查询）",notes = "管理员权限,一页默认10个记录")
    @RequestMapping(value="/getAllPosts",method = RequestMethod.POST)
    public Map<String,Object> getAllPosts(@RequestParam("title")String title,@RequestParam("currentPage")String currentPage) throws Exception {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(Integer.valueOf(currentPage),ResultMsg.PAGESIZE);
        List<Post> list=postService.findAllWithTerms(title,null);
        PageInfo<Post> pageInfo=new PageInfo<>(list);
        map.put("posts",list);
        map.put("pageInfo",pageInfo);
        map.put("status", ResultMsg.OK);
        return map;
    }

    /**
     *管理员新增帖子
     *@params:title,content
     *@return:Map
     *@date: 20:10 2018/3/3
     **/
    @ApiOperation(value = "管理员新增帖子",notes = "管理员新增帖子")
    @RequestMapping(value="/addPost",method = RequestMethod.POST)
    public Map<String,Object> addPost(@RequestParam("title")String title,@RequestParam("content")String content) throws Exception {
        Map<String,Object> map=new HashMap<>();
        postService.addPost(new Post(title,content,0,DateTimeUtil.getDate()));
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *管理员删除帖子
     *@params:postid
     *@return:
     *@date: 22:41 2018/3/7
     **/
    @ApiOperation(value = "管理员删除帖子",notes = "管理员删除帖子")
    @RequestMapping(value="/delPost",method = RequestMethod.POST)
    public Map<String,Object> delPost(@RequestParam("postid")String postid) throws Exception {
        Map<String,Object> map=new HashMap<>();
        postService.deletePost(Integer.parseInt(postid));
        map.put("status", ResultMsg.OK);
        return map;
    }

    /**
     *管理员获取通知（根据通知标题模糊查询）
     *@params:title,currentPage
     *@return:Map
     *@date: 22:22 2018/3/7
     **/
    @ApiOperation(value = "管理员获取通知（根据通知标题模糊查询）",notes = "管理员权限,一页默认10个记录")
    @RequestMapping(value="/getAllNotices",method = RequestMethod.POST)
    public Map<String,Object> getAllNotices(@RequestParam("title")String title,@RequestParam("currentPage")String currentPage) throws Exception {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(Integer.valueOf(currentPage),ResultMsg.PAGESIZE);
        List<Notice> list=noticeService.findAllWithTerms(title,null);
        PageInfo<Notice> pageInfo=new PageInfo<>(list);
        map.put("notices",list);
        map.put("pageInfo",pageInfo);
        map.put("status", ResultMsg.OK);
        return map;
    }

    /**
     *管理员新增通知
     *@params:title,content
     *@return:Map
     *@date: 20:10 2018/3/3
     **/
    @ApiOperation(value = "管理员新增通知",notes = "管理员新增通知")
    @RequestMapping(value="/addNotice",method = RequestMethod.POST)
    public Map<String,Object> addNotice(@RequestParam("title")String title,@RequestParam("content")String content) throws Exception {
        Map<String,Object> map=new HashMap<>();
        noticeService.addNotice(new Notice(title,content,0,DateTimeUtil.getDate()));
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *管理员删除通知
     *@params:nid
     *@return:
     *@date: 22:41 2018/3/7
     **/
    @ApiOperation(value = "管理员删除帖子",notes = "管理员删除帖子")
    @RequestMapping(value="/delNotice",method = RequestMethod.POST)
    public Map<String,Object> delNotice(@RequestParam("nid")String nid) throws Exception {
        Map<String,Object> map=new HashMap<>();
        noticeService.deleteNotice(Integer.parseInt(nid));
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *管理员更改滚屏信息
     *@params:rid
     *@return:Map
     *@date: 0:00 2018/4/13
     **/
    @ApiOperation(value = "管理员更改滚屏信息",notes = "管理员更改滚屏信息")
    @RequestMapping(value="/updateRunpic",method = RequestMethod.POST)
    public Map<String,Object> updateRunpic(@RequestParam("rid")String rid,@RequestParam("url")String url,@RequestParam("description")String description) throws Exception {
        Map<String,Object> map=new HashMap<>();
        Runpic runpic=runpicService.getByRid(Integer.parseInt(rid));
        runpic.setDescription(description);
        runpic.setUrl(url);
        runpicService.update(runpic);
        map.put("status", ResultMsg.OK);
        return map;
    }
}
