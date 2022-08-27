package com.example.servlet;


import com.example.service.MaterialAddUpdateService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/materialaddupdate")
public class MaterialAddUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        if(req.getSession().getAttribute("user1") == null && req.getSession().getAttribute("user2") == null) {
            return;
        }
        MaterialAddUpdateService materialAddUpdateService = new MaterialAddUpdateService();

        String name = req.getParameter("name");
        String materialRecordID = req.getParameter("materialRecordId");
        String type = req.getParameter("gtype");
        String stock = req.getParameter("stock");
        String unit = req.getParameter("unit");
        String price = req.getParameter("price");

        Writer writer = resp.getWriter();

        int ret = materialAddUpdateService.AddUpdate(Integer.parseInt(materialRecordID), name, type, stock, unit, price);

        if (ret == 0) {
            writer.write("<h2> 更新原材料记录失败 </h2>");
        } else {
            writer.write("<h2> 更新原材料记录成功 </h2>");
            System.out.println("更新原材料记录成功");
            if(req.getSession().getAttribute("user1") != null){
                resp.sendRedirect("index.html");
            }else {
                resp.sendRedirect("indexu.html");
            }
        }
    }
}
