package com.xiang.project.service;

import com.xiang.project.domain.JWCheckCode;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class JWGetter {


    private static OkHttpClient okHttpClient ;
    private static final String MAIN_URL = "http://jw.hzau.edu.cn/";

    private static String getViewState() throws IOException {
        return Jsoup.connect(MAIN_URL).get().getElementsByTag("input").attr("value");
    }

    public static JWCheckCode getCheckCode() throws IOException {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        String GET_CODE_URL = "http://jw.hzau.edu.cn/CheckCode.aspx";
        Request request = new Request.Builder().url(MAIN_URL).build();
        Response response = okHttpClient.newCall(request).execute();
        String viewState = Jsoup.parse(response.body().string()).getElementsByTag("input").attr("value");
        String cookie =  response.header("Set-Cookie");
        cookie = cookie.substring(0, cookie.indexOf(';'));
        System.out.println(viewState);
        System.out.println(cookie);

        request = new Request.Builder()
                .url(MAIN_URL+"CheckCode.aspx")
                .addHeader("Cookie", cookie)
                .build();
        response = okHttpClient.newCall(request).execute();
        byte[] bytes = response.body().bytes();
        JWCheckCode checkCode = new JWCheckCode();
        checkCode.setCheckCode(bytes);
        checkCode.setCookie(cookie);
        checkCode.setViewState(viewState);
        return checkCode;
    }

    public static Document getExamPlanUrl(String account, String password, String code, String cookie, String viewState) throws IOException {
        FormBody body = new FormBody.Builder()
                .add("__VIEWSTATE", viewState)
                .add("txtUserName", account)
                .add("TextBox1", password)
                .add("TextBox2", password)
                .add("txtSecretCode", code)
                .add("RadioButtonList1", "学生")
                .add("Button1", "")
                .add("lbLanguage", "")
                .add("hidPdrs", "")
                .add("hidsc", "")
                .build();
        String POST_URL = "http://jw.hzau.edu.cn/default2.aspx";
        Request request = new Request.Builder()
                .url(POST_URL)
                .post(body)
                .addHeader("Cookie", cookie)
                .build();
        Response urlResponse = okHttpClient.newCall(request).execute();
        Document urlDocument = Jsoup.parse(urlResponse.body().string());
        return urlDocument;
    }


//    public static Document getExamPlanDoc(String account, String password, String code, String cookie) throws IOException {
//        String examPlanUrl = getExamPlanUrl(account, password, code, cookie);
//        FormBody formBody = new FormBody.Builder()
//                .add("xm", "�\uEF61��")
//                .add("xh", account)
//                .add("gnmkdm", examPlanUrl.substring(examPlanUrl.lastIndexOf('=')))
//                .build();
//        Request examPlanRequest = new Request.Builder()
//                .url(examPlanUrl)
//                .post(formBody)
//                .addHeader("Cookie", cookie)
//                .addHeader("Referer", "http://jw.hzau.edu.cn/xs_main.aspx?xh="+account)
//                .build();
//        Response examPlanResponse = okHttpClient.newCall(examPlanRequest).execute();
//        return Jsoup.parse(examPlanResponse.body().string());
//    }



}
