package com.lin.sharebooks.util;


import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *springboot内部发起http请求（微信小程序使用）
 *@author 18374
 *@date: 17:18 2018/1/27
 **/
public class HttpClientUtil {
    public static String clientGET(String url, HttpMethod method){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity requestEntity = new HttpEntity(null, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }
}
