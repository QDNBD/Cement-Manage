package com.example.bean;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GoodSell {
    private String id;
    private Integer allPrice;
    private String purchaser;
    private String createTime;

    public List<GoodSellItem> goodSellItemList = new ArrayList<>();
}
