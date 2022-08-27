package com.example.dao;

import com.example.bean.Goods;
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

public class GoodsDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int Select(String name, String type) {
        try {
            String sql = "select id from goods where name=? and type=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);

            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }

        }catch (Exception e) {
            System.out.println("总表查询是否有入库产品失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }

    public void Addall(String name, String type, String stock, String unit) {

        try {
            String sql = "insert into goods(name, type, instock, stock, unit) values (?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setInt(4,Integer.parseInt(stock));
            ps.setString(5,unit);

            ps.executeUpdate();

        }catch (Exception e) {
            System.out.println("产品插入总表");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
    }

    public Goods SelectIdAll(int retid) {
        Goods goods = new Goods();
        try {
            String sql = "select name, type, instock, sellstock, stock, unit, price, create_time from goods where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,retid);

            rs = ps.executeQuery();
            if(rs.next()) {
                goods.setId(retid);
                goods.setName(rs.getString("name"));
                goods.setType(rs.getString("type"));
                goods.setInstock(rs.getInt("instock"));
                goods.setSellstock(rs.getInt("sellstock"));
                goods.setStock(rs.getInt("stock"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getInt("price"));

                String cT = rs.getString("create_time");
                String cTs = cT.substring(0,cT.length()-2);
                goods.setCreateTime(cTs);
            }else {
                return null;
            }
            return goods;

        }catch (Exception e) {
            System.out.println("查询总表所有数据失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return null;
    }

    public void UpdateStock(int retid, int instock, int stock) {

        try{
            String sql = "update goods set instock=?, stock=?, create_time=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,instock);
            ps.setInt(2,stock);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ps.setString(3, LocalDateTime.now().format(formatter));
            ps.setInt(4,retid);

            ps.executeUpdate();
            System.out.println("产品总表入库更新成功");
        }catch (Exception e) {
            System.out.println("产品总表入库更新失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
    }

    public int updateGoods(Goods goods) {

        try{
            String sql = "update goods set sellstock=?, stock=?, price=?, create_time=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,goods.getSellGoodsNum()+goods.getSellstock());
            ps.setInt(2,goods.getStock()-goods.getSellGoodsNum());
            ps.setInt(3,goods.getPrice()+(goods.getSellGoodsPrice()*goods.getSellGoodsNum()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ps.setString(4, LocalDateTime.now().format(formatter));

            ps.setInt(5,goods.getId());

            return ps.executeUpdate();

        }catch (Exception e) {
            System.out.println("产品总表入库更新失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }

    public List<Goods> selectMonth(String time) {
        List<Goods> goodsList = new ArrayList<>();

        try {
            String sql = "select id,name,type,instock,sellstock,unit,price from goodsmonth where create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setString(1,time);
            rs =ps.executeQuery();

            while (rs.next()) {
                Goods goods = new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setType(rs.getString("type"));
                goods.setInstock(rs.getInt("instock"));
                goods.setSellstock(rs.getInt("sellstock"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getInt("price"));
                goods.setCreateTime(time);
                goodsList.add(goods);
            }

            return goodsList;
        } catch (SQLException e) {
            System.out.println("查询数据库产品总表数据失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public Goods SelectStock(String name, String type) {
        Goods goods = new Goods();

        try {
            String sql = "select id, instock, stock from goods where name=? and type=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);

            rs = ps.executeQuery();

            if (rs.next()) {
                goods.setId(rs.getInt("id"));
                goods.setInstock(rs.getInt("instock"));
                goods.setStock(rs.getInt("stock"));
            }

            return goods;

        }catch (Exception e) {
            System.out.println("查询goodsStock失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return goods;
    }

    public void GoodsUpdate(Integer id, int instock, int stock) {
        try {
            String sql = "update goods set instock=?, stock=?, create_time=? where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,instock);
            ps.setInt(2,stock);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ps.setString(3, LocalDateTime.now().format(formatter));
            ps.setInt(4,id);

            ps.executeUpdate();

        }catch (SQLException e) {
            System.out.println("GoodsUpdate 更新失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,null);
        }
    }

    public List<Goods> selectYear(String dYear) {
        List<Goods> goodsList = new ArrayList<>();

        try {
            String sql = "select id,name,type,instock,sellstock,stock,unit,price from goods";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            rs =ps.executeQuery();

            while (rs.next()) {
                Goods goods = new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setType(rs.getString("type"));
                goods.setInstock(rs.getInt("instock"));
                goods.setSellstock(rs.getInt("sellstock"));
                goods.setStock(rs.getInt("stock"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getInt("price"));
                goods.setCreateTime(dYear);
                goodsList.add(goods);
            }
            return goodsList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }
}
