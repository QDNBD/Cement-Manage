package com.example.servlet;

import com.example.dao.MaterialsAddDao;
import com.example.service.MaterialsAddService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/materialsadd")
public class MaterialsAddServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getSession().getAttribute("user1") == null && req.getSession().getAttribute("user2") == null) {
            System.out.println("未登录");
            return;
        }
        System.out.println("进入原材料添加页面");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        MaterialsAddService materialsAddService = new MaterialsAddService();

        String name = req.getParameter("name");
        String atype = req.getParameter("atype");
        String stock = req.getParameter("stock");
        String unit = req.getParameter("unit");
        String price = req.getParameter("price");

        Writer writer = resp.getWriter();

        int ret = materialsAddService.Add(name, atype, stock, unit, price);

        if (ret == 0) {
            writer.write("<h2> 原材料添加失败 </h2>");
        } else {
            writer.write("<h2> 原材料添加成功 </h2>");

            System.out.println("原材料添加成功");
            if(req.getSession().getAttribute("user1") != null){
                resp.sendRedirect("index.html");
            }else {
                resp.sendRedirect("indexu.html");
            }

        }
    }
}
