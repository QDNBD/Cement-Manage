package com.example.dao;

import com.example.bean.Goods;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsAllBrowesDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Goods> selectGoods() {

        List<Goods> goodsList = new ArrayList<>();
        try {
            String sql = "select id,name,type,instock,sellstock,stock,unit,price,create_time from goods";
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
                String cT = rs.getString("create_time");
                String cTs = cT.substring(0,cT.length()-2);
                goods.setCreateTime(cTs);
                goodsList.add(goods);
            }
            System.out.println("产品列表：");
            System.out.println(goodsList);

            return goodsList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return goodsList;
    }
}
