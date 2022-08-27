package com.example.servlet;


import com.example.bean.Goods;
import com.example.dao.GoodsDao;
import com.example.tool.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/yeargoodsExportExcel")
public class YearGoodsExportExcelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user1") == null) {
            return;
        }
        System.out.println("进入导出程序");
        GoodsDao goodsDao = new GoodsDao();
        GetTime getTime = new GetTime();
        List<Goods> goodsList = goodsDao.selectYear(getTime.getNowTime().substring(0,4));
        String[] title = {"ID", "产品名称", "产品类型", "产品入库数量", "产品出库数量", "库存数量", "产品单位", "产品总价格(元)", "创建时间"};
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String name = LocalDateTime.now().format(formatter);
        String filename = name + "yearGoodsAll.xls";
        String sheetName = "sheet1";
        String[][] content = new String[goodsList.size()][title.length];
        try {
            int i = 0;
            for (Goods goods : goodsList) {
                content[i][0] = String.valueOf(goods.getId());
                content[i][1] = goods.getName();
                content[i][2] = goods.getType();
                content[i][3] = String.valueOf(goods.getInstock());
                content[i][4] = String.valueOf(goods.getSellstock());
                content[i][5] = String.valueOf(goods.getStock());
                content[i][6] = goods.getUnit();
                content[i][7] = String.valueOf(goods.getPrice());
                content[i][8] = goods.getCreateTime();
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbookGoods(sheetName, title, content, null);
        try {
            // 响应到客户端
            this.setResponseHeader(resp, filename);
            OutputStream os = resp.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 向客户端发送响应流方法
     *
     * @param response
     * @param fileName
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("进入导出响应程序");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
