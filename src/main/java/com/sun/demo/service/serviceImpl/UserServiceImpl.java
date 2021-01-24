package com.sun.demo.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.demo.entity.Review;
import com.sun.demo.entity.User;
import com.sun.demo.entity.UserVO;
import com.sun.demo.mapper.ReViewDao;
import com.sun.demo.mapper.UserDao;
import com.sun.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReViewDao reViewDao;


    @Override
    public User getUserById(Integer id) {
        return userDao.selectById(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.selectList(null);
    }

    @Override
    public int updateUser(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        userDao.updateById(user);
        List<Review> reViews = userVO.getReViews();
        for (Review reView : reViews) {
            reView.setUserId(user.getId());
            reViewDao.updateById(reView);
        }
        return 1;
    }

    @Override
    public int addUser(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        userDao.insert(user);
        user.getId();
        List<Review> reViews = userVO.getReViews();
        reViews.forEach(v->{
            v.setUserId(user.getId());
            reViewDao.insert(v);
        });
        return 1;
    }

    @Override
    public int delUser(List<User> list) {
        for (User user : list) {
            userDao.deleteById(user.getId());
            QueryWrapper<Review> qw = new QueryWrapper<>();
            qw.eq("User_id", user.getId());
            reViewDao.delete(qw);
        }
        return 1;
    }

    @Override
    public int delById(int id) {
        return userDao.deleteById(id);
    }

    @Override
    public List<User> queryList(String username, String recoveryTime, String reviewTime,String reviewDay,String ssDay) {
        String sql = "select * from user where 1 = 1 ";
        if (!StringUtils.isEmpty(username)) {
            sql += "and username = \"" + username + "\" ";
        }
        if (!StringUtils.isEmpty(recoveryTime)) {
            sql += "and date(recovery_Time) = STR_TO_DATE('" + recoveryTime + "','%Y-%m-%d')";
        }
      /*  if (!StringUtils.isEmpty(reviewTime)) {
            sql += " and review_Time = STR_TO_DATE('" + reviewTime + "','%Y-%m-%d')";
        }
        if (!StringUtils.isEmpty(reviewDay)) {
            sql += " and 0 <= datediff(NOW(),review_Time) <="+Integer.parseInt(reviewDay);
        }*/
        if (!StringUtils.isEmpty(ssDay)) {
            sql += " and 0 <= datediff(NOW(),review_Time) <="+Integer.parseInt(ssDay);
        }
        return userDao.queryList(sql);
    }

}
