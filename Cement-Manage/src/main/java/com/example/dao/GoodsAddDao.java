package com.example.dao;

import com.example.bean.Goods;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GoodsAddDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int Add(String name, String type, String stock, String unit) {

        try{
            String sql = "insert into goodrecord(name,type,stock,unit) values (?,?,?,?)";
            connection = DBUtil.getConnection(true);
            assert connection != null;
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setString(4,unit);

            return ps.executeUpdate();
        }catch (Exception e) {
            System.out.println("数据库产品入库失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return 0;
    }

    public Goods SelectAll(Integer goodRecordID) {

        Goods goods = new Goods();

        try {
            String sql = "select name, type, stock, unit, create_time from goodrecord where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,goodRecordID);

            rs = ps.executeQuery();

            if (rs.next()) {
                goods.setId(goodRecordID);
                goods.setName(rs.getString("name"));
                goods.setType(rs.getString("type"));
                goods.setStock(rs.getInt("stock"));
                goods.setUnit(rs.getString("unit"));
                String cT = rs.getString("create_time");
                String cTs = cT.substring(0,cT.length()-2);
                goods.setCreateTime(cTs);
            }

            return goods;

        }catch (Exception e) {
            System.out.println("查询goodRecordID失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return goods;
    }


    public int AddUpdate(Integer goodRecordID, String name, String type, String stock, String unit) {

        try {
            String sql = "update goodrecord set name=?, type=?, stock=?, unit=?, create_time=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setString(4,unit);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ps.setString(5, LocalDateTime.now().format(formatter));
            ps.setInt(6,goodRecordID);

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
