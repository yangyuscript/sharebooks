package com.lin.sharebooks.filter;

import com.lin.sharebooks.model.User;
import com.lin.sharebooks.service.UserService;
import com.lin.sharebooks.util.RedisComponent;
import com.lin.sharebooks.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {"/user/*","/super/*"},filterName = "BasicFilter")
//@WebFilter(urlPatterns = {"/test/*"},filterName = "BasicFilter")
public class BasicFilter implements Filter{
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private UserService userService;

    private static final List<String> ALLOWED_PATHS=new ArrayList<>(Arrays.asList("/user/login"));
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化过滤器sharebooks");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("<----------doFilter执行---------->");
        HttpServletRequest httpRequest=(HttpServletRequest)servletRequest;
        HttpServletResponse httpResponse=(HttpServletResponse)servletResponse;
        //判断请求类型是否为OPTIONS,若是则直接放行
        String method=httpRequest.getMethod();
        System.out.println("请求的类型是:"+method);

        String url_all=httpRequest.getRequestURL().toString();
        System.out.println("请求url_all是:"+url_all);
        String url=httpRequest.getRequestURI();
        System.out.println("请求url是:"+url);
        String token=httpRequest.getHeader("x-access-token");
        System.out.println("<----------此次请求的请求头中token为:"+token+"---------->");
        System.out.println(ALLOWED_PATHS.contains(url));
        //ALLOWED_PATHS.contains(url)若为true此请求地址不进行身份验证
        if(!("OPTIONS".equals(method))&&!ALLOWED_PATHS.contains(url)){
            if(token!=null && !("".equals(token))){
                Object object=redisComponent.sentinelGet(token);
                int userid = 0;
                if(object!=null){
                    System.out.println("根据token从redis中取出的内容是"+object.toString());
                    userid = Integer.valueOf(object.toString());
                }
                if(userid!=0){
                    User user = userService.getByUserid(userid);
                    if(user!=null){
                        if(!(url.contains("super")&&user.getCondi()== 0)){
                            //此时请求操作为管理员权限且该用户不为管理员具有管理员权限，权限不足
                            ResultMsg.returnErrJson(httpResponse,"此时请求操作为管理员权限且该用户不为管理员具有管理员权限，权限不足");
                            return ;
                        }
                    }else{
                        //根据token查询到的用户无效
                        ResultMsg.returnErrJson(httpResponse,"根据token查询到的用户无效,拒绝请求");
                        return ;
                    }
                }else{
                    //token失效,拒绝请求
                    ResultMsg.returnErrJson(httpResponse,"token失效,拒绝请求");
                    return ;
                }
            }else{
                //请求
                ResultMsg.returnErrJson(httpResponse,"请求无token,拒绝请求");
                return ;
            }
        }
        filterChain.doFilter(httpRequest,httpResponse);
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
