package com.example.service;

import com.example.bean.Goods;
import com.example.dao.GoodsDao;

import java.util.List;

public class YearGoodService {
    public List<Goods> selectYear(String dYear) {
        GoodsDao goodsDao = new GoodsDao();
        return goodsDao.selectYear(dYear);
    }
}
