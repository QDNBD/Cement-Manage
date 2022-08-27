package com.example.servlet;


import com.example.bean.User;
import com.example.service.UserinforService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/userinfor")
public class UserinforServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user1") == null && req.getSession().getAttribute("user2") == null) {
            return;
        }
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        UserinforService userinforService = new UserinforService();

        String password = req.getParameter("password");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");


        Writer writer = resp.getWriter();
        if(password1.trim().length() < 6 || password1.trim().length() > 20) {
            System.out.println("失败！密码小于6位字符或者大于20位字符");
            writer.write("失败！密码小于6位字符或者大于20位字符");
            return;
        }
        if (!password1.equals(password2)) {
            System.out.println("失败！密码不一致或为空");
            writer.write("失败！密码不一致或为空");
            return;
        }

        User user = new User();
        HttpSession session = req.getSession();
        int type = 0;
        if(req.getSession().getAttribute("user1") != null) {
            user = (User) session.getAttribute("user1");
            System.out.println(user);
            type = 1;
        }else {
            user = (User) session.getAttribute("user2");
            System.out.println(user);
            type = 2;
        }

        int ret = userinforService.UpdatePass(user, password, password1);

        if(ret == 0) {
            System.out.println("密码修改失败！");
            writer.write("<h2> 密码修改失败，原密码输入可能有误</h2>" );
        }else {
            System.out.println("密码修改成功!");
            writer.write("<h2> 密码修改成功 </h2>" );
            if(type == 1) {
                resp.sendRedirect("/Cement_Manage_war_exploded/index.html");
            }else if(type == 2) {
                resp.sendRedirect("/Cement_Manage_war_exploded/indexu.html");
            }
        }

    }
}
