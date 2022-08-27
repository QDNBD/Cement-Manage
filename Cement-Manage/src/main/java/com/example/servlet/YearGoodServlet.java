package com.example.servlet;


import com.example.bean.Goods;
import com.example.service.YearGoodService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet("/yeargoods")
public class YearGoodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        if (req.getSession().getAttribute("user1") == null) {
            return;
        }
        YearGoodService yearGoodService = new YearGoodService();
        GetTime getTime = new GetTime();

        try {
            List<Goods> goodsList = yearGoodService.selectYear(getTime.getNowTime().substring(0,4));

            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = resp.getWriter();
            //将list转换为json字符串，并将该字符串写到流当中
            objectMapper.writeValue(writer, goodsList);
            //推到前端
            writer.write(writer.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

