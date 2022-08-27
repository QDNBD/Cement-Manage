package com.example.servlet;


import com.example.bean.Materials;
import com.example.service.MaterialsConsueBrowseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet("/materialsConsueBrow")
public class MaterialsConsueBrowseServlet extends HttpServlet {

    MaterialsConsueBrowseService materialsConsueBrowseService = new MaterialsConsueBrowseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("查看所有原材料出库记录");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        if(req.getSession().getAttribute("user1") == null && req.getSession().getAttribute("user2") == null) {
            return;
        }

        try {
            //1、先从数据库当中 找到所有的商品信息
            List<Materials> materialsList = materialsConsueBrowseService.selectMaterials();

            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = resp.getWriter();
            //将list转换为json字符串，并将该字符串写到流当中
            objectMapper.writeValue(writer,materialsList);
            //推到前端
            writer.write(writer.toString());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
