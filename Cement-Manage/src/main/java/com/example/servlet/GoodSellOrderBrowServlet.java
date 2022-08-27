package com.example.servlet;

import com.example.bean.GoodSell;
import com.example.service.GoodSellOrderBrowService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;


@WebServlet("/goodsellorderbrow")
public class GoodSellOrderBrowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        if (req.getSession().getAttribute("user1") == null) {
            return;
        }
        GoodSellOrderBrowService goodSellOrderBrowService = new GoodSellOrderBrowService();

        Writer writer = resp.getWriter();

        List<GoodSell> goodSells = goodSellOrderBrowService.queryOrdersAll();
        System.out.println("订单明细："+goodSells);

        if(goodSells == null || goodSells.isEmpty()) {
            System.out.println("还没有产生出库单");
            writer.write("<h2> 还没有产生出库单 </h2>");
        }else {
            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                //将list转换为json字符串，并将该字符串写到流当中
                objectMapper.writeValue(writer,goodSells);
                System.out.println("json字符串："+writer.toString());
                //推到前端
                writer.write(writer.toString());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
