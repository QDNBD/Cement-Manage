package com.example.service;

import com.example.bean.GoodSell;
import com.example.bean.Goods;
import com.example.dao.GoodSellDao;
import com.example.dao.GoodsDao;
import com.example.dao.GoodsMonthDao;
import com.example.servlet.GetTime;

public class GoodSellService {
    public boolean commitDate(GoodSell goodSell) {
        GoodSellDao goodSellDao = new GoodSellDao();
        return goodSellDao.commitDate(goodSell);
    }


    public boolean updateGoods(Goods goods) {
        GoodsDao goodsDao = new GoodsDao();
        int ret = goodsDao.updateGoods(goods);
        if(ret == 0) {
            return false;
        }
        return true;
    }

    public boolean monthUpdate(Goods goods) {
        GoodsMonthDao goodsMonthDao = new GoodsMonthDao();
        Goods goodsMon = new Goods();
        GetTime getTime = new GetTime();
        goodsMon = goodsMonthDao.SelectStock(goods.getName(),goods.getType(),getTime.getNowTime());

        int rets = 0;
        if(goodsMon.getId() == null) {
            rets = goodsMonthDao.AddSellMonth(goods.getName(),goods.getType(),goods.getSellGoodsNum(),goods.getUnit(),goods.getSellGoodsPrice(),getTime.getNowTime());
        }else {
            rets = goodsMonthDao.UpdateSellStock(goodsMon.getId(), (goodsMon.getSellstock() + goods.getSellGoodsNum()), goodsMon.getPrice()+(goods.getSellGoodsNum()* goods.getSellGoodsPrice()), getTime.getNowTime());
        }

        if(rets == 0) {
            return false;
        }
        return true;
    }
}
