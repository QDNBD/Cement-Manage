package com.example.service;

import com.example.bean.Materials;
import com.example.dao.MaterialConsumeDao;
import com.example.dao.MaterialsDao;
import com.example.dao.MaterialsMonthDao;
import com.example.servlet.GetTime;


public class MaterialConsumeService {


    public int ConsumeUpdate(String name, String atype, String stock, String unit) {
        MaterialConsumeDao materialConsumeDao = new MaterialConsumeDao();
        MaterialsDao materialsDao = new MaterialsDao();
        MaterialsMonthDao materialsMonthDao = new MaterialsMonthDao();
        GetTime getTime = new GetTime();
        try {
            //判断总原材料库是否有该材料
            int retid = materialsDao.Select(name,atype);
            if(retid == 0) {
                System.out.println("原材料库中没有该出库材料");
                return 0;
            }

            //该材料库存是否满足出库数量
            Materials materials = new Materials();
            materials = materialsDao.SelectAll(retid);

            if(materials.getStock() < Integer.parseInt(stock)) {
                System.out.println("原材料库库存数量不够");
                return 0;
            }

            //如果满足，完成对总原材料表的更新
            materialConsumeDao.UpdateStock(retid,materials.getStock()-Integer.parseInt(stock), materials.getUsestock()+ Integer.parseInt(stock));

            //月表
            Materials materialsMon = new Materials();
            materialsMon = materialsMonthDao.SelectStock(name,atype,getTime.getNowTime());
            if(materialsMon.getId() == null) {
                materialsMonthDao.ConsumeAll(name,atype,stock,unit,getTime.getNowTime());
            }else {
                materialsMonthDao.UpdateConsumeStock(materialsMon.getId(), materialsMon.getUsestock() + Integer.parseInt(stock), getTime.getNowTime());
            }


            //出库记录
            int ret = materialConsumeDao.ConUpdate(name, atype, stock,materials.getStock()-Integer.parseInt(stock), unit);

            return ret;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
