package com.oj.service.serviceImpl;

import com.oj.entity.User;
import com.oj.mapper.UserMapper;
import com.oj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
/**
 * @author lixu shijian
 * @Time 2019年3月9日 15点21分
 * @Description 创建Service接口实现，进行Mapper接口调用和数据预处理操作
 */

//向框架中注册Service
@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    //注入UserMapper
    @Autowired(required = false)
    private UserMapper mapper;

    /**
     * 返回Map类型的List接口方法实现
     * @param user
     * @author lixu
     * @return List<Map>
     */
    @Override
    public List<Map> queryUserMapList(User user) {
        return mapper.queryUserMapList(user);
    }

    /**
     * 删除用户
     * @param user
     * @author lixu
     */
    @Override
    public void deleteUser(User user) {
        mapper.deleteUser(user);
    }

    /**
     * 新增或修改用户
     * @param user
     * @author lixu
     */
    //Transactional为开启数据库事务标识
    @Transactional
    @Override
    public void demoSaveOrUpdateUser(User user) {
        //当id为0时，代表未传入用户ID，则可判定为是新增用户可以保存
        if(0 == user.getId()){
            try {
                user.setUserPassword("123456");
                mapper.save(user);
            }catch (Exception e){
                log.error(e.getMessage());
            }

        }else{
            try{
                mapper.update(user);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }
}
