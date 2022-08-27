package com.example.service;

import com.example.bean.Goods;
import com.example.dao.GoodsDao;

public class GoodReadySellService {
    public Goods getGoods(int goodId) {
        GoodsDao goodsDao = new GoodsDao();

        return goodsDao.SelectIdAll(goodId);
    }
}
