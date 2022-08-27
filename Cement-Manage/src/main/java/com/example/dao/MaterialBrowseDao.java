package com.example.dao;

import com.example.bean.Materials;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialBrowseDao {

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    public List<Materials> selectMaterials() {
        List<Materials> materialsList = new ArrayList<>();

        try {
            String sql = "select id,name,type,stock,unit,price,create_time from materialrecord";
            connection = DBUtil.getConnection(true);
            ps = connection.prepareStatement(sql);
            rs =ps.executeQuery();

            while (rs.next()) {
                Materials materials = new Materials();
                materials.setId(rs.getInt("id"));
                materials.setName(rs.getString("name"));
                materials.setType(rs.getString("type"));
                materials.setStock(rs.getInt("stock"));
                materials.setUnit(rs.getString("unit"));
                materials.setPrice(rs.getInt("price"));
                String cT = rs.getString("create_time");
                String cTs = cT.substring(0,cT.length()-2);

                materials.setCreateTime(cTs);
                materialsList.add(materials);
            }
            System.out.println("商品列表：");
            System.out.println(materialsList);

            return materialsList;

        } catch (SQLException e) {
            System.out.println("查询数据库原材料数据失败");
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, ps, rs);
        }
        return materialsList;
    }
}
