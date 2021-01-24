package com.sun.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.demo.entity.Review;
import com.sun.demo.entity.User;
import com.sun.demo.entity.UserVO;
import com.sun.demo.mapper.ReViewDao;
import com.sun.demo.mapper.UserDao;
import com.sun.demo.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReViewDao reViewDao;

    @GetMapping("/getUser/{id}")
    public UserVO getUser(@PathVariable("id") Integer id) {
        UserVO userVO = new UserVO();
        User user = userService.getUserById(id);
        BeanUtils.copyProperties(user, userVO);
        QueryWrapper<Review> qw = new QueryWrapper<>();
        qw.eq("user_id", user.getId());
        List<Review> reviews = reViewDao.selectList(qw);
        userVO.setReViews(reviews);
        return userVO;
    }

    @GetMapping("/getAllUser")
    public Map getAll(int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, limit);
        List<User> list = userService.getAll();
        PageInfo pageInfo = new PageInfo(list, page);
        map.put("code", 0);
        map.put("massage", "200");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @PostMapping("/updateUser")
    public int updateUser(@RequestBody UserVO userVO) {
        userService.updateUser(userVO);
        return 1;
    }

    @GetMapping("/del/{id}")
    public int delUserById(@PathVariable("id") int id) {
        return userService.delById(id);
    }

    @PostMapping("/addUser")
    public int addUser(@RequestBody UserVO userVO) {
        userService.addUser(userVO);
        return 1;
    }

    @PostMapping("/delUser")
    public int delUser(@RequestBody List<User> list) {
        userService.delUser(list);
        return 1;
    }

    @GetMapping("/selectAll")
    public Map selectAll(int page, int limit, String username, String recoveryTime, String reviewTime, String reviewDay, String ssDay) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, limit);
        List<User> list = userService.queryList(username, recoveryTime, reviewTime, reviewDay, ssDay);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String nowDate = formatter.format(date);
        for (User user : list) {
            QueryWrapper<Review> qw = new QueryWrapper<>();
            qw.eq("user_id", user.getId());
            qw.gt("review_Date", nowDate).orderByAsc("review_Times").last("limit 1");
            //查询下次复查的时间 与当前时间对比
            List<Review> reviews = reViewDao.selectList(qw);
            if (!CollectionUtils.isEmpty(reviews)) {
                Review review = reviews.get(0);
                user.setNextTime(review.getReviewDate());
                //如果还未进行过复查
                if (review.getReviewTimes() == 0) {
                    user.setLastMatter("未进行过复查");
                } else {
                    //查出上次的复查信息
                    QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("user_id", user.getId());
                    queryWrapper.eq("review_Times", review.getReviewTimes() - 1);
                    List<Review> reviews1 = reViewDao.selectList(queryWrapper);
                    Review review1 = reviews1.get(0);
                    user.setLastTime(review1.getReviewDate());
                    if ("0".equals(review1.getFlag())) {
                        user.setLastMatter("未复查");
                    } else {
                        user.setLastMatter("已复查");
                    }
                }
            } else {
                //与当前时间相比已经全部复查 差出最后一次复查的情况
                QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", user.getId());
                queryWrapper.orderByAsc("review_Times");
                List<Review> reviews1 = reViewDao.selectList(queryWrapper);
                if (!CollectionUtils.isEmpty(reviews1)) {
                    Review review = reviews1.get(0);
                    user.setLastTime(review.getReviewDate());
                    if ("0".equals(review.getFlag())) {
                        user.setLastMatter("未复查");
                    } else {
                        user.setLastMatter("已复查");
                    }
                }
            }

        }

        PageInfo pageInfo = new PageInfo(list, page);
        map.put("code", 0);
        map.put("massage", "200");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

}
