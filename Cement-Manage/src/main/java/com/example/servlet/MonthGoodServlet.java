package com.example.servlet;


import com.example.bean.Goods;
import com.example.service.MonthGoodService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/monthgoods")
public class MonthGoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        if (req.getSession().getAttribute("user1") == null) {
            return;
        }
        MonthGoodService monthMaterialService = new MonthGoodService();
        GetTime getTime = new GetTime();

        try {
            //1、先从数据库当中 找到所有的产品月信息
            //当月
            List<Goods> goodsList = monthMaterialService.selectMonth(getTime.getNowTime());

            //上月
            List<Goods> goodsList1 = monthMaterialService.selectMonth(getTime.getLastMonth());

            List<List<Goods>> lists = new ArrayList<>();
            if (!goodsList.isEmpty()) {
                lists.add(goodsList);
            }
            if (!goodsList1.isEmpty()) {
                lists.add(goodsList1);
            }
            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = resp.getWriter();
            //将list转换为json字符串，并将该字符串写到流当中
            objectMapper.writeValue(writer, lists);
            //推到前端
            writer.write(writer.toString());

        } catch (Exception e) {
            System.out.println("浏览产品月记录失败");
            e.printStackTrace();
        }
    }
}
