package com.example.service;

import com.example.bean.Goods;
import com.example.dao.GoodsBrowseDao;
import java.util.List;

public class GoodsBrowseService {

    GoodsBrowseDao goodsBrowseDao = new GoodsBrowseDao();

    public List<Goods> selectGoodsRecord() {
        return goodsBrowseDao.selectGoodsRecord();
    }
}
