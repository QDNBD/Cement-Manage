package com.example.service;

import com.example.bean.Goods;
import com.example.dao.GoodsAddDao;
import com.example.dao.GoodsDao;
import com.example.dao.GoodsMonthDao;
import com.example.servlet.GetTime;

public class GoodsAddService {
    public int Add(String name, String type, String stock, String unit) {
        GoodsAddDao goodsAddDao = new GoodsAddDao();
        GoodsDao goodsDao = new GoodsDao();
        GoodsMonthDao goodsMonthDao = new GoodsMonthDao();
        GetTime getTime = new GetTime();
        try {
            //查询产品总表是否有该原料
            int retid = goodsDao.Select(name,type);

            //将入库的产品数量加入到总表
            if(retid == 0) {
                //说明没有直接插入
                goodsDao.Addall(name, type, stock, unit);
            }else {
                //说明有
                //通过id获取产品总表的入库数量
                Goods goods = new Goods();
                goods = goodsDao.SelectIdAll(retid);

                //将本次数量与原始数量进行相加写入数据库
                goodsDao.UpdateStock(retid,goods.getInstock()+ Integer.parseInt(stock), goods.getStock()+Integer.parseInt(stock));
            }

            int retMonth = goodsMonthDao.Select(name, type, getTime.getNowTime());
            if(retMonth == 0) {
                //说明没有直接插入
                goodsMonthDao.Addall(name, type, stock, unit, getTime.getNowTime());
            }else {
                //说明有
                //通过id获取原材料月表的入库数量
                Goods goods = new Goods();
                goods = goodsMonthDao.SelectAll(retMonth);

                //将本次数量与原始数量进行相加写入数据库
                goodsMonthDao.UpdateStock(retMonth, goods.getInstock()+ Integer.parseInt(stock), getTime.getNowTime());
            }


            int ret = goodsAddDao.Add(name, type, stock, unit);
            return ret;

        }catch (Exception e) {
            System.out.println("产品添加失败");
            e.printStackTrace();
        }
        return 0;

    }
}
