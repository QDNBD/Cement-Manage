package com.example.servlet;

import com.example.bean.Goods;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.service.GoodsBrowseService;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;


@WebServlet("/goodsRecordBrow")
public class GoodsBrowseServlet extends HttpServlet {

    GoodsBrowseService goodsBrowseService = new GoodsBrowseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getSession().getAttribute("user1") == null && req.getSession().getAttribute("user2") == null) {
            return;
        }
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        System.out.println("进入查看所有产品");


        try {
            //1、先从数据库当中 找到所有的商品信息
            List<Goods> goodsList = goodsBrowseService.selectGoodsRecord();

            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = resp.getWriter();
            //将list转换为json字符串，并将该字符串写到流当中
            objectMapper.writeValue(writer,goodsList);
            //推到前端
            writer.write(writer.toString());

        }catch (Exception e) {
            System.out.println("浏览产品入库记录失败");
            e.printStackTrace();
        }
    }

}
