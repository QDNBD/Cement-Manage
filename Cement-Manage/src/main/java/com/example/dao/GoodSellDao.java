package com.example.dao;

import com.example.bean.GoodSell;
import com.example.bean.GoodSellItem;
import com.example.util.DBUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodSellDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean commitDate(GoodSell goodSell) {

        try {
            String insertGoodSellSql = "insert into goodsell(id, allprice, purchaser, create_time) values (?,?,?,?)";

            connection = DBUtil.getConnection(false);
            ps = connection.prepareStatement(insertGoodSellSql);
            ps.setString(1, goodSell.getId());
            ps.setInt(2, goodSell.getAllPrice());
            ps.setString(3, goodSell.getPurchaser());
            ps.setString(4, goodSell.getCreateTime());

            int ret = ps.executeUpdate();
            if (ret == 0) {
                throw new RuntimeException("插入goodsell表产品售出失败！");
            }

            String insertGoodSellItemSql = "insert into goodsell_item(goodsell_id, goods_id, goods_name,goods_type, goods_sellstock, goods_unit,goods_price) values (?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(insertGoodSellItemSql);

            for (GoodSellItem goodSellItem : goodSell.getGoodSellItemList()) {
                ps.setString(1,goodSellItem.getGoodsellId());
                ps.setInt(2, goodSellItem.getGoodsId());
                ps.setString(3, goodSellItem.getGoodsName());
                ps.setString(4, goodSellItem.getGoodsType());
                ps.setInt(5, goodSellItem.getGoodsSellStock());
                ps.setString(6, goodSellItem.getGoodsUnit());
                ps.setInt(7, goodSellItem.getGoodsPrice());
                ps.addBatch();// 缓存
            }

            int[] effect = ps.executeBatch();//批量的插入
            for (int i : effect) {
                if (i == 0) {
                    throw new RuntimeException("插入出库单item明细失败！");
                }
            }
            //批量插入没有发生任何的异常
            connection.commit();


        }catch (Exception e) {
            e.printStackTrace();
            //判断链接是否断开
            if (connection != null) {
                try {
                    //回滚
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println("GoodsellDao数据库插入失败");
            return false;
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return true;
    }
}
