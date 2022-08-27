package com.example.dao;

import com.example.bean.Materials;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialsMonthDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    public int Select(String name, String type, String nowTime) {
        try {
            String sql = "select id from materialsmonth where name=? and type=? and create_time=?";
            connection = DBUtil.getConnection(true);
            assert connection != null;
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setString(3,nowTime);

            rs = ps.executeQuery();
            int id = 0;
            if(rs.next()) {
                id = rs.getInt("id");
                return id;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }

    public void Addall(String name, String type, String stock, String unit, String price, String nowTime) {
        try{
            String sql = "insert into materialsmonth(name, type, instock, unit, price, create_time) values(?,?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setString(4,unit);
            ps.setInt(5,(Integer.parseInt(price) * Integer.parseInt(stock)));
            ps.setString(6,nowTime);

            int ret = ps.executeUpdate();
            if (ret == 0) {
                System.out.println("数据库原材料月表添加失败");
            }else {
                System.out.println("数据库原材料月表添加成功");
            }

        }catch (Exception e){
            System.out.println("数据库原材料月表添加失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
    }

    public Materials SelectAll(int id) {
        try {
            String sql = "select name,type,instock,usestock,unit,price,create_time from materialsmonth where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            rs =ps.executeQuery();
            Materials materials = new Materials();
            if (rs.next()) {
                materials.setId(id);
                materials.setName(rs.getString("name"));
                materials.setType(rs.getString("type"));
                materials.setInstock(rs.getInt("instock"));
                materials.setUsestock(rs.getInt("usestock"));
                materials.setUnit(rs.getString("unit"));
                materials.setPrice(rs.getInt("price"));
                materials.setCreateTime(rs.getString("create_time"));
            }
            return materials;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public void UpdateStock(int id, int instock, int price, String time) {
        try{
            String sql = "update materialsmonth set instock=?, price=? where id=? and create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,instock);
            ps.setInt(2,price);
            ps.setInt(3,id);
            ps.setString(4,time);

            ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
    }

    public List<Materials> selectMonth(String dTime) {
        List<Materials> materialsList = new ArrayList<>();

        try {
            String sql = "select id,name,type,instock,usestock,unit,price from materialsmonth where create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setString(1,dTime);
            rs =ps.executeQuery();

            while (rs.next()) {
                Materials materials = new Materials();
                materials.setId(rs.getInt("id"));
                materials.setName(rs.getString("name"));
                materials.setType(rs.getString("type"));
                materials.setInstock(rs.getInt("instock"));
                materials.setUsestock(rs.getInt("usestock"));
                materials.setUnit(rs.getString("unit"));
                materials.setPrice(rs.getInt("price"));
                materials.setCreateTime(dTime);
                materialsList.add(materials);
            }

            return materialsList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public Materials SelectStock(String name, String type, String bTime) {
        Materials materials = new Materials();

        try {
            String sql = "select id, instock, usestock, price from materialsmonth where name=? and type=? and create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setString(3,bTime);

            rs = ps.executeQuery();

            if (rs.next()) {
                materials.setId(rs.getInt("id"));
                materials.setInstock(rs.getInt("instock"));
                materials.setUsestock(rs.getInt("usestock"));
                materials.setPrice(rs.getInt("price"));
            }

            return materials;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return materials;
    }

    public void ConsumeAll(String name, String type, String usestock, String unit, String nowTime) {
        try{
            String sql = "insert into materialsmonth(name, type, usestock, unit, create_time) values(?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(usestock));
            ps.setString(4,unit);
            ps.setString(5,nowTime);

            int ret = ps.executeUpdate();
            if (ret == 0) {
                System.out.println("数据库原材料月表出库添加失败");
            }else {
                System.out.println("数据库原材料月表出库添加成功");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }

    }

    public void UpdateConsumeStock(Integer id, int usestock, String nowTime) {
        try{
            String sql = "update materialsmonth set usestock=? where id=? and create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,usestock);
            ps.setInt(2,id);
            ps.setString(3,nowTime);

            ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
    }
}
