package com.wjysky.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@Slf4j
public class TestController {

    @RequestMapping("test")
    public String test() {
        log.info("---------------");
        return "/html/test.html";
    }

    public static void main(String[] args) {
        String filename = "C:\\Users\\admin\\Desktop\\data.txt";
        try {
            StringBuilder sb = new StringBuilder();
            Files.lines(Paths.get(filename)).parallel().forEachOrdered(sb::append);
            System.out.println(sb.toString());

            byte[] bytes = new BASE64Decoder().decodeBuffer(sb.toString());

            System.out.println(new String(bytes, "GBK"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
