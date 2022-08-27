package com.example.bean;


import lombok.Data;

@Data
public class Goods {

    private Integer id;
    private String name;
    private String type;
    private Integer instock;
    private Integer sellstock;
    private Integer stock;
    private String unit;
    private Integer price;
    private String createTime;


    private Integer sellGoodsNum;
    private Integer sellGoodsPrice;


}
