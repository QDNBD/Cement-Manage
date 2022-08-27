package com.example.service;

import com.example.bean.Materials;
import com.example.dao.MaterialsConsueBrowseDao;

import java.util.List;

public class MaterialsConsueBrowseService {
    public List<Materials> selectMaterials() {
        MaterialsConsueBrowseDao materialsConsueBrowseDao = new MaterialsConsueBrowseDao();
        return materialsConsueBrowseDao.selectMaterials();
    }
}
