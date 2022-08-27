package com.example.servlet;

import com.example.bean.Materials;
import com.example.service.YearMaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/yearmaterial")
public class YearMaterialServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        if (req.getSession().getAttribute("user1") == null) {
            return;
        }
        YearMaterialService yearMaterialService = new YearMaterialService();
        GetTime getTime = new GetTime();

        try {
            //1、先从数据库当中 找到所有的原材料月信息
            //当月
            List<Materials> materialsList = yearMaterialService.selectYear(getTime.getNowTime().substring(0,4));

            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = resp.getWriter();
            //将list转换为json字符串，并将该字符串写到流当中
            objectMapper.writeValue(writer, materialsList);
            //推到前端
            writer.write(writer.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
