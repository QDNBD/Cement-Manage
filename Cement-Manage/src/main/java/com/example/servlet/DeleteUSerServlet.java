package com.example.servlet;


import com.example.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/delUSer")
public class DeleteUSerServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        if(req.getSession().getAttribute("user1") == null) {
            return;
        }
        Writer writer = resp.getWriter();
        String id = req.getParameter("id");

        UserDao userDao = new UserDao();
        int ret = userDao.DeleteUser(Integer.parseInt(id));
        if (ret == 1) {
            writer.write("<h2> 删除成功：" + id + "</h2>");
        } else {
            writer.write("<h2> 下架失败：" + id + "</h2>");
        }

    }
}
