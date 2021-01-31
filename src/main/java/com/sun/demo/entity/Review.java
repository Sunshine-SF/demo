package com.sun.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Review {
    @TableId(type = IdType.AUTO)
    private int id;
    private int userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewDate;
    private int reviewTimes;
    private String flag;
    private String remark;
}
