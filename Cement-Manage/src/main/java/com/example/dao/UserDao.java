package com.example.dao;

import com.example.bean.User;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    public User SelectAll(String username, String password, String utype) {
        User user = new User();
        try {
            String sql = "select id,username,password,identity_type from user where username=? and password=? and identity_type=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,username);
            ps.setString(2,password);
            ps.setInt(3,Integer.parseInt(utype));

            rs = ps.executeQuery();
            if(rs.next()) {
                Integer id = rs.getInt("id");
                user.setId(id);
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUType(rs.getInt("identity_type"));
            }

            return user;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return user;
    }

    public User Select(String usname, String password) {
        User user = new User();
        try {
            String sql = "select id from user where username=? and password=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,usname);
            ps.setString(2,password);

            rs = ps.executeQuery();
            if(rs.next()) {
                Integer id = rs.getInt("id");
                user.setId(id);
                user.setUsername(usname);
                user.setPassword(password);
            }
            return user;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return user;
    }

    public int UpdatePass(Integer id, String password1) {

        try {
            String sql = "update user set password=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,password1);
            ps.setInt(2,id);

            return ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }

    public int InsertUser(String username, String password1, String utype, String nickname, String email, String sex, String age, String head) {

        try {
            String sql = "insert into user(username,password,identity_type,nickname,email,sex,age,head) values(?,?,?,?,?,?,?,?)";
            connection = DBUtil.getConnection(true);

            ps = connection.prepareStatement(sql);

            ps.setString(1,username);
            ps.setString(2,password1);
            ps.setInt(3,Integer.parseInt(utype));
            ps.setString(4,nickname);
            ps.setString(5,email);
            ps.setString(6,sex);
            ps.setInt(7, Integer.parseInt(age));
            ps.setString(8,head);

            return ps.executeUpdate();
        }catch (Exception e) {
            System.out.println("抛出异常，注册失败,用户可能已经注册过了");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,null);
        }
        return 0;
    }

    public List<User> selectUsers() {
        List<User> userList = new ArrayList<>();
        try {
            String sql = "select id,username,identity_type,nickname,email,sex,age,create_time from user";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            rs =ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setUType(rs.getInt("identity_type"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                user.setSex(rs.getString("sex"));
                user.setAge(rs.getInt("age"));
                String cT = rs.getString("create_time");
                String cTs = cT.substring(0,cT.length()-2);
                user.setCreateTime(cTs);

                userList.add(user);
            }
            System.out.println("用户列表：");
            System.out.println(userList);

            return userList;

        } catch (SQLException e) {
            System.out.println("查询数据库用户数据失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return userList;
    }

    public int DeleteUser(int id) {
        try {
            String sql = "delete from user where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            return ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps,null);
        }
        return 0;
    }
}
