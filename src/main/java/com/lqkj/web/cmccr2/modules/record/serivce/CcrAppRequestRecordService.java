package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.modules.record.dao.CcrAppRequestRecordRepository;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAppRequestRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CcrAppRequestRecordService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CcrAppRequestRecordRepository ccrAppRequestRecordRepository;

    /**
     * 添加
     */
    public void add(CcrAppRequestRecord ccrAppRequestRecord){
        ccrAppRequestRecordRepository.save(ccrAppRequestRecord);
    }

    /**
     * 获取应用访问top5
     */
    public List<Object[]> topRequest(){
        List<Object[]> result = ccrAppRequestRecordRepository.topRequest();

        logger.info("数据统计结果：{}", result);

        return result;
    }
}
