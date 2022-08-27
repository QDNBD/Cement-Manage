package com.example.dao;

import com.example.bean.Materials;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MaterialsAddDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    public int Add(String name, String type, String stock, String unit, String price) {

        try{
            String sql = "insert into materialrecord(name,type,stock,unit,price) values(?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            assert connection != null;
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setString(4,unit);
            ps.setInt(5,Integer.parseInt(price));

            return ps.executeUpdate();

        }catch (Exception e){
            System.out.println("数据库原材料添加失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }

    public Materials SelectAll(int matrialid) {

        Materials materials = new Materials();
        try {
            String sql = "select name, type, stock, unit, price, create_time from materialrecord where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,matrialid);

            rs = ps.executeQuery();

            if (rs.next()) {
                materials.setId(matrialid);
                materials.setName(rs.getString("name"));
                materials.setType(rs.getString("type"));
                materials.setStock(rs.getInt("stock"));
                materials.setUnit(rs.getString("unit"));
                materials.setPrice(rs.getInt("price"));
                String cT = rs.getString("create_time");
                String cTs = cT.substring(0,cT.length()-2);
                materials.setCreateTime(cTs);
            }
            return materials;

        }catch (Exception e) {
            System.out.println("查询MaterialRecordID失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return materials;
    }

    public int AddUpdate(int matrialid, String name, String type, String stock, String unit, String price) {

        try {
            String sql = "update materialrecord set name=?, type=?, stock=?, unit=?, price=?, create_time=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setString(4,unit);
            ps.setInt(5,Integer.parseInt(price));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ps.setString(6, LocalDateTime.now().format(formatter));
            ps.setInt(7,matrialid);

            return ps.executeUpdate();

        }catch (Exception e) {
            System.out.println("AddUpdate 产品记录更新失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }
}
