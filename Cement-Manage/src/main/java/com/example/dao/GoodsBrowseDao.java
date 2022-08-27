package com.example.dao;

import com.example.bean.Goods;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsBrowseDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Goods> selectGoodsRecord() {
        List<Goods> goodsList = new ArrayList<>();

        try {
            String sql = "select id,name,type,stock,unit,create_time from goodrecord";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            rs =ps.executeQuery();

            while (rs.next()) {
                Goods goods = new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setType(rs.getString("type"));
                goods.setStock(rs.getInt("stock"));
                goods.setUnit(rs.getString("unit"));
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
