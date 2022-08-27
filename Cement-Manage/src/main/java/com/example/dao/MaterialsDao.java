package com.example.dao;

import com.example.bean.Materials;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MaterialsDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Materials> selectMonth(String time) {
        List<Materials> materialsList = new ArrayList<>();

        try {
            String sql = "select id,name,type,instock,usestock,unit,price from materialsmonth where create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setString(1,time);
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
                materials.setCreateTime(time);
                materialsList.add(materials);
            }

            return materialsList;
        } catch (SQLException e) {
            System.out.println("查询数据库原材料总表数据失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public void Addall(String name, String type, String stock, String unit, String price) {
        try{
            String sql = "insert into materials(name,type,instock,stock,unit,price) values(?,?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            assert connection != null;
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setInt(4,Integer.parseInt(stock));
            ps.setString(5,unit);
            ps.setInt(6,(Integer.parseInt(price) * Integer.parseInt(stock)));

            int ret = ps.executeUpdate();
            if (ret == 0) {
                System.out.println("数据库初始原材料总表添加失败");
            }else {
                System.out.println("数据库初始原材料总表添加成功");
            }

        }catch (Exception e){
            System.out.println("数据库初始原材料添加失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
    }

    public void UpdateStock(int id, int instock, int stock, int price) {

        try{
            String sql = "update materials set instock=?, stock=?, price=?, create_time=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,instock);
            ps.setInt(2,stock);
            ps.setInt(3,price);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ps.setString(4, LocalDateTime.now().format(formatter));
            ps.setInt(5,id);

            ps.executeUpdate();
            System.out.println("原材料总表入库更新成功");
        }catch (Exception e) {
            System.out.println("原材料总表入库更新失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
    }

    public Materials SelectAll(int id) {

        try {
            String sql = "select name,type,instock,stock,usestock,unit,price,create_time from materials where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            rs =ps.executeQuery();

            if (rs.next()) {
                Materials materials = new Materials();
                materials.setId(id);
                materials.setName(rs.getString("name"));
                materials.setType(rs.getString("type"));
                materials.setInstock(rs.getInt("instock"));
                materials.setStock(rs.getInt("stock"));
                materials.setUsestock(rs.getInt("usestock"));
                materials.setUnit(rs.getString("unit"));
                materials.setPrice(rs.getInt("price"));
                String cT = rs.getString("create_time");
                String cTs = cT.substring(0, cT.length() - 2);

                materials.setCreateTime(cTs);
                System.out.println("原材料总列表：");
                System.out.println(materials);

                return materials;
            }
        } catch (SQLException e) {
            System.out.println("查询数据库原材料总表数据失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public Materials SelectStock(String name, String type) {

        Materials materials = new Materials();

        try {
            String sql = "select id, instock, stock, price from materials where name=? and type=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);

            rs = ps.executeQuery();

            if (rs.next()) {
                materials.setId(rs.getInt("id"));
                materials.setInstock(rs.getInt("instock"));
                materials.setStock(rs.getInt("stock"));
                materials.setPrice(rs.getInt("price"));
            }

            return materials;

        }catch (Exception e) {
            System.out.println("查询materialStock失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return materials;

    }

    public void MaterialsUpdate(int id, int instock, int stock, int price) {
        try {
            String sql = "update materials set instock=?, stock=?, price=?, create_time=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,instock);
            ps.setInt(2,stock);
            ps.setInt(3,price);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ps.setString(4,LocalDateTime.now().format(formatter));
            ps.setInt(5,id);

            ps.executeUpdate();

        }catch (SQLException e) {
            System.out.println("materialsUpdate 更新失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,null);
        }

    }

    public int Select(String name, String type) {
        try {
            String sql = "select id from materials where name=? and type=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);

            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }

    public List<Materials> Selects() {
        List<Materials> materialsList = new ArrayList<>();
        try {
            String sql = "select id,name,type,instock,stock,usestock,unit,price,create_time from materials";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            rs =ps.executeQuery();

            while (rs.next()) {
                Materials materials = new Materials();
                materials.setId(rs.getInt("id"));
                materials.setName(rs.getString("name"));
                materials.setType(rs.getString("type"));
                materials.setInstock(rs.getInt("instock"));
                materials.setStock(rs.getInt("stock"));
                materials.setUsestock(rs.getInt("usestock"));
                materials.setUnit(rs.getString("unit"));
                materials.setPrice(rs.getInt("price"));
                String cT = rs.getString("create_time");
                String cTs = cT.substring(0, cT.length() - 2);
                materials.setCreateTime(cTs);
                materialsList.add(materials);
            }
            System.out.println("原材料总列表：");
            System.out.println(materialsList);
            return materialsList;
        } catch (SQLException e) {
            System.out.println("查询数据库原材料总表数据失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public List<Materials> selectYear(String dYear) {
        List<Materials> materialsList = new ArrayList<>();
        try {
            String sql = "select id,name,type,instock,stock,usestock,unit,price from materials";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            rs =ps.executeQuery();

            while (rs.next()) {
                Materials materials = new Materials();
                materials.setId(rs.getInt("id"));
                materials.setName(rs.getString("name"));
                materials.setType(rs.getString("type"));
                materials.setInstock(rs.getInt("instock"));
                materials.setStock(rs.getInt("stock"));
                materials.setUsestock(rs.getInt("usestock"));
                materials.setUnit(rs.getString("unit"));
                materials.setPrice(rs.getInt("price"));
                materials.setCreateTime(dYear);
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
}
