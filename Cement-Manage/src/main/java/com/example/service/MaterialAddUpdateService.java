package com.example.service;

import com.example.bean.Materials;
import com.example.dao.MaterialsAddDao;
import com.example.dao.MaterialsDao;
import com.example.dao.MaterialsMonthDao;
import com.example.servlet.GetTime;

public class MaterialAddUpdateService {
    public int AddUpdate(int matrialid, String name, String type, String stock, String unit, String price) {
        try {
            MaterialsAddDao materialsAddDao = new MaterialsAddDao();
            MaterialsDao materialsDao = new MaterialsDao();
            MaterialsMonthDao materialsMonthDao = new MaterialsMonthDao();
            Materials materials = new Materials();
            Materials materialsRecord = new Materials();
            Materials materialsMon = new Materials();
            GetTime getTime = new GetTime();
            //拿到Id检查 materialrecord 表是否存在该Id
            materialsRecord = materialsAddDao.SelectAll(matrialid);

            if(materialsRecord.getName().equals("")) {
                System.out.println("不存在该原材料入库记录");
                return 0;
            }
            //存在，判断该修改的是否为同名，同型号
            if(materialsRecord.getName().equals(name) && materialsRecord.getType().equals(type)) {
                //如果是
                //拿到总表的之前的数量
                materials = materialsDao.SelectStock(name, type);
                //更新数量
                materialsDao.MaterialsUpdate(materials.getId(), (materials.getInstock() - materialsRecord.getStock() + Integer.parseInt(stock)),
                        (materials.getStock() - materialsRecord.getStock() + Integer.parseInt(stock)),
                        materials.getPrice() - (materialsRecord.getPrice() * materialsRecord.getStock()) + (Integer.parseInt(stock) * Integer.parseInt(price)));

            }else {
                //如果不是，将material表中删除该条入库信息
                materials = materialsDao.SelectStock(materialsRecord.getName(), materialsRecord.getType());
                materialsDao.MaterialsUpdate(materials.getId(), (materials.getInstock() - materialsRecord.getStock()),
                        (materials.getStock() - materialsRecord.getStock()), materials.getPrice() - (materialsRecord.getPrice() * materialsRecord.getStock()));

                //判断总表是否已经写入本次修改的原材料
                //查询原材料总表是否有该原料
                int retUId = materialsDao.Select(name,type);
                System.out.println(retUId);
                if(retUId == 0) {
                    //说明没有直接插入
                    materialsDao.Addall(name, type, stock, unit, price);
                }else {
                    //说明有
                    //通过id获取产品总表的入库数量
                    Materials materials1 = new Materials();
                    materials1 = materialsDao.SelectAll(retUId);

                    //将本次数量与原始数量进行相加写入数据库
                    materialsDao.MaterialsUpdate(retUId,materials1.getInstock()+ Integer.parseInt(stock), materials1.getStock()+Integer.parseInt(stock),
                            materials1.getPrice() + (Integer.parseInt(stock)* Integer.parseInt(price)) );
                }

            }

            //更新原材料月表
            materialsMon = materialsMonthDao.SelectStock(materialsRecord.getName(),materialsRecord.getType(),materialsRecord.getCreateTime().substring(0,7));
            //先减去
            materialsMonthDao.UpdateStock(materialsMon.getId(), materialsMon.getInstock() - materialsRecord.getStock(),
                    materialsMon.getPrice() - (materialsRecord.getPrice() * materialsRecord.getStock()),
                    materialsRecord.getCreateTime().substring(0,7));
            //查询原材料月表是否有该原料
            materialsMon = materialsMonthDao.SelectStock(name,type,getTime.getNowTime());
            if(materialsMon.getId() == null) {
                materialsMonthDao.Addall(name,type,stock,unit,price,getTime.getNowTime());
            }else {
                materialsMonthDao.UpdateStock(materialsMon.getId(), materialsMon.getInstock() + Integer.parseInt(stock),
                        materialsMon.getPrice() + (Integer.parseInt(stock) * Integer.parseInt(price)), getTime.getNowTime());
            }


            //更新本次修改记录 goodrecord 表
            int ret = materialsAddDao.AddUpdate(matrialid, name, type, stock, unit, price);
            return ret;
        }catch (Exception e) {
            System.out.println("MaterialsAddUpdateService 出错");
            e.printStackTrace();
        }
        return 0;
    }
}
