package com.oj.service.other;
import com.oj.entity.other.SubmitStatus;
import java.util.List;
import java.util.Map;

public interface SubmitStatusService {
    //获取主题信息列表
    public List<Map> getSubmitStatusMaplist(Map<String, String> param);
}
