package com.example.service;

import com.example.bean.Goods;
import com.example.dao.GoodsMonthDao;

import java.util.List;

public class MonthGoodService {
    public List<Goods> selectMonth(String time) {
        GoodsMonthDao goodsMonthDao = new GoodsMonthDao();
        return goodsMonthDao.selectMonth(time);
    }
}
