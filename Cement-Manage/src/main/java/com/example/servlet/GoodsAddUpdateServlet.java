package com.example.servlet;

import com.example.service.GoodsAddUpdateService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;


@WebServlet("/goodsaddupdate")
public class GoodsAddUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getSession().getAttribute("user1") == null && req.getSession().getAttribute("user2") == null) {
            return;
        }
        GoodsAddUpdateService goodsAddUpdateService = new GoodsAddUpdateService();

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");


        String name = req.getParameter("name");
        String goodRecordID = req.getParameter("goodRecordId");
        String type = req.getParameter("gtype");
        String stock = req.getParameter("stock");
        String unit = req.getParameter("unit");

        Writer writer = resp.getWriter();


        int ret = goodsAddUpdateService.AddUpdate(Integer.parseInt(goodRecordID), name, type, stock, unit);

        if (ret == 0) {
            writer.write("<h2> 更新产品记录失败 </h2>");
        } else {
            writer.write("<h2> 更新产品记录成功 </h2>");

            System.out.println("更新产品记录成功");
            if(req.getSession().getAttribute("user1") != null){
                resp.sendRedirect("index.html");
            }else {
                resp.sendRedirect("indexu.html");
            }
        }

    }
}
