package com.example.service;

import com.example.bean.Materials;
import com.example.dao.MaterialsDao;

import java.util.List;

public class MaterialAllBrowService {
    public List<Materials> Selects() {
        MaterialsDao materialsDao = new MaterialsDao();
        return materialsDao.Selects();
    }
}
