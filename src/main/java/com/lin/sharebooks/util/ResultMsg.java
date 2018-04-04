package com.lin.sharebooks.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResultMsg {
    public final static String OK="ok";
    public final static String NO="no";
    public final static int PAGESIZE=10;
    public final static int CURRENTPAGE=1;
    public static final Map<String,Object> map = new HashMap();

    /**
     *过滤器验证用户权限返回结果-权限不足
     *@params:httpResponse
     *@return:
     *@date: 16:58 2018/4/4
     **/
    public static void returnErrJson(HttpServletResponse httpResponse,String msg){
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        map.put("result",msg);
        map.put("status","401");
        try {
            httpResponse.getWriter().write(mapper.writeValueAsString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
