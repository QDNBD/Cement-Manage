package com.example.dao;

import com.example.bean.Goods;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsMonthDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


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
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public int Select(String name, String type, String nowTime) {
        try {
            String sql = "select id from goodsmonth where name=? and type=? and create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setString(3,nowTime);

            rs = ps.executeQuery();
            int id = 0;
            if(rs.next()) {
                id = rs.getInt("id");
            }
            return id;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }

    public void Addall(String name, String type, String stock, String unit, String nowTime) {
        try{
            String sql = "insert into goodsmonth(name, type, instock, unit, create_time) values(?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,Integer.parseInt(stock));
            ps.setString(4,unit);
            ps.setString(5,nowTime);

            int ret = ps.executeUpdate();
            if (ret == 0) {
                System.out.println("数据库产品月表添加失败");
            }else {
                System.out.println("数据库产品月表添加成功");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
    }

    public Goods SelectAll(int id) {
        try {
            String sql = "select name,type,instock,sellstock,unit,price,create_time from goodsmonth where id=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            rs =ps.executeQuery();
            Goods goods = new Goods();
            if (rs.next()) {
                goods.setId(id);
                goods.setName(rs.getString("name"));
                goods.setType(rs.getString("type"));
                goods.setInstock(rs.getInt("instock"));
                goods.setSellstock(rs.getInt("sellstock"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getInt("price"));
                goods.setCreateTime(rs.getString("create_time"));
            }
            return goods;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public void UpdateStock(int id, int instock, String nowTime) {
        try{
            String sql = "update goodsmonth set instock=? where id=? and create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,instock);
            ps.setInt(2,id);
            ps.setString(3,nowTime);

            int ret = ps.executeUpdate();
            if(ret == 0) {
                System.out.println("goodsMonth UpdateStock 失败");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }

    }

    public Goods SelectStock(String name, String type, String bTime) {
        Goods goods = new Goods();
        try {
            String sql = "select id, instock, sellstock, price from goodsmonth where name=? and type=? and create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setString(3,bTime);

            rs = ps.executeQuery();

            if (rs.next()) {
                goods.setId(rs.getInt("id"));
                goods.setInstock(rs.getInt("instock"));
                goods.setSellstock(rs.getInt("sellstock"));
                goods.setPrice(rs.getInt("price"));
            }

            return goods;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return goods;
    }

    public int AddSellMonth(String name, String type, Integer sellGoodsNum, String unit, Integer sellGoodsPrice, String nowTime) {
        try{
            String sql = "insert into goodsmonth(name, type, sellstock, unit, price, create_time) values(?,?,?,?,?,?)";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,type);
            ps.setInt(3,sellGoodsNum);
            ps.setString(4,unit);
            ps.setInt(5,(sellGoodsPrice * sellGoodsNum));
            ps.setString(6,nowTime);

            return ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }

        return 0;
    }

    public int UpdateSellStock(Integer id, int sellstock, int price, String nowTime) {
        try{
            String sql = "update goodsmonth set sellstock=?, price=? where id=? and create_time=?";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            ps.setInt(1,sellstock);
            ps.setInt(2,price);
            ps.setInt(3,id);
            ps.setString(4,nowTime);

            return ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return 0;
    }
}
