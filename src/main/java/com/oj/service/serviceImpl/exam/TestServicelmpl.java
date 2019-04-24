package com.oj.service.serviceImpl.exam;

import com.oj.entity.exam.Test;
import com.oj.mapper.exam.TestMapper;
import com.oj.service.exam.TestService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 考试管理Service接口实现类
 */
@Service
public class TestServicelmpl implements TestService {

    @Autowired(required = false)
    private TestMapper mapper;

    /**
     * 获取当前教师负责班级下的考试列表接口功能实现
     * @param testName
     * @param user_id
     * @return
     */
    @Override
    public List<Map> getTestInfo(String testName, String user_id) {
        Map<String, String> params = new HashMap<>();
        params.put("testName", testName);
        params.put("user_id", user_id);
        return mapper.getTestInfo(params);
    }

    /**
     * 获取题目列表接口功能实现
     * @return
     */
    @Override
    public List<Map> loadPreSelectQuestion() {
        return mapper.loadPreSelectQuestion();
    }

    /**
     * 获取班级列表接口功能实现
     * @return
     */
    @Override
    public List<Map> loadPreSelectClass(String user_id) {
        return mapper.loadPreSelectClass(user_id);
    }

    /**
     * 获取机房列表接口功能实现
     * @return
     */
    @Override
    public List<Map> loadPreSelectJroom() {
        return mapper.loadPreSelectJroom();
    }

    /**
     * 通过ID获取当前考试信息接口功能实现
     * @param id
     * @return
     */
    @Override
    public Map getTestInfoById(String id) {
        return mapper.getTestInfoById(id);
    }
    @Override
    public List<Map> getIpInfoById(Map<String, String> param) {
        return mapper.getIpInfoById(param);
    }
    /**
     * 通过ID获取已选择试题列表接口功能实现
     * @param id
     * @return
     */
    @Override
    public List<Map> getSelectedQueListById(String id) {
        return mapper.getSelectedQueListById(id);
    }

    /**
     * 通过ID获取已选择班级列表接口功能实现
     * @param id
     * @return
     */
    @Override
    public List<Map> getSelectedClassListById(String id) {
        return mapper.getSelectedClassListById(id);
    }

    /**
     * 通过ID获取已选择机房列表接口功能实现
     * @param id
     * @return
     */
    @Override
    public List<Map> getSelectedJroomListById(String id) {
        return mapper.getSelectedJroomListById(id);
    }

    /**
     * 更新或新增考试接口功能实现
     * @param param
     */
    @Transactional
    @Override
    public void saveOrUpdateTest(Map<String, Object> param, String user_id) throws Exception {
        //获取考试信息
        JSONObject obj = JSONObject.fromObject(param.get("testInfo"));
        Test testInfo = (Test)JSONObject.toBean(obj, Test.class);
        //获取考试已选择试题信息
        List<Map<String, String>>  selectedQueList = (List<Map<String, String>>)param.get("selectedQueList");
        //获取考试已选择班级信息
        List<Map<String, String>>  selectedClassList = (List<Map<String, String>>)param.get("selectedClassList");
        //获取考试已选择机房信息
        List<Map<String, String>>  selectedJroomList = (List<Map<String, String>>)param.get("selectedJroomList");
        //如果id为add，则进行添加操作
        if ("add".equals(param.get("id"))){
            testInfo.setAdminId(user_id);
            testInfo.setReport("0");
            testInfo.setKind("2");
            mapper.saveTest(testInfo);
            String testId = testInfo.getId();
            for (Map<String, String> selectedQue:selectedQueList) {
                selectedQue.put("testId", testId);
                mapper.saveSelectedQue(selectedQue);
            }
            for (Map<String, String> selectedClass:selectedClassList) {
                selectedClass.put("testId", testId);
                mapper.saveSelectedClass(selectedClass);
            }
            for (Map<String, String> selectedJroom:selectedJroomList) {
                selectedJroom.put("testId", testId);
                mapper.saveSelectedJroom(selectedJroom);
            }
        }else{
            testInfo.setId(param.get("id").toString());
            mapper.deleteQue(testInfo.getId());
            mapper.deleteClass(testInfo.getId());
            mapper.deleteJroom(testInfo.getId());
            mapper.updateTest(testInfo);
            for (Map<String, String> selectedQue:selectedQueList) {
                selectedQue.put("testId", testInfo.getId());
                mapper.saveSelectedQue(selectedQue);
            }
            for (Map<String, String> selectedClass:selectedClassList) {
                selectedClass.put("testId", testInfo.getId());
                mapper.saveSelectedClass(selectedClass);
            }
            for (Map<String, String> selectedJroom:selectedJroomList) {
                selectedJroom.put("testId", testInfo.getId());
                mapper.saveSelectedJroom(selectedJroom);
            }
        }
    }

    /**
     * 通过ID删除考试接口功能实现
     * @param id
     */
    @Transactional
    @Override
    public void testDelete(String id) {
        //删除绑定试题
        mapper.deleteQue(id);
        //删除绑定班级
        mapper.deleteClass(id);
        //删除绑定机房
        mapper.deleteJroom(id);
        //删除考试信息
        mapper.deleteTest(id);
    }

    //*********************************************************************************************************** xln
    /*
     * @author xielanning
     * @Time 2019年4月24日 10点18分
     */
    //获取指定的考试成绩
    public List<Map> getTestScoreResultList(Map<String, String> param, String user_id){
        param.put("user_id", user_id);
        return mapper.getTestScoreResult(param);
    }
    //获取指定考试的简要信息
    public Map getTestBriefInf(String id){
        return mapper.getTestBriefInf(id);
    }
    //获取本次考试下的所有班级
    public List<Map> getTestClassList(String id){
        return mapper.getTestClassList(id);
    }
    //获取考试题目集
    public List<Map> getTestProblemList(String id){
        return mapper.getTestProblemList(id);
    }
    //获取考试结果统计结果
    public List<Map> getTheStatisticalResult(Map<String, String> param){
        return  mapper.getTheStatisticalResult(param);
    }
    //获取本次考试下的所有专业
}
