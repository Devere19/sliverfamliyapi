package cn.edu.guet.controller;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @Author 郭乐源
 * @Date 2022/8/6 16:15
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        String str="{\"phoneNumber\":\"13785962053\",\"purePhoneNumber\":\"13785962053\",\"countryCode\":\"86\",\"watermark\":{\"timestamp\":1659773509,\"appid\":\"wx4bd8a563740e834f\"}}";
        System.out.println(str);
        Map maps = (Map) JSON.parse(str);
        System.out.println(maps.get("phoneNumber"));
    }
}
