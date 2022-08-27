package com.example.servlet;

import com.example.bean.GoodSell;
import com.example.bean.GoodSellItem;
import com.example.bean.Goods;
import com.example.service.GoodReadySellService;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/goodsellcom")
public class GoodReadySellServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GoodReadySellService goodReadySellService = new GoodReadySellService();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        if (req.getSession().getAttribute("user1") == null) {
            return;
        }

        String goodsId = req.getParameter("goodsId");
        String goodsNum = req.getParameter("goodsNum");
        String goodsPrice = req.getParameter("goodsPrice");
        String purchaser = req.getParameter("purchaser");


        List<Goods> goodsList = new ArrayList<>();
        String[] gIds = goodsId.split(",");
        String[] gNums = goodsNum.split(",");
        String[] gPrices = goodsPrice.split(",");

        //在这里没有做错误处理，即传入数据不匹配

        //将本次所有购买的货物，数量，单价价格都放在goodsList里
        for (int i = 0; i < gIds.length; i++) {
            //查找产品总表是否存在
            Goods goods = goodReadySellService.getGoods(Integer.parseInt(gIds[i]));
            if(goods != null) {
                if(goods.getStock() < Integer.parseInt(gNums[i])) {
                    System.out.println("售卖的产品库存数量不够");
                    resp.getWriter().println("售卖的产品库存数量不够，请重新检查");
                    return;
                }else {
                    goods.setSellGoodsNum(Integer.parseInt(gNums[i]));
                    goods.setSellGoodsPrice(Integer.parseInt(gPrices[i]));
                    goodsList.add(goods);
                }
            }else {
                System.out.println("售卖的产品不存在");
                resp.getWriter().println("售卖的产品不存在，请重新检查");
                return;
            }
        }


        //创建出货单
        GoodSell goodSell = new GoodSell();

        goodSell.setId(String.valueOf(System.currentTimeMillis()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        goodSell.setCreateTime(LocalDateTime.now().format(formatter));


        int totalMoney = 0;
        for (Goods goods : goodsList) {
            //每一个产品实际上就是一个item
            GoodSellItem goodSellItem = new GoodSellItem();
            goodSellItem.setGoodsellId(goodSell.getId());
            goodSellItem.setGoodsId(goods.getId());
            goodSellItem.setGoodsName(goods.getName());
            goodSellItem.setGoodsType(goods.getType());
            goodSellItem.setGoodsSellStock(goods.getSellGoodsNum());
            goodSellItem.setGoodsUnit(goods.getUnit());
            goodSellItem.setGoodsPrice(goods.getSellGoodsPrice());

            goodSell.goodSellItemList.add(goodSellItem);

            //Money 代表每个产品的总金额
            int Money = goods.getSellGoodsNum() * goods.getSellGoodsPrice();
            //totalMoney ： 总共的钱
            totalMoney += Money;
        }

        goodSell.setAllPrice(totalMoney);
        goodSell.setPurchaser(purchaser);

        System.out.println("产品出库总表：");
        System.out.println(goodSell);

        HttpSession session2 = req.getSession();
        session2.setAttribute("goodSell",goodSell);

        HttpSession session3 = req.getSession();
        session3.setAttribute("goodsList",goodsList);


        //如果是跳转到另一个网页的话，对应的数据不好拿到，  所以在这里直接进行打印网页
        //通过响应体对前端传入数据。
        resp.getWriter().println("<html>");
        resp.getWriter().println("<p>"+"【订单编号】:"+goodSell.getId()+"</p>");
        resp.getWriter().println("<p>"+"【创建时间】:"+goodSell.getCreateTime()+"</p>");

        resp.getWriter().println("<p>"+"编号  "+"名称   "+"数量  "+"单位  "+"单价（元）   "+"</p>");
        resp.getWriter().println("<ol>");
        for (GoodSellItem goodSellItem  : goodSell.goodSellItemList) {
            resp.getWriter().println("<li>" + goodSellItem.getGoodsName() +" " + goodSellItem.getGoodsSellStock()+ " "+
                    goodSellItem.getGoodsUnit()+" " + goodSellItem.getGoodsPrice()+"</li>");
        }
        resp.getWriter().println("</ol>");
        resp.getWriter().println("<p>"+"【总金额】:"+goodSell.getAllPrice() +"</p>");
        resp.getWriter().println("<p>"+"【购买方】:"+goodSell.getPurchaser() +"</p>");
        resp.getWriter().println("<a href=\"goodSellServlet\">确认</a>");
        resp.getWriter().println("<a href= \"index.html\">取消</a>");
        resp.getWriter().println("</html>");


    }
}
