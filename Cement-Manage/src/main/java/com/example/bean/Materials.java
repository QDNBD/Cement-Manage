package com.example.bean;

import lombok.Data;

@Data
public class Materials {
    private Integer id;
    private String name;
    private String type;
    private Integer instock;
    private Integer stock;
    private Integer usestock;
    private String unit;
    private Integer price;
    private String createTime;

}
