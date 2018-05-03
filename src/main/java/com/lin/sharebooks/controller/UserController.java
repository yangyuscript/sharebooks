package com.lin.sharebooks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.sharebooks.model.*;
import com.lin.sharebooks.service.*;
import com.lin.sharebooks.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LoginlogService loginlogService;
    @Autowired
    private BookService bookService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PostService postService;
    @Autowired
    private NoticeService noticeService;
    /**
     * 用户注册时，检查邮箱是否已被注册使用
     * @param email
     * @return
     */
    @ApiOperation(value="账号可用性",notes = "判断注册账号是否已被注册使用")
    @RequestMapping(value="/judgeEmail",method= RequestMethod.POST)
    public Map<String,Object> judgeEmail(String email){
        Map<String,Object> map=new HashMap<>();
        boolean flag=userService.isUsedEmail(email);
        if(flag){
            map.put("status", ResultMsg.OK);
        }else{
            map.put("status",ResultMsg.NO);
        }
        return map;
    }
    /**
     * 用户注册
     * @param email
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    /*@ApiOperation(value="用户注册",notes="用户注册，发送邮箱验证")
    @RequestMapping(value="/regist",method = RequestMethod.POST)
    public Map<String, Object> regist(@RequestParam("email")String email, @RequestParam("password")String password)throws NoSuchAlgorithmException,UnsupportedEncodingException{
        Map<String,Object> map=new HashMap<>();
        boolean flag=userService.isUsedEmail(email);
        if(flag){
            //注册账号可使用
            User user=new User(email,email, MD5.EncodeByMD5(password),"head.jpg",0,0,"openid");
            userService.addUser(user);
            map.put("status",ResultMsg.OK);
        }else{
            //注册账号不可用（已被注册使用）
            map.put("msg","账号已被注册使用");
            map.put("status",ResultMsg.NO);
        }
        return map;
    }*/

    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @ApiOperation(value="普通用户登录",notes = "用户登录->判断密码->判断用户状态->生成token")
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestParam("email")String email, @RequestParam("password")String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, JsonProcessingException {
        Map<String,Object> map=new HashMap<>();
        User user=userService.checkEmailAndPassword(email,password);
        if(user!=null){
            //账号密码正确，登录成功
            String token= MD5.EncodeByMD5(user.getUserId()+UUID.randomUUID().toString());
            //redisComponent.sentinelSet(token,objectMapper.writeValueAsString(user));
            redisComponent.sentinelSet(token,user.getUserId());
            redisComponent.setLongExpire(token);
            map.put("token",token);
            map.put("loginUser",user);
            map.put("status",ResultMsg.OK);
        }else{
            //账号密码有错
            map.put("status",ResultMsg.NO);
        }
        return map;
    }
    /**
     *模拟登录页面
     *@params:
     *@return:
     *@date: 21:31 2017/12/7
     **/
    @ApiOperation(value="模拟登录页面",notes = "")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String pretendLogin(){
        return "请先登录，登录页面";
    }

    /**
     *微信小程序获取token
     *@params:code
     *@return:
     *@date: 17:03 2018/1/27
     **/
    @ApiOperation(value="微信小程序",notes = "微信小程序用户获取token")
    @RequestMapping(value="/getToken",method = RequestMethod.POST)
    public String getToken(@RequestParam(name = "code",required = true)String code){
        //String params="appid=wxb59a3d47efedfd9d&secret=5bdc0da0dda7abbc72242e23879e2376&js_code="+code+"&grant_type=authorization_code";
        String result=HttpClientUtil.clientGET("https://api.weixin.qq.com/sns/jscode2session?appid=wxb59a3d47efedfd9d&secret=5bdc0da0dda7abbc72242e23879e2376&js_code="+code+"&grant_type=authorization_code", HttpMethod.GET);
        System.out.println(result);
        //{"session_key":"T0fpWHgHGej7DispGNcxQQ==","expires_in":7200,"openid":"oPVIL0es7vjW3oz1TDs1riFzJLAk"}
        String openid_str=result.split(",")[2].split(":")[1];
        String openid=openid_str.substring(1,openid_str.length()-2);
        String session_3rd=null;
        System.out.println("---------------yangyuliushu"+openid);
        try {
            session_3rd=MD5.EncodeByMD5("yangyuliushu"+openid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User user=userService.getByOpenid(openid);
        if(user==null){
            userService.addUser(new User(null,null,null,null,0,1,openid,null,null,null,null,null,DateTimeUtil.getDate()));
        }
        redisComponent.sentinelSet(session_3rd,openid);
        redisComponent.setLongExpire(session_3rd);
        return session_3rd;
    }
    /**
     *微信小程序更新用户信息
     *@params:User
     *@return:map
     *@date: 22:02 2018/2/8
     **/
    @ApiOperation(value="微信小程序",notes = "更新用户信息")
    @RequestMapping(value="/updateUserInfo",method = RequestMethod.POST)
    public Map<String,Object> updateUserInfo(@RequestParam("nickName")String nickName,@RequestParam("head")String head,@RequestParam("gender")String gender,@RequestParam("city")String city,@RequestParam("province")String province,@RequestParam("country")String country,@RequestParam("language")String language,@RequestParam("token")String token){
        Map map=new HashMap();
        String openid=redisComponent.sentinelGet(token).toString();
        User oldUser=userService.getByOpenid(openid);
        if(oldUser==null){
            map.put("status",ResultMsg.NO);
        }else{
            oldUser.setNickname(nickName);
            oldUser.setHead(head);
            oldUser.setGender(gender);
            oldUser.setCity(city);
            oldUser.setProvince(province);
            oldUser.setCountry(country);
            oldUser.setLanguage(language);
            userService.update(oldUser);
            map.put("status",ResultMsg.OK);
        }
        return map;
    }

    /**
     *微信小程序写入用户登入地址
     *@params:token
     *@return:
     *@date: 19:51 2018/1/28
     **/
    @ApiOperation(value="微信小程序",notes = "写入用户登入地址")
    @RequestMapping(value="/updateLoginlog",method = RequestMethod.POST)
    public Map<String,Object> updateLoginlog(@RequestParam("token")String token,@RequestParam("latitude")double latitude,@RequestParam("longitude")double longitude){
        Map map=new HashMap();
        String openid=redisComponent.sentinelGet(token).toString();
        User user = userService.getByOpenid(openid);
        if(user!=null){
            Loginlog loginlog= new Loginlog(user.getUserId(),longitude,latitude, DateTimeUtil.getDate());
            loginlogService.addLoginlog(loginlog);
            map.put("status",ResultMsg.OK);
        }else{
            map.put("status",ResultMsg.NO);
        }
        return map;
    }
    /**
     *微信小程序用户添加书籍
     *@params:bookHead(file),bookName,bookDesc,bookType
     *@return:Map
     *@date: 21:34 2018/1/31
     **/
    @ApiOperation(value="微信小程序",notes = "用户添加书籍")
    @RequestMapping(value="/addBook",method = RequestMethod.POST)
    public Map<String,Object> addBook(@RequestParam("bookHead") MultipartFile bookHead, @RequestParam("bookName")String bookName, @RequestParam("bookTypeId")int bookTypeId, @RequestParam("bookDesc")String bookDesc, @RequestParam("token")String token, HttpServletRequest request){
        //System.out.println("request.getContextPath()"+request.getContextPath());
        //System.out.println("request.getSession().getServletContext().getRealPath(\"/\")"+request.getSession().getServletContext().getRealPath("/work/Tomcat/localhost/ROOT"));
        Map map=new HashMap();
        if(!bookHead.isEmpty()){
            // 获取文件名
            String fileName = bookHead.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String filePath = "c:/sbimgs/upload/";
            // 解决中文问题，liunx下中文路径，图片显示问题
            fileName = UUID.randomUUID() + suffixName;
            System.out.println("上传的图片是："+fileName);
            filePath=filePath + fileName;
            File dest = new File(filePath);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                bookHead.transferTo(dest);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String openid=redisComponent.sentinelGet(token).toString();
            User user=userService.getByOpenid(openid);
            Book book=new Book(user.getUserId(),bookTypeId,bookName,fileName,bookDesc,1,DateTimeUtil.getDate(),0);
            bookService.addBook(book);
            map.put("status",ResultMsg.OK);
        }else{
            map.put("status",ResultMsg.NO);
        }
        return map;
    }

    /**
     *微信小程序用户查看书籍详情（根据书籍id查找书籍详情）
     *@params:bid
     *@return:Book
     *@date: 19:27 2018/2/4
     **/
    @ApiOperation(value="微信小程序",notes = "用户查看书籍详情（根据书籍id查找书籍详情）")
    @RequestMapping(value="/getDetailBookByBid",method = RequestMethod.POST)
    public Map<String,Object> getDetailBookByBid(@RequestParam("bid")int bid){
        System.out.println("..........."+bid);
        Map map=new HashMap();
        Book book=bookService.getByBid(bid);
        map.put("book",book);
        return map;
    }
    /**
     *微信小程序从后台取出当前用户2km范围内的附近用户数据
     *@params:
     *@return:
     *@date: 22:36 2018/2/6
     **/
    @ApiOperation(value="微信小程序",notes = "从后台取出当前用户2km范围内的附近用户数据")
    @RequestMapping(value="/get2kmUsers",method = RequestMethod.POST)
    public Map<String,Object> get2kmUsers(@RequestParam("longitude")String longitude,@RequestParam("latitude")String latitude){
        Map map=new HashMap();
        map.put("arroundUsers",loginlogService.find2kmLoginlogs(Double.valueOf(longitude),Double.valueOf(latitude)));
        return map;
    }
    /**
     *微信小程序获取用户浏览发布者信息
     *@params:logid
     *@return:poster,postedBooks
     *@date: 20:08 2018/2/8
     **/
    @ApiOperation(value="微信小程序",notes = "获取用户浏览发布者信息")
    @RequestMapping(value="/getDataForViewUser",method = RequestMethod.POST)
    public Map<String,Object> getDataForViewUser(@RequestParam("logid")int logid,@RequestParam("currPage")int currPage){
        Map map=new HashMap();
        Loginlog loginlog=loginlogService.getByLogid(logid);
        if(loginlog!=null){
            int userid=loginlog.getUserid();
            User user=userService.getByUserid(userid);
            List<Book> bookList=bookService.findByUserid(userid);
            map.put("poster",user);
            PageHelper.startPage(currPage,ResultMsg.PAGESIZE);
            map.put("postedBooks",bookList);
            map.put("status",ResultMsg.OK);
        }else{
            map.put("status",ResultMsg.NO);
        }
        return map;
    }

    /**
     *微信小程序获取personal页面所需数据
     *@params:touserid
     *@return:Map
     *@date: 17:08 2018/2/25
     **/
    @ApiOperation(value="微信小程序",notes = "获取personal页面所需数据")
    @RequestMapping(value="/getPersonalIniaData",method = RequestMethod.POST)
    public Map<String,Object> getPersonalIniaData(@RequestParam("token")String token){
        Map map=new HashMap();
        String openid=redisComponent.sentinelGet(token).toString();
        User user=userService.getByOpenid(openid);
        if(user!=null){
            int messagesNotReadedNum=messageService.getPersonalIniaData(user.getUserId());
            map.put("messageNotReadedNum",messagesNotReadedNum);
            map.put("result",ResultMsg.OK);
        }else{
            map.put("result", ResultMsg.NO);
        }
        return map;
    }

    /**
     *微信小程序获取用户和其他用户聊天的最新的message
     *@params:touserid
     *@return:Map
     *@date: 17:50 2018/2/25
     **/
    @ApiOperation(value="微信小程序",notes = "获取personal页面所需数据")
    @RequestMapping(value="/getAllLastestMessagesToUser",method = RequestMethod.POST)
    public Map<String,Object> getAllLastestMessagesToUser(@RequestParam("token")String token){
        Map map=new HashMap();
        String openid=redisComponent.sentinelGet(token).toString();
        User user=userService.getByOpenid(openid);
        if(user!=null){
            List<Message> latestMessagesToUser=messageService.findAllByTouserid(user.getUserId());
            map.put("latestMessagesToUser",latestMessagesToUser);
            map.put("result",ResultMsg.OK);
        }else{
            map.put("result", ResultMsg.NO);
        }
        return map;
    }

    /**
     *微信小程序获取fromuser和touser所有的聊天信息
     *@params:fromuserid,token
     *@return:Map
     *@date: 19:51 2018/2/25
     **/
    @ApiOperation(value="微信小程序",notes = "获取fromuser和touser所有的聊天信息")
    @RequestMapping(value="/getAllMessagesFromAndTo",method = RequestMethod.POST)
    public Map<String,Object> getAllMessagesFromAndTo(@RequestParam("fromuserid")String fromuserid,@RequestParam("token")String token){
        Map map=new HashMap();
        String openid=redisComponent.sentinelGet(token).toString();
        User touser=userService.getByOpenid(openid);
        if(touser!=null){
            //改变未读消息condi为0已读状态
            List<Message> notReadedNews=messageService.findAllNotReadedBetween(touser.getUserId(),Integer.valueOf(fromuserid));
            for (Message m:notReadedNews) {
                Message nm=m.setCondi(0);
                messageService.updateMessage(nm);
            }
            //获取用户间所有信息
            List<Message> allMessagesFromAndTo=messageService.findAllByTouseridAndFromuserid(touser.getUserId(),Integer.valueOf(fromuserid));
            map.put("allMessagesFromAndTo",allMessagesFromAndTo);
            map.put("result",ResultMsg.OK);
        }else{
            map.put("result", ResultMsg.NO);
        }
        return map;
    }
    /**
     *用户发送信息
     *@params:token,fromuserid,message
     *@return:'ok'
     *@date: 21:26 2018/2/26
     **/
    @ApiOperation(value="微信小程序",notes = "用户发送信息")
    @RequestMapping(value="/sendMessage",method = RequestMethod.POST)
    public Map<String,Object> sendMessage(@RequestParam("token")String token,@RequestParam("touserid")String touserid,@RequestParam("message")String message){
        Map map=new HashMap();
        String openid=redisComponent.sentinelGet(token).toString();
        User fromuser=userService.getByOpenid(openid);
        if(fromuser!=null){
            Message m=new Message(fromuser.getUserId(),Integer.valueOf(touserid),message,1,DateTimeUtil.getDate());
            messageService.addMessage(m);
            map.put("result",ResultMsg.OK);
        }else{
            map.put("result", ResultMsg.NO);
        }
        return map;
    }
    /**
     *微信小程序获取用户发布的书籍(分页)
     *@params:token,currPage
     *@return:Map
     *@date: 20:43 2018/2/27
     **/
    @ApiOperation(value="微信小程序",notes = "获取用户发布的书籍(分页)")
    @RequestMapping(value="/findMyBooks",method = RequestMethod.POST)
    public Map<String,Object> findMyBooks(@RequestParam("token")String token,@RequestParam("currPage")String currPage){
        Map map=new HashMap();
        String openid=redisComponent.sentinelGet(token).toString();
        User user=userService.getByOpenid(openid);
        if(user!=null){
            PageHelper.startPage(Integer.valueOf(currPage),ResultMsg.PAGESIZE);
            List<Book> myBooks=bookService.findByUserid(user.getUserId());
            map.put("myBooks",myBooks);
            map.put("result",ResultMsg.OK);
        }else{
            map.put("result", ResultMsg.NO);
        }
        return map;
    }
    /**
     *微信小程序用户获取帖子（分页）
     *@params:token,currPage
     *@return:Map
     *@date: 22:50 2018/3/21
     **/
    @ApiOperation(value = "微信小程序用户获取帖子（分页）",notes = "一页默认10个记录")
    @RequestMapping(value="/getAllPosts",method = RequestMethod.POST)
    public Map<String,Object> getAllPosts(@RequestParam("token")String token,@RequestParam("currentPage")String currentPage) throws Exception {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(Integer.valueOf(currentPage),ResultMsg.PAGESIZE);
        List<Post> list=postService.findAllWithTerms(null,null);
        PageInfo<Post> pageInfo=new PageInfo<>(list);
        map.put("posts",list);
        map.put("pageInfo",pageInfo);
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *微信小程序用户查看帖子详情
     *@params:postid
     *@return:map
     *@date: 18:57 2018/3/25
     **/
    @ApiOperation(value="微信小程序",notes = "用户查看帖子详情（根据帖子id查找帖子详情）")
    @RequestMapping(value="/getDetailPostByPostid",method = RequestMethod.POST)
    public Map<String,Object> getDetailPostByPostid(@RequestParam("postid")int postid) throws Exception {
        Map map=new HashMap();
        Post post=postService.getByPostid(postid);
        map.put("post",post);
        return map;
    }
    /**
     *微信小程序用户获取通知
     *@params:currentPage
     *@return:Map
     *@date: 21:34 2018/3/25
     **/
    @ApiOperation(value = "微信小程序用户获取通知",notes = "一页默认10个记录")
    @RequestMapping(value="/getAllNotices",method = RequestMethod.POST)
    public Map<String,Object> getAllNotices(@RequestParam("currentPage")String currentPage) throws Exception {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(Integer.valueOf(currentPage),ResultMsg.PAGESIZE);
        List<Notice> list=noticeService.findAllWithTerms(null,null);
        PageInfo<Notice> pageInfo=new PageInfo<>(list);
        map.put("notices",list);
        map.put("pageInfo",pageInfo);
        map.put("status", ResultMsg.OK);
        return map;
    }
    /**
     *微信小程序用户查看通知详情
     *@params:nid
     *@return:map
     *@date: 18:57 2018/3/25
     **/
    @ApiOperation(value="微信小程序",notes = "用户查看通知详情（根据nid查找通知详情）")
    @RequestMapping(value="/getDetailNoticeByNid",method = RequestMethod.POST)
    public Map<String,Object> getDetailNoticeByNid(@RequestParam("nid")int nid) throws Exception {
        Map map=new HashMap();
        Notice notice=noticeService.getByNid(nid);
        map.put("notice",notice);
        return map;
    }
    /**
     *微信小程序用户删除书籍
     *@params:bid
     *@return:result
     *@date: 0:12 2018/4/8
     **/
    @ApiOperation(value="微信小程序",notes = "用户删除书籍")
    @RequestMapping(value="/deleteBookByBid",method = RequestMethod.POST)
    public Map<String,Object> deleteBookByBid(@RequestParam("bid")int bid,HttpServletRequest request) throws Exception {
        Map map=new HashMap();
        if(bid!=0){
            bookService.deleteBook(bid);
        }
        map.put("result",ResultMsg.OK);
        return map;
    }
}
