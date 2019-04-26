package com.oj.controller.exam;


import com.oj.service.exam.TestService;
import com.oj.service.serviceImpl.exam.TestServicelmpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 考试管理controller类
 */
@Controller
@RequestMapping("/testMn")
public class TestController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TestService testService;

    public TestController(){ //在构造函数里添加初始化执行失败
        super();

    }
    @RequestMapping("/")
    public String index(){
        return "exam/test.html";
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
    @RequestMapping("/copyTest/{id}")
    //@ResponseBody
    public String copyTest(@PathVariable String id, Model model,HttpServletRequest request){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("id", "add");
        //如果为编辑，加入被编辑的考试的参数
        //考试信息
        info.put("testInfo", testService.getTestInfoById(id));
        //已选择试题信息
        info.put("selectedQueList", testService.getSelectedQueListById(id));
        //已选择班级信息
        info.put("selectedClassList", testService.loadPreSelectClass(request.getSession().getAttribute("user_id").toString()));
        //已选择机房信息
        //info.put("selectedJroomList", testService.getSelectedJroomListById(id));

        model.addAttribute("info", info);
        return "exam/copyTest";
    }
    @RequestMapping("/showIp/{tid}")
    public String showIp(@PathVariable String tid, Model model){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("tid",tid);
        model.addAttribute("info", info);
        return "exam/showIp";
    }
    @PostMapping("/getIpInfoList")
    @ResponseBody
    public List<Map> getIpInfoList(@RequestBody Map<String, String> param, HttpServletRequest request){
        //String testName = request.getParameter("testName");
        return testService.getIpInfoById(param);
    }
    //获取考试信息
    @PostMapping("/getTestInfo")
    @ResponseBody
    public List<Map> getTestInfo(HttpServletRequest request){
        ///
        testService.FunctionLY("318");
        ///
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
    //********************************************************************** xln
    /*
     * @author xielanning
     * @Time 2019年4月24日 10点18分
     */
    //获取考试成绩页面
    @RequestMapping("/showTestScore/{tid}")
    public String showTestScore(@PathVariable String tid, Model model){
        Map<String,Object> info = new HashMap<>();
        info.put("tid", tid);
        model.addAttribute("info", info);
        return "exam/testScore";
    }
    //获取考试相似度判断结果页面
    @RequestMapping("/similarityUser/{tid}")
    public String showTestSimilarityUser(@PathVariable String tid, Model model){
        Map<String,Object> info = new HashMap<>();
        info.put("tid", tid);
        model.addAttribute("info", info);
        return "exam/similarityUser";
    }
    //获取考试简要信息
    @RequestMapping("/getTestBriefInf")
    @ResponseBody
    public Map getTestBriefInf(@RequestBody Map<String, String> param, HttpServletRequest request){
        Map map = testService.getTestBriefInf(param.get("testId"));
        System.out.println(map);
        return map;
    }
    //获取考试题目集
    @RequestMapping("/getTestProblemList")
    @ResponseBody
    public List<Map> getTestProblemList(@RequestBody Map<String, String> param, HttpServletRequest request){
        return testService.getTestProblemList(param.get("testId"));
    }
    //获取考试结果统计结果
    @RequestMapping("/getTheStatisticalResult")
    @ResponseBody
    public Map getTheStatisticalResult(@RequestBody Map<String, String> param, HttpServletRequest request){
        Map result = new HashMap<String, Object>();
        List<Map> list = testService.getTheStatisticalResult(param);
        List keyList = new LinkedList<String>();
        for(Map k : list){
            k.put("name", k.get("name")+"分");
            keyList.add(k.get("name"));
        }
        result.put("key", keyList);
        result.put("data", list);
        return result;
    }
    //获取本次考试下的所有班级
    @RequestMapping("/getTestClassList")
    @ResponseBody
    public List<Map> getTestClassList(@RequestBody Map<String, String> param, HttpServletRequest request){
        return testService.getTestClassList(param.get("testId"));
    }
    //获取考试成绩集
    @PostMapping("/getTestScoreResultList")
    @ResponseBody
    public List<Map> getTestScoreResult(@RequestBody Map<String, String> param, HttpServletRequest request){
        //System.out.println(param);
        //System.out.println(request.getSession().getAttribute("user_id").toString());
        return testService.getTestScoreResultList(param, request.getSession().getAttribute("user_id").toString());
    }
    //获取本次考试下的所有专业


    //定时任务-成绩处理
    //（1）周期性处理（实验）提交状态表，并汇总到成绩表
    //（2）定时于（实验/考试）结束后的5mins处理提交状态表，并汇总到成绩表
    //定时任务-相似判断处理
    //（1）定时于（实验/考试）结束后的10mins处理提交状态表，并汇总结果到相似性判断表
    //*********************************************************************************
    //周期性地在指定的时间开始，对所有进行中的（实验/考试）的提交状态表进行处理，并依次汇总给成绩表
    @Scheduled(cron = "0 01 3 ? * *")
    public void organizeTheSubDataForTestResult(){
        log.info("夜伴三庚，又到更新数据的时候了。       ---- liyue");
        testService.RunDoIt();
    }

}

