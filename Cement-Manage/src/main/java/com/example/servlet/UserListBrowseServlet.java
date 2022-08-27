package com.example.servlet;


import com.example.bean.Materials;
import com.example.bean.User;
import com.example.dao.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet("/userlist")
public class UserListBrowseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        if(req.getSession().getAttribute("user1") == null) {
            return;
        }
        UserDao userDao = new UserDao();
        try {
            //1、先从数据库当中 找到所有信息
            List<User> userList = userDao.selectUsers();
            //将后端的数据  转换为json字符串
            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = resp.getWriter();
            //将list转换为json字符串，并将该字符串写到流当中
            objectMapper.writeValue(writer,userList);
            //推到前端
            writer.write(writer.toString());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
