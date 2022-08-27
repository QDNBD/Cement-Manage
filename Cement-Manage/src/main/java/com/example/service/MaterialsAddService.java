package com.example.service;

import com.example.bean.Materials;
import com.example.dao.MaterialsAddDao;
import com.example.dao.MaterialsDao;
import com.example.dao.MaterialsMonthDao;
import com.example.servlet.GetTime;


public class MaterialsAddService {

    public int Add(String name, String type, String stock, String unit, String price) {
        //对入库的原材料记录表操作
        MaterialsAddDao materialsAddDao = new MaterialsAddDao();
        MaterialsDao materialsDao = new MaterialsDao();
        MaterialsMonthDao materialsMonthDao = new MaterialsMonthDao();
        GetTime getTime = new GetTime();
        try {
            //查询原材料总表是否有该原料
            int retselet = materialsDao.Select(name,type);

            //将入库的原料料数量加入到总表
            if(retselet == 0) {
                //说明没有直接插入
                materialsDao.Addall(name, type, stock, unit, price);
            }else {
                //说明有
                //通过id获取原材料总表的入库数量
                Materials materials = new Materials();
                materials = materialsDao.SelectAll(retselet);

                //将本次数量与原始数量进行相加写入数据库
                materialsDao.UpdateStock(retselet,materials.getInstock()+ Integer.parseInt(stock), materials.getStock()+Integer.parseInt(stock),
                        materials.getPrice()+(Integer.parseInt(stock) * Integer.parseInt(price)));
            }

            //查询原材料月表是否有该原料
            int retMonth = materialsMonthDao.Select(name, type, getTime.getNowTime());
            //将入库的原料料数量加入
            if(retMonth == 0) {
                //说明没有直接插入
                materialsMonthDao.Addall(name, type, stock, unit, price, getTime.getNowTime());
            }else {
                //说明有
                //通过id获取原材料月表的入库数量
                Materials materials = new Materials();
                materials = materialsMonthDao.SelectAll(retMonth);

                //将本次数量与原始数量进行相加写入数据库
                materialsMonthDao.UpdateStock(retMonth,materials.getInstock()+ Integer.parseInt(stock), materials.getPrice()+(Integer.parseInt(stock) * Integer.parseInt(price)), getTime.getNowTime());
            }

            //插入记录表
            int retadd = materialsAddDao.Add(name, type, stock, unit, price);
            return retadd;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
