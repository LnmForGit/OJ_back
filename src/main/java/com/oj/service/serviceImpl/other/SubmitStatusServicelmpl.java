package com.oj.service.serviceImpl.other;
import com.oj.entity.other.SubmitStatus;
import com.oj.mapper.other.SubmitStatusMapper;
import com.oj.service.other.SubmitStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class SubmitStatusServicelmpl implements SubmitStatusService{
    @Autowired(required = false)
    private SubmitStatusMapper mapper;

    @Override
    public List<Map> getSubmitStatusMaplist(Map<String, String> param)  {
        List<Map> list = mapper.getSubmitStatusMaplist(param) ;
        return list;
    }
}
