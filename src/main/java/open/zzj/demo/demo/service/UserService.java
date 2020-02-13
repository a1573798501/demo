package open.zzj.demo.demo.service;


import open.zzj.demo.demo.mapper.UserMapper;
import open.zzj.demo.demo.model.User;
import open.zzj.demo.demo.model.UserExample;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            //插入数据库
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //更新数据库
            User dbUser = users.get(0);
            User userUpdate = new User();
            userUpdate.setGmtModified(System.currentTimeMillis());
            userUpdate.setAvatarUrl(user.getAvatarUrl());
            userUpdate.setName(user.getName());
            userUpdate.setToken(user.getToken());
            UserExample userExampleUpdate = new UserExample();
            userExampleUpdate.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(userUpdate,userExampleUpdate);
        }
    }
}
