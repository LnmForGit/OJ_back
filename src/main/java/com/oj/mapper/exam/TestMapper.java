package com.oj.mapper.exam;

import com.oj.entity.exam.Test;
import com.oj.mapper.provider.exam.TestProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author xielanning
 * @Time 2019年4月17日 10点18分
 * @Description 考试管理Mapper接口
 */


@Mapper
public interface TestMapper {

    //获取考试信息列表
    @SelectProvider(type= TestProvider.class, method = "getTestInfoSql")
    public List<Map> getTestInfo(@Param("condition")Map<String, String> params);



    //获取试题信息列表
    @Select("SELECT id, name, IF(public='on','是','否') as isShow FROM teach_problems where name !='' ORDER BY id DESC")
    public List<Map> loadPreSelectQuestion();

    //获取班级列表
    @Select("SELECT b.id, b.name FROM teach_course_class a INNER JOIN teach_class b on a.class_id = b.id  " +
            "WHERE course_id in (SELECT course_id FROM teach_admin_course " +
            "WHERE admin_id = #{user_id}) ORDER BY b.id ")
    public List<Map> loadPreSelectClass(@Param("user_id") String user_id);

    //获取机房列表
    @Select("SELECT id, location, ip FROM teach_ip ORDER BY id DESC")
    public List<Map> loadPreSelectJroom();

    //通过ID获取考试信息
    @Select("SELECT id, name, FROM_UNIXTIME(start) as start, FROM_UNIXTIME(end) as end, is_ip, only_ip, description  FROM teach_test WHERE id = #{id}")
    public Map getTestInfoById(String id);

    //通过ID获取已选择试题信息
    @Select("SELECT b.id, b.name, a.score FROM teach_test_problems a INNER JOIN teach_problems b on a.pid = b.id WHERE a.tid = #{id} ORDER BY b.id")
    public List<Map> getSelectedQueListById(String id);

    //通过ID获取已选择班级信息
    @Select("SELECT b.id, b.name FROM teach_test_class a INNER JOIN teach_class b on a.class_id = b.id WHERE a.test_id = #{id} ORDER BY b.id")
    public List<Map> getSelectedClassListById(String id);

    //通过ID获取已选择机房信息
    @Select("SELECT b.id, b.location, b.ip FROM teach_test_ip a INNER JOIN teach_ip b on a.iid = b.id where a.tid = #{id} ORDER BY b.id")
    public List<Map> getSelectedJroomListById(String id);

    @Insert("INSERT INTO teach_test (NAME, START, END, description, kind, is_ip, only_ip, report, admin_id) " +
            "VALUES(#{name},UNIX_TIMESTAMP(#{start}),UNIX_TIMESTAMP(#{end}),#{description},#{kind},#{isIp},#{onlyIp},#{report},#{adminId})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
    public void saveTest(Test testInfo);

    @Insert("INSERT INTO teach_test_problems(tid, pid, score) VALUES(#{testId}, #{id}, #{score})")
    public void saveSelectedQue(Map<String,String> selectedQue);

    @Insert("INSERT INTO teach_test_class(test_id, class_id) VALUES(#{testId}, #{id})")
    public void saveSelectedClass(Map<String,String> selectedClass);

    @Insert("INSERT INTO teach_test_ip(tid, iid) VALUES(#{testId}, #{id})")
    public void saveSelectedJroom(Map<String,String> selectedJroom);

    @Delete("DELETE FROM teach_test_problems where tid = #{id}")
    public void deleteQue(String id);

    @Delete("DELETE FROM teach_test_class where test_id = #{id}")
    public void deleteClass(String id);

    @Delete("DELETE FROM teach_test_ip where tid = #{id}")
    public void deleteJroom(String id);

    @Delete("DELETE FROM teach_test where id = #{id}")
    public void deleteTest(String id);

    @Update("UPDATE teach_test SET name=#{name}, start=UNIX_TIMESTAMP(#{start}), end=UNIX_TIMESTAMP(#{end}), description=#{description}, only_ip=#{onlyIp}, is_ip=#{isIp} where id=#{id}")
    public void updateTest(Test testInfo);

}