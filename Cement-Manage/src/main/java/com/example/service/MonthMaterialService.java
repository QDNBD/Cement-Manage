package com.example.service;

import com.example.bean.Materials;
import com.example.dao.MaterialsMonthDao;

import java.util.List;

public class MonthMaterialService {
    public List<Materials> selectMonth( String dTime) {
        MaterialsMonthDao materialsMonthDao = new MaterialsMonthDao();
        return materialsMonthDao.selectMonth(dTime);
    }
}
