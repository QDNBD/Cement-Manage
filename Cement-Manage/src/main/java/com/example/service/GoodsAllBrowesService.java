package com.example.service;

import com.example.bean.Goods;
import com.example.dao.GoodsAllBrowesDao;

import java.util.List;

public class GoodsAllBrowesService {
    GoodsAllBrowesDao goodsAllBrowesDao = new GoodsAllBrowesDao();

    public List<Goods> selectGoods() {
        return goodsAllBrowesDao.selectGoods();
    }
}
