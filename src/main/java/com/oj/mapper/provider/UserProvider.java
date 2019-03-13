package com.oj.mapper.provider;

import com.oj.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author lixu
 * @Time 2019年3月11日 18点15分
 * @Description 与User表相关动态sql生成
 */
public class UserProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public String getQuerySql(Map<String, Object> params){
        User user = (User) params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ( @i := @i + 1 ) AS num, t1.*  ");
        sql.append(" FROM ( SELECT a.id, a.user_name, a.user_sex, a.user_phone, a.user_mail FROM USER a WHERE 1=1 ");
        if (user.getId() != 0){
            sql.append(" AND a.id = '"+user.getId()+"' ");
        }
        if (! StringUtils.isEmpty(user.getUserName())){
            sql.append(" AND a.user_name like '%"+user.getUserName()+"%' ");
        }
        if (user.getSex() != 0){
            sql.append(" AND a.user_sex = '"+user.getSex()+"' ");
        }
        if (! StringUtils.isEmpty(user.getUserPhone())){
            sql.append(" AND a.user_phone like '%"+user.getUserPhone()+"%' ");
        }
        if (! StringUtils.isEmpty(user.getUserMail())){
            sql.append(" AND a.user_mail like '%"+user.getUserMail()+"%' ");
        }
        sql.append(" ) t1,( SELECT @i := 0 ) t2 ");
        log.info(sql.toString());
        return sql.toString();

    }
}
