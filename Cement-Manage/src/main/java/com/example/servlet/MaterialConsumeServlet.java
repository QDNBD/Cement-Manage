package com.example.servlet;


import com.example.service.MaterialConsumeService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/materialconsume")
public class MaterialConsumeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        if(req.getSession().getAttribute("user1") == null && req.getSession().getAttribute("user2") == null) {
            return;
        }

        MaterialConsumeService materialConsumeService = new MaterialConsumeService();

        String name = req.getParameter("name");
        String atype = req.getParameter("atype");
        String stock = req.getParameter("stock");
        String unit = req.getParameter("unit");


        Writer writer = resp.getWriter();

        int ret = materialConsumeService.ConsumeUpdate(name,atype,stock,unit);

        if (ret == 0) {
            writer.write("<h2> 原材料出库失败 </h2>");
        } else {
            writer.write("<h2> 原材料出库成功 </h2>");

            System.out.println("原材料出库成功");
            if(req.getSession().getAttribute("user1") != null){
                resp.sendRedirect("index.html");
            }else {
                resp.sendRedirect("indexu.html");
            }
        }
    }
}
