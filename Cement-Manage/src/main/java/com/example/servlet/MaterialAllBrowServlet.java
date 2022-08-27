package com.example.servlet;


import com.example.bean.Materials;
import com.example.service.MaterialAllBrowService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet("/materialAllBrow")
public class MaterialAllBrowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        if(req.getSession().getAttribute("user1") == null) {
            return;
        }
        MaterialAllBrowService materialAllBrowService = new MaterialAllBrowService();

        try {
            List<Materials> materialsList = materialAllBrowService.Selects();
            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = resp.getWriter();
            //将list转换为json字符串，并将该字符串写到流当中
            objectMapper.writeValue(writer,materialsList);
            //推到前端
            writer.write(writer.toString());

        }catch (Exception e) {
            System.out.println("查看原材料库存失败");
            e.printStackTrace();
        }

    }
}
