package com.example.servlet;

import com.example.bean.User;
import com.example.dao.UserDao;
import com.example.util.DBUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        resp.setContentType("text/html; charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String utype = req.getParameter("utype");

        UserDao userDao = new UserDao();
        User user = userDao.SelectAll(username, password, utype);

        Writer writer = resp.getWriter();

        try {
            if(user.getId() == null) {
                writer.write("<h2> 没有该用户："+username+"</h2>");
            }else if(!password.equals(user.getPassword())){
                writer.write("<h2> 密码错误："+username+"</h2>");
            }else {
                HttpSession session = req.getSession();
                if(user.getUType() == 1) {
                    session.setAttribute("user1",user);
                    writer.write("<h2> 登录成功："+username+"</h2>");
                    resp.sendRedirect("/Cement_Manage_war_exploded/index.html");
                }else {
                    session.setAttribute("user2",user);
                    writer.write("<h2> 登录成功："+username+"</h2>");
                    resp.sendRedirect("/Cement_Manage_war_exploded/indexu.html");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
