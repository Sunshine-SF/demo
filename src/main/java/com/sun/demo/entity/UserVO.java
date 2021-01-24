package com.sun.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserVO extends User {
    private List<Review> reViews;
}
