package com.example.servlet;

import com.example.dao.UserDao;
import com.example.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("注册！");

        req.setCharacterEncoding("UTF-8");

        resp.setContentType("text/html; charset=utf-8");

        String username = req.getParameter("username");
        String utype = req.getParameter("utype");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String nickname = req.getParameter("nickname");
        String email= req.getParameter("email");
        String sex = req.getParameter("sex");
        String age = req.getParameter("age");
        String head = req.getParameter("head");

        UserDao userDao = new UserDao();

        Writer writer = resp.getWriter();
        if(username.trim().length() != 11) {
            System.out.println("注册失败！注册失败！手机号码不正确");
            writer.write("<h2> 注册失败！手机号不正确 </h2>" );
            return;
        }
        if(password1.trim().length() < 6 || password1.trim().length() > 20) {
            System.out.println("注册失败！注册失败！密码小于6位字符或者大于20位字符");
            writer.write("注册失败！注册失败！密码小于6位字符或者大于20位字符");
            return;
        }
        if (!password1.equals(password2)) {
            System.out.println("注册失败！注册失败！密码不一致或为空");
            writer.write("注册失败！注册失败！密码不一致或为空");
            return;
        }

        if(username.trim().length() == 11 && password1.trim().length() >= 6 && password2.trim().length() >= 6
                && password1.trim().length() <=20 && password1.equals(password2)){

            int ret = userDao.InsertUser(username,password1,utype,nickname,email,sex,age,head);
            if(ret == 0) {
                System.out.println("注册失败！");
                writer.write("<h2> 注册失败</h2>" );
            }else {
                System.out.println("注册成功!");
                writer.write("<h2> 注册成功 </h2>" );
                resp.sendRedirect("/Cement_Manage_war_exploded/index.html");
            }

        }else {
            System.out.println("注册失败！注册失败！密码输入有误");
            writer.write("<h2> 注册失败！密码输入有误 </h2>" );
        }
    }
}
