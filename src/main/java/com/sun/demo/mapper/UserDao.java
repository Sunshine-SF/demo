package com.sun.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {


    @Select("${sql}")
    List<User> queryList(@Param("sql") String sql);


}
