package com.lin.sharebooks.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResultMsg {
    private static double EARTH_RADIUS = 6378.137;
    public final static String OK = "ok";
    public final static String NO = "no";
    public final static int PAGESIZE = 10;
    public final static int CURRENTPAGE = 1;
    public static final Map<String, Object> map = new HashMap();

    /**
     * 过滤器验证用户权限返回结果-权限不足
     *
     * @params:httpResponse
     * @return:
     * @date: 16:58 2018/4/4
     **/
    public static void returnErrJson(HttpServletResponse httpResponse, String msg) {
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        map.put("result", msg);
        map.put("status", "401");
        try {
            httpResponse.getWriter().write(mapper.writeValueAsString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

}
