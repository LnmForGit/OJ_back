package com.oj.mapper;

import com.oj.entity.User;
import com.oj.mapper.provider.UserProvider;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;
/**
 * @author lixu
 * @Time 2019年3月9日 15点21分
 * @Description User表数据库操作接口
 */
//注册Mapper
@Mapper
public interface UserMapper {

    //编写插入语句
    @Insert("insert into user(user_name, user_password, user_sex, user_phone, user_mail) values(#{userName},#{userPassword},#{sex},#{userPhone},#{userMail})")
    //保存插入对象之后将自增主键set到保存的对象当中
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    //插入用户操作
    public int save(User user);

    //通过UserProvider类中的getQuerySql()方法动态构建查询语句
    @SelectProvider(type=UserProvider.class, method = "getQuerySql")
    //查询用户结果，返回Map类型List
    public List<Map> queryUserMapList(@Param("condition")User user);

    //删除用户
    @Delete("delete from user where id = #{id}")
    public void deleteUser(User user);

    //更新用户
    @Update("update user set user_name = #{userName}, user_sex = #{sex}, user_phone = #{userPhone}, user_mail = #{userMail} where id = #{id}")
    public int update(User user);

}
