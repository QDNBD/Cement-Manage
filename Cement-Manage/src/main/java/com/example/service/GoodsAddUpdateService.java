package com.example.service;

import com.example.bean.Goods;
import com.example.dao.GoodsAddDao;
import com.example.dao.GoodsDao;
import com.example.dao.GoodsMonthDao;
import com.example.servlet.GetTime;

public class GoodsAddUpdateService {
    public int AddUpdate(Integer goodRecordID, String name, String type, String stock, String unit) {
        try {
            GetTime getTime = new GetTime();
            GoodsAddDao goodsAddDao = new GoodsAddDao();
            GoodsDao goodsDao = new GoodsDao();
            GoodsMonthDao goodsMonthDao = new GoodsMonthDao();
            Goods goodsMon = new Goods();
            Goods goods = new Goods();
            Goods goodsrecord = new Goods();
            //拿到Id检查 goodrecord 表是否存在该Id
            goodsrecord = goodsAddDao.SelectAll(goodRecordID);
            if(goodsrecord.getName().equals("")) {
                System.out.println("不存在该产品入库记录");
                return 0;
            }
            //存在，判断该修改的是否为同名，同型号产品
            if(goodsrecord.getName().equals(name) && goodsrecord.getType().equals(type)) {
                //如果是
                //拿到总表的之前的数量
                goods = goodsDao.SelectStock(name, type);
                //更新数量
                goodsDao.GoodsUpdate(goods.getId(), (goods.getInstock() - goodsrecord.getStock() + Integer.parseInt(stock)),
                        (goods.getStock() - goodsrecord.getStock() + Integer.parseInt(stock)));

            }else {
                //如果不是，将goods表中删除该条入库信息
                goods = goodsDao.SelectStock(goodsrecord.getName(), goodsrecord.getType());
                goodsDao.GoodsUpdate(goods.getId(), (goods.getInstock() - goodsrecord.getStock()),
                        (goods.getStock() - goodsrecord.getStock()));

                //判断总表是否已经写入本次修改的产品
                //查询产品总表是否有该原料
                int retUId = goodsDao.Select(name,type);
                System.out.println(retUId);
                if(retUId == 0) {
                    //说明没有直接插入
                    goodsDao.Addall(name, type, stock, unit);
                }else {
                    //说明有
                    //通过id获取产品总表的入库数量
                    Goods goodsStock = new Goods();
                    goodsStock = goodsDao.SelectIdAll(retUId);

                    //将本次数量与原始数量进行相加写入数据库
                    goodsDao.UpdateStock(retUId,goodsStock.getInstock()+ Integer.parseInt(stock), goodsStock.getStock()+Integer.parseInt(stock));
                }
            }

            //更新产品月表
            goodsMon = goodsMonthDao.SelectStock(goodsrecord.getName(), goodsrecord.getType(), goodsrecord.getCreateTime().substring(0,7));
            //先减去
            goodsMonthDao.UpdateStock(goodsMon.getId(), goodsMon.getInstock() - goodsrecord.getStock(), goodsrecord.getCreateTime().substring(0,7));
            //查询产品月表是否有
            goodsMon = goodsMonthDao.SelectStock(name,type,getTime.getNowTime());
            if(goodsMon.getId() == null) {
                goodsMonthDao.Addall(name,type,stock,unit,getTime.getNowTime());
            }else {
                goodsMonthDao.UpdateStock(goodsMon.getId(), goodsMon.getInstock() + Integer.parseInt(stock), getTime.getNowTime());
            }

            //更新本次修改记录 goodrecord 表
            int ret = goodsAddDao.AddUpdate(goodRecordID, name, type, stock,unit);
            return ret;
        }catch (Exception e) {
            System.out.println("GoodsAddUpdateService 出错");
            e.printStackTrace();
        }
        return 0;
    }
}
