package com.he.community.service;
/**
 * 业务逻辑层
 */

import com.he.community.mapper.UserMapper;
import com.he.community.model.User;
import com.he.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        /*User newUser = userMapper.findByAccountId(user.getAccountId());*/
        /*System.out.println("cou:" + users.size());*/
        /*System.out.println("存在OK");*/
        if(users.size() == 0){
            //插入
            System.out.println("开始插入");
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            System.out.println("插入完");
        }else{
            //更新
            User newUser = users.get(0);

            User updateUser = new User();
            updateUser.setId(newUser.getId());
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            /*UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(newUser.getId());*/
            /*System.out.println("updateUser:" + updateUser.getId());*/
            /*userMapper.update(newUser);*/
            userMapper.updateByPrimaryKeySelective(updateUser);
            /*System.out.println("更新完");*/
        }

    }
}
