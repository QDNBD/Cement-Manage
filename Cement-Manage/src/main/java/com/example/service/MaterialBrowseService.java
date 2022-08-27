package com.example.service;


import com.example.bean.Materials;
import com.example.dao.MaterialBrowseDao;

import java.util.List;

public class MaterialBrowseService {

    MaterialBrowseDao materialBrowseDao = new MaterialBrowseDao();


    public List<Materials> selectMaterials() {
        return materialBrowseDao.selectMaterials();
    }
}
