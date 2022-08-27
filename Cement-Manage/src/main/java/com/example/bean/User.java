package com.example.bean;


import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer uType;
    private String nickname;
    private String email;
    private String sex;
    private Integer age;
    private String createTime;
}
