package com.example.servlet;

import com.example.bean.GoodSell;
import com.example.bean.GoodSellItem;
import com.example.dao.GoodSellOrderBrowDao;
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
import java.util.List;


@WebServlet("/goodSellOrderExportExcel")
public class GoodSellOrderExportExcelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user1") == null) {
            return;
        }
        System.out.println("进入导出程序");
        String orderNumber = req.getParameter("ordernumber");
        GoodSellOrderBrowDao goodSellOrderBrowDao = new GoodSellOrderBrowDao();

        GetTime getTime = new GetTime();
        List<GoodSell> goodSells = goodSellOrderBrowDao.selectMonthNumber(orderNumber);
        //如果goodSells没有该id
        if(goodSells.isEmpty()) {
            resp.getWriter().println("没有找到该产品出库编号！！！");
            System.out.println("没有找到该产品出库编号！！！");
            return;
        }
        String[] title = {"产品名称", "产品类型", "产品售出数量", "产品售出单价(元)", "出库单号", "总金额(元)", "购买方", "购买时间"};
        String filename = orderNumber + "goodSellOrder.xls";
        String sheetName = "sheet1";
        int colnum = 0;
        for (GoodSell goodSell : goodSells) {
            colnum = goodSell.getGoodSellItemList().size();
        }
        String[][] content = new String[colnum][4];
        String goodSellId = "";
        Integer goodSellAllPrice = 0;
        String goodSellPurchaser = "";
        String goodSellCreateTime = "";
        try {
            int i = -1;
            for (GoodSell goodSell : goodSells) {
                for (GoodSellItem goodSellItem : goodSell.getGoodSellItemList()) {
                    i++;
                    content[i][0] = String.valueOf(goodSellItem.getGoodsName());
                    content[i][1] = String.valueOf(goodSellItem.getGoodsType());
                    content[i][2] = String.valueOf(goodSellItem.getGoodsSellStock());
                    content[i][3] = String.valueOf(goodSellItem.getGoodsPrice());
                }
            }
            for (GoodSell goodSell : goodSells) {
                goodSellId = String.valueOf(goodSell.getId());
                goodSellAllPrice = goodSell.getAllPrice();
                goodSellPurchaser =  String.valueOf(goodSell.getPurchaser());
                goodSellCreateTime =  goodSell.getCreateTime();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbookSellOrder(sheetName, title, content, null, goodSellId, goodSellAllPrice, goodSellPurchaser, goodSellCreateTime);
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
