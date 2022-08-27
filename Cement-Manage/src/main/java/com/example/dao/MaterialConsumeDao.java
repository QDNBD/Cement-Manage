package com.example.dao;

import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MaterialConsumeDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void UpdateStock(int retid, int stock, int usestock) {
        try{
            String sql = "update materials set stock=?, usestock=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,stock);
            ps.setInt(2,usestock);
            ps.setInt(3,retid);

            ps.executeUpdate();
            System.out.println("原材料总表出库更新成功");
        }catch (Exception e) {
            System.out.println("原材料总表出库更新失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }

    }

    public int ConUpdate(String name, String type, String usestock, int stock, String unit) {
        try{
            String sql = "insert into materialuse(name,type,usestock,stock,unit) values(?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            assert connection != null;
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(usestock));
            ps.setInt(4,stock);
            ps.setString(5,unit);

            return ps.executeUpdate();

        }catch (Exception e){
            System.out.println("数据库原材料出库添加失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }
}
