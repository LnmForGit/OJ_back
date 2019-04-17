package com.oj.service.exam;


import java.util.List;
import java.util.Map;

/**
 * @author xielanning
 * @Time 2019年4月17日 10点18分
 * @Description 考试管理Service接口
 */

public interface TestService {

    //获取当前教师负责班级下的考试列表
    public List<Map> getTestInfo(String testName, String user_id);


    //获取题目列表
    public List<Map> loadPreSelectQuestion();

    //获取班级列表
    public List<Map> loadPreSelectClass(String user_id);

    //获取机房列表
    public List<Map> loadPreSelectJroom();

    //通过ID获取当前考试信息
    public Map getTestInfoById(String id);

    //通过ID获取已选择试题列表
    public List<Map> getSelectedQueListById(String id);

    //通过ID获取已选择班级列表
    public List<Map> getSelectedClassListById(String id);

    //通过ID获取已选择机房列表
    public List<Map> getSelectedJroomListById(String id);

    //更新或新增考试
    public void saveOrUpdateTest(Map<String, Object> param, String user_id) throws Exception;

    //通过ID删除考试
    public void testDelete(String id);

}
