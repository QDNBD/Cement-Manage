package com.example.servlet;


import com.example.bean.GoodSell;
import com.example.bean.Goods;
import com.example.service.GoodSellService;
import lombok.SneakyThrows;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/goodSellServlet")
public class GoodSellServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");

        GoodSellService goodSellService = new GoodSellService();

        HttpSession session = req.getSession();
        GoodSell goodSell =(GoodSell)session.getAttribute("goodSell");
        System.out.println("预出库产品：");
        System.out.println(goodSell);

        List<Goods> goodsList = (List<Goods>) session.getAttribute("goodsList");
        System.out.println("库存产品：");
        System.out.println(goodsList);

        //将订单内的数据写入数据库当中
        //提交
        boolean flg = goodSellService.commitDate(goodSell);

        /*
         * 1、更新哪些商品的库存？
         *    goodsList
         */
        if(flg) {
            //更新库存
            for (Goods goods : goodsList) {
                boolean isUpdate = goodSellService.updateGoods(goods);
                if(isUpdate) {
                    System.out.println("isUpdate更新库存成功！");
                }else {
                    System.out.println("isUpdate更新库存失败");
                    return;
                }
                boolean ismonthUpdate = goodSellService.monthUpdate(goods);
                if(ismonthUpdate) {
                    System.out.println("ismonthUpdate更新库存成功！");
                }else {
                    System.out.println("ismonthUpdate更新库存失败");
                    return;
                }
            }
        }else {
            System.out.println("插入订单失败！");
            resp.getWriter().println("产品出库失败！！！");
            return;
        }
        resp.getWriter().println("产品出库成功！！！");
        resp.getWriter().println("3秒后跳转到主页面");
        if(req.getSession().getAttribute("user1") != null){
            resp.sendRedirect("index.html");
        }else {
            resp.sendRedirect("indexu.html");
        }
    }
}
