package com.oj.controller.exam;


import com.oj.service.exam.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author xielanning
 * @Time 2019年4月17日 10点18分
 * @Description 考试管理controller类
 */
@Controller
@RequestMapping("/testMn")
public class TestController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestService testService;
    @RequestMapping("/")
    public String index(){
        return "exam/test.html";
    }

    //获取考试信息
    @PostMapping("/getTargetTestList")
    @ResponseBody
    public List<Map> getTargetTestList(  HttpServletRequest request){

        String testName = request.getParameter("testName");
        return testService.getTestInfo(testName, request.getSession().getAttribute("user_id").toString());
    }

    //展示考试编辑页面
    @RequestMapping("/addTest/{id}")
    public String addTest(@PathVariable String id, Model model){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("id", id);
        //如果为编辑，加入被编辑的考试的参数
        if (!id.equals("add")){
            //考试信息
            info.put("testInfo", testService.getTestInfoById(id));
            //已选择试题信息
            info.put("selectedQueList", testService.getSelectedQueListById(id));
            //已选择班级信息
            info.put("selectedClassList", testService.getSelectedClassListById(id));
            //已选择机房信息
            info.put("selectedJroomList", testService.getSelectedJroomListById(id));
        }
        model.addAttribute("info", info);
        return "exam/addTest";
    }
    @RequestMapping("/testScore/{id}")
    public String testScore(@PathVariable String id, Model model){
        System.out.println("flag:"+id);
        return "exam/testScore";

    }

    //获取考试信息
    @PostMapping("/getTestInfo")
    @ResponseBody
    public List<Map> getTestInfo(HttpServletRequest request){
        String testName = request.getParameter("testName");
        return testService.getTestInfo(testName, request.getSession().getAttribute("user_id").toString());
    }

    //获取试题列表
    @PostMapping("/loadPreSelectQuestion")
    @ResponseBody
    public List<Map> loadPreSelectQuestion(){
        return testService.loadPreSelectQuestion();
    }

    //获取班级列表
    @PostMapping("/loadPreSelectClass")
    @ResponseBody
    public List<Map> loadPreSelectClass(HttpServletRequest request){
        return testService.loadPreSelectClass(request.getSession().getAttribute("user_id").toString());
    }

    //获取机房列表
    @PostMapping("/loadPreSelectJroom")
    @ResponseBody
    public List<Map> loadPreSelectJroom(){
        return testService.loadPreSelectJroom();
    }

    //更新或新增考试
    @PostMapping("/saveOrUpdateTest")
    @ResponseBody
    public Map saveOrUpdateTest(@RequestBody Map<String, Object> param, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            testService.saveOrUpdateTest(param, request.getSession().getAttribute("user_id").toString());
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }

    //
    @PostMapping("/testDelete")
    @ResponseBody
    public Map<String, String> testDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            testService.testDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

}
