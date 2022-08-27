package com.example.dao;

import com.example.bean.GoodSell;
import com.example.bean.GoodSellItem;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodSellOrderBrowDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<GoodSell> queryOrdersAll() {
        List<GoodSell> goodSellList = new ArrayList<>();
        try {
            String sql = "select o1.id         as id,\n" +
                    "       o1.allprice        as allprice,\n" +
                    "       o1.purchaser       as purchaser,\n" +
                    "       o1.create_time     as create_time,\n" +
                    "       o2.id              as item_id,\n" +
                    "       o2.goodsell_id     as goodsell_id,\n" +
                    "       o2.goods_id        as goods_id,\n" +
                    "       o2.goods_name      as goods_name,\n" +
                    "       o2.goods_type      as goods_type,\n" +
                    "       o2.goods_sellstock as goods_sellstock,\n" +
                    "       o2.goods_unit      as goods_unit,\n" +
                    "       o2.goods_price     as goods_price\n" +
                    "from goodsell as o1 left join goodsell_item as o2 on o1.id = o2.goodsell_id order by o1.id;";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            GoodSell goodSell = null;
            //组织订单内容
            while (rs.next()) {
                if(goodSell == null) {
                    goodSell = new GoodSell();
                    extractOrder(goodSell,rs);
                    goodSellList.add(goodSell);
                }

                String goodSellId = rs.getString("id");

                if(!goodSellId.equals(goodSell.getId())) {
                    goodSell = new GoodSell();
                    extractOrder(goodSell,rs);
                    goodSellList.add(goodSell);
                }
                GoodSellItem goodSellItem = extractOrderItem(rs);
                goodSell.getGoodSellItemList().add(goodSellItem);
            }

            return goodSellList;

        }catch (Exception e) {
            System.out.println("GoodSellOrderBrowDao queryOrdersAll() 失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }

    public GoodSellItem extractOrderItem(ResultSet resultSet) throws SQLException {

        GoodSellItem goodSellItem = new GoodSellItem();
        goodSellItem.setId(resultSet.getInt("item_id"));
        goodSellItem.setGoodsellId(resultSet.getString("goodsell_id"));
        goodSellItem.setGoodsId(resultSet.getInt("goods_id"));
        goodSellItem.setGoodsName(resultSet.getString("goods_name"));
        goodSellItem.setGoodsType(resultSet.getString("goods_type"));
        goodSellItem.setGoodsSellStock(resultSet.getInt("goods_sellstock"));
        goodSellItem.setGoodsUnit(resultSet.getString("goods_unit"));
        goodSellItem.setGoodsPrice(resultSet.getInt("goods_price"));
        return goodSellItem;
    }

    public void extractOrder(GoodSell goodSell,ResultSet resultSet) throws SQLException{
        goodSell.setId(resultSet.getString("id"));
        goodSell.setAllPrice(resultSet.getInt("allprice"));
        goodSell.setPurchaser(resultSet.getString("purchaser"));
        String cT = resultSet.getString("create_time");
        String cTs = cT.substring(0,cT.length()-2);
        goodSell.setCreateTime(cTs);
    }

    public List<GoodSell> selectMonthNumber(String orderNumber) {

        List<GoodSell> goodSellList = new ArrayList<>();

        try {
            String sql = "select o1.id         as id,\n" +
                    "       o1.allprice        as allprice,\n" +
                    "       o1.purchaser       as purchaser,\n" +
                    "       o1.create_time     as create_time,\n" +
                    "       o2.id              as item_id,\n" +
                    "       o2.goodsell_id     as goodsell_id,\n" +
                    "       o2.goods_id        as goods_id,\n" +
                    "       o2.goods_name      as goods_name,\n" +
                    "       o2.goods_type      as goods_type,\n" +
                    "       o2.goods_sellstock as goods_sellstock,\n" +
                    "       o2.goods_unit      as goods_unit,\n" +
                    "       o2.goods_price     as goods_price\n" +
                    "from goodsell as o1 left join goodsell_item as o2 on o1.id = o2.goodsell_id where o1.id=?;";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setString(1,orderNumber);

            rs = ps.executeQuery();

            GoodSell goodSell = null;
            //组织订单内容
            while (rs.next()) {
                if(goodSell == null) {
                    goodSell = new GoodSell();
                    extractOrder(goodSell,rs);
                    goodSellList.add(goodSell);
                }

                String goodSellId = rs.getString("id");

                if(!goodSellId.equals(goodSell.getId())) {
                    goodSell = new GoodSell();
                    extractOrder(goodSell,rs);
                    goodSellList.add(goodSell);
                }
                GoodSellItem goodSellItem = extractOrderItem(rs);
                goodSell.getGoodSellItemList().add(goodSellItem);
            }
            return goodSellList;

        }catch (Exception e) {
            System.out.println("GoodSellOrderBrowDao queryOrdersAll() 失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return null;
    }
}
