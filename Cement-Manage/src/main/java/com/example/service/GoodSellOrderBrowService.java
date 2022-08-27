package com.example.service;

import com.example.bean.GoodSell;
import com.example.dao.GoodSellOrderBrowDao;

import java.util.List;

public class GoodSellOrderBrowService {
    public List<GoodSell> queryOrdersAll() {
        GoodSellOrderBrowDao goodSellOrderBrowDao = new GoodSellOrderBrowDao();

        return goodSellOrderBrowDao.queryOrdersAll();

    }
}
