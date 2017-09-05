package com.xiang.project.controller;


import com.xiang.project.domain.JWCheckCode;
import com.xiang.project.service.JWGetter;
import okhttp3.*;
import org.jsoup.nodes.Document;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.lang.annotation.Documented;

@EnableAutoConfiguration
@RestController
public class JWController {

    @ResponseBody
    @RequestMapping("/jw/getCheckCode")
    public JWCheckCode getCheckCode() throws IOException {
        return JWGetter.getCheckCode();
    }

    @ResponseBody
    @RequestMapping("/jw/login")
    public String getExamPlanDoc(String account, String password, String code, String cookie, String viewState) throws IOException {
        System.out.println(account);
        System.out.println(password);
        System.out.println(code);
        System.out.println(cookie);
        System.out.println(viewState);
        Document document = JWGetter.getExamPlanUrl(account, password, code, cookie, viewState);
        System.out.println(document.body().text());
        return document.body().text();
    }

    @ResponseBody
    @RequestMapping("/jw/test")
    public String getTest() throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, "{\"nodeInText\":\"132*Arc*荟18栋\",\"PartList\":\"\",\"SelectPart\":1}");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.163.128.60/SelectPage.aspx/SerBindTabDate")
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());

        return null;
    }

}
