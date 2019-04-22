package com.oj.mapper.testing;

import com.oj.entity.testing.Case;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhouli
 */
@Mapper
public interface CaseMapper {

    //通过题目id获取测试信息
    @Select("SELECT id,in_put,out_put FROM teach_test_data WHERE problem_id = #{id}")
    List<Map> getProblemById(@Param("id")String id);

    //插入一条测试信息
    @Insert("insert into teach_test_data(problem_id,in_put,out_put,time,description) values( #{problem_id},#{in_put},#{out_put},#{time},#{description})")
    int addCase(Case c);
    //更新一条测试信息
    @Update("update teach_test_data set in_put = #{in_put},out_put = #{out_put} where id = #{id}")
    int alterCase(Case c);

    //删除一条测试信息
    @Delete("delete from teach_test_data where id = #{id}")
    void deleteCase(String id);

}
