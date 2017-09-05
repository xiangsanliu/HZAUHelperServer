package com.xiang.project;


import com.alibaba.fastjson.JSON;
import com.xiang.project.controller.JWController;
import com.xiang.project.service.Building;
import com.xiang.project.service.Re;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(JWController.class, args);
        List<Building> buildings = new ArrayList<Building>();
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, "{\"nodeInText\":\"9*Area*荟园\",\"PartList\":\"\",\"SelectPart\":1}");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.163.128.60/SelectPage.aspx/SerBindTabDate")
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String content = response.body().string();
        Re re = JSON.parseObject(content, Re.class);
        System.out.println(re.getD());
        Document document = Jsoup.parse(re.getD());
        Elements elements = document.getElementsByClass("AreaBox");

        for (Element element : elements) {
            String name = element.text().substring(0, 4);
            String id = element.attr("ondblclick").substring(25,37);
            buildings.add(new Building(name, id));
        }

        for (Building building: buildings) {
            System.out.println(building.getName()+"\t"+building.getId());
        }
        System.out.println(document.text());
    }

}
