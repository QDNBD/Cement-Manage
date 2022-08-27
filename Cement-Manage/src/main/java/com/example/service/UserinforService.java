package com.example.service;

import com.example.bean.User;
import com.example.dao.UserDao;


public class UserinforService {
    public int UpdatePass(User user, String password, String password1) {
        UserDao userDao = new UserDao();
        try {
            //查询账号密码是否正确
            String usname = user.getUsername();
            User user1 = userDao.Select(usname, password);
            if(user1.getId() == null) {
                System.out.println("密码错误");
                return 0;
            }
            return userDao.UpdatePass(user1.getId(), password1);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
