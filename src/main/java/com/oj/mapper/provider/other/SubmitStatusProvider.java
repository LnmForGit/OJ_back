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
        sql.append(" 	SELECT			 ");
        sql.append(" 			a.problem_id,	 ");
        sql.append(" 			b.NAME,	 ");
        sql.append(" 			a.submit_state,	 ");
        sql.append(" 			a.submit_language,	 ");
        sql.append(" 			a.submit_time,	 ");
        sql.append(" 			a.submit_memory,	 ");
        sql.append(" 			a.submit_code_length,	 ");
        sql.append(" 			FROM_UNIXTIME(a.submit_date) as submit_date	 ");
        sql.append(" 		FROM		 ");
        sql.append(" 			teach_submit_code AS a,	 ");
        sql.append(" 			teach_students AS b 	 ");
        sql.append(" 		WHERE		 ");
        sql.append(" 			a.user_id = b.id 	 ");
        if(!StringUtils.isEmpty(info.get("problem_id"))){
            sql.append("		AND a.problem_id like '%"+info.get("problem_id")+"%' ");
        }
        if(!StringUtils.isEmpty(info.get("account"))){
            sql.append("		AND b.name like '%"+info.get("account")+"%' ");
        }
        if(!StringUtils.isEmpty(info.get("submit_state"))){
            sql.append("		AND a.submit_state = '"+info.get("submit_state")+"' ");
        }
        sql.append(" 		ORDER BY		 ");
        sql.append(" 			a.submit_date DESC 	 ");
        sql.append(" 			LIMIT "+info.get("start")+","+info.get("count")+"	 ");
        log.info(sql.toString());
        return sql.toString();

    }

    public String selectRecordsFiltered(Map<String,Object> params){
        Map<String, String> info = (Map<String, String>)params.get("condition");
        StringBuffer sql = new StringBuffer();
        sql.append(" 	SELECT	count(*)		 ");
        sql.append(" 		FROM		 ");
        sql.append(" 			teach_submit_code AS a,	 ");
        sql.append(" 			teach_students AS b 	 ");
        sql.append(" 		WHERE		 ");
        sql.append(" 			a.user_id = b.id 	 ");
        if(!StringUtils.isEmpty(info.get("problem_id"))){
            sql.append("		AND a.problem_id like '%"+info.get("problem_id")+"%' ");
        }
        if(!StringUtils.isEmpty(info.get("account"))){
            sql.append("		AND b.name like '%"+info.get("account")+"%' ");
        }
        if(!StringUtils.isEmpty(info.get("submit_state"))){
            sql.append("		AND a.submit_state = '"+info.get("submit_state")+"' ");
        }
        sql.append(" 		ORDER BY		 ");
        sql.append(" 			a.submit_date DESC 	 ");

        log.info(sql.toString());
        return sql.toString();
    }

}
