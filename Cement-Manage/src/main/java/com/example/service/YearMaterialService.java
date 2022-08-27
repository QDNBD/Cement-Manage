package com.example.service;

import com.example.bean.Materials;
import com.example.dao.MaterialsDao;

import java.util.List;

public class YearMaterialService {
    public List<Materials> selectYear(String dYear) {
        MaterialsDao materialsDao = new MaterialsDao();
        return materialsDao.selectYear(dYear);
    }
}
