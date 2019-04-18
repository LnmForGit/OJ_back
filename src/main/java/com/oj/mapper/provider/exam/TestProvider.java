package com.oj.mapper.provider.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author xielanning
 * @Time 2019年4月1日 10点18分
 * @Description 考试管理provider类
 */
public class TestProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public String getTestInfoSql(Map<String, Object> params){
        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("a.id, ");
        sql.append("a.name, ");
        sql.append("'考试' as kind, ");
        sql.append("FROM_UNIXTIME(a.`start`) as start_time, ");
        sql.append("FROM_UNIXTIME(a.`end`) as end_time ");
        sql.append("FROM ");
        sql.append("teach_test a ");
        sql.append("INNER JOIN ( ");
        sql.append("SELECT DISTINCT ");
        sql.append("test_id ");
        sql.append("FROM ");
        sql.append("teach_test_class ");
        sql.append("WHERE ");
        sql.append("class_id IN ( ");
        sql.append("SELECT class_id ");
        sql.append("FROM teach_course_class ");
        sql.append("WHERE course_id in ( ");
        sql.append("SELECT course_id ");
        sql.append("FROM teach_admin_course ");
        sql.append("WHERE admin_id = '"+info.get("user_id")+"' ");
        sql.append(") ) ");
        sql.append(") b ON a.id = b.test_id ");
        sql.append("WHERE a.kind = '1' ");
        if (!StringUtils.isEmpty(info.get("testName"))){
            sql.append("AND a.name like '%"+info.get("testName")+"%' ");
        }
        sql.append("ORDER BY ");
        sql.append("a.id DESC ");
        log.info(sql.toString());
        return sql.toString();
    }
}
