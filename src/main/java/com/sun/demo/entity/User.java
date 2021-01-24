package com.sun.demo.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.text.Format;
import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private Integer age;
    private String sex;
    private String tel;
    private String operate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recoveryTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewTime;
    private String pharmacy;
    private String specialItem;
    @TableField(exist = false)
    private String leftrecoveryDay;
    @TableField(exist = false)
    private String leftReviewDay;
    //上次复查时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date lastTime;
    //上次复查情况
    @TableField(exist = false)
    private String lastMatter;
    //下次复查时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date nextTime;

    public User(Integer id, String username, Integer age, String sex, String tel, String operate, Date recoveryTime, Date reviewTime, String pharmacy, String specialItem) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.sex = sex;
        this.tel = tel;
        this.operate = operate;
        this.recoveryTime = recoveryTime;
        this.reviewTime = reviewTime;
        this.pharmacy = pharmacy;
        this.specialItem = specialItem;
    }

    public User() {
    }
}
