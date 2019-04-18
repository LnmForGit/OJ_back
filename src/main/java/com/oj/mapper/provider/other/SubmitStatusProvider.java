package com.oj.mapper.provider.other;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

public class SubmitStatusProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public String getQuerySql(Map<String,Object> params){
        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append("select a.problem_id,a.name,b.state_name,c.language_name,a.submit_time,a.submit_memory,a.submit_code_length,a.submit_date\n" +
                "from (SELECT  a.problem_id,b.name,a.submit_state,a.submit_language,a.submit_time,a.submit_memory,a.submit_code_length,a.submit_date  FROM teach_submit_code as a ,teach_students as b where a.user_id=b.id order by a.submit_date desc limit 100)as a\n" +
                "join teach_submit_state as b on a.submit_state=b.id\n" +
                "join teach_submit_language as c on a.submit_language=c.id\n" +
                "order by a.submit_date desc");
        log.info(sql.toString());
        return sql.toString();

    }
}
