package com.oj.controller.exam;

import com.oj.service.exam.ExperimentService;
import net.sf.json.JSONObject;
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
 * @author lixu
 * @Time 2019年4月1日 10点18分
 * @Description 实验管理controller类
 */
@Controller
@RequestMapping("/experimentMn")
public class ExperimentController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExperimentService experimentService;

    @RequestMapping("/")
    public String index(){
        return "exam/experiment";
    }
    //展示实验编辑页面
    @RequestMapping("/addExper/{id}")
    public String addExper(@PathVariable String id, Model model){
        Map<String, Object> info = new HashMap<>();
        //ID
        info.put("id", id);
        //如果为编辑，加入被编辑的实验的参数
        if (!id.equals("add")){
            //实验信息
            info.put("experInfo", experimentService.getExperInfoById(id));
            //已选择试题信息
            info.put("selectedQueList", experimentService.getSelectedQueListById(id));
            //已选择班级信息
            info.put("selectedClassList", experimentService.getSelectedClassListById(id));
            //已选择机房信息
            info.put("selectedJroomList", experimentService.getSelectedJroomListById(id));
        }
        model.addAttribute("info", info);
        return "exam/addExper";
    }

    //获取实验信息
    @PostMapping("/getExperInfo")
    @ResponseBody
    public List<Map> getExperInfo(HttpServletRequest request){
        String experName = request.getParameter("experName");
        return experimentService.getExperInfo(experName, request.getSession().getAttribute("user_id").toString());
    }

    //获取试题列表
    @PostMapping("/loadPreSelectQuestion")
    @ResponseBody
    public List<Map> loadPreSelectQuestion(){
        return experimentService.loadPreSelectQuestion();
    }

    //获取班级列表
    @PostMapping("/loadPreSelectClass")
    @ResponseBody
    public List<Map> loadPreSelectClass(HttpServletRequest request){
        return experimentService.loadPreSelectClass(request.getSession().getAttribute("user_id").toString());
    }

    //获取机房列表
    @PostMapping("/loadPreSelectJroom")
    @ResponseBody
    public List<Map> loadPreSelectJroom(){
        return experimentService.loadPreSelectJroom();
    }

    //更新或新增实验
    @PostMapping("/saveOrUpdateExper")
    @ResponseBody
    public Map saveOrUpdateExper(@RequestBody Map<String, Object> param, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            experimentService.saveOrUpdateExper(param, request.getSession().getAttribute("user_id").toString());
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
    @PostMapping("/experDelete")
    @ResponseBody
    public Map<String, String> experDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            experimentService.experDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

}
