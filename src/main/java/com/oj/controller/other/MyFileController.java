package com.oj.controller.other;

import com.oj.entity.other.MyFile;
import com.oj.service.other.MyFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author zt
 * @Time 2019年4月4日 15点00分
 * @Description 文件管理控制类
 */
@Controller
@RequestMapping("/myFile")
public class MyFileController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MyFileService myfileService;

    //返回文件管理页面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request)
    {
        return "other/myFile";
    }

    @PostMapping("/getAdminSelectInfo")
    @ResponseBody
    //获取教师下拉信息
    public List<Map> getAdminSelectInfo(){
        List<Map> list = myfileService.getAdminSelectInfo();
        return list;
    }

    @PostMapping("/getFileMapList")
    @ResponseBody
    public List<Map> getFileMapList(@RequestBody Map<String, String> param, HttpServletRequest request)
    {
        return myfileService.getFileMapList(param);
    }

    @PostMapping("/uploadMyFile")
    @ResponseBody
    public Map<String, String> uploadMyFile(HttpServletRequest request, @RequestParam("file") MultipartFile file)
    {
        Map<String, String> map = new HashMap<>();
        try {
            myfileService.uploadMyFile(request, file);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }

    @PostMapping("/checkFileName")
    @ResponseBody
    public Map<String, String> checkFileName(HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        try {
            myfileService.checkFileName(request.getParameter("name"));
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }


    @PostMapping("/fileDelete")
    @ResponseBody
    public Map<String, String> fileDelete(HttpServletRequest request)
    {
        Map<String, String> map = new HashMap<>();
        try
        {
            myfileService.fileDelete(request);
            map.put("flag", "1");
            return map;
        }
        catch(Exception e)
        {
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    @GetMapping("/downloadFile")
    @ResponseBody
    public void downloadFile(HttpServletRequest request, HttpServletResponse response)
    {
        String id = request.getParameter("id");
        //System.out.println("id : " + id);
        myfileService.downloadFile(id, response);
    }
}
