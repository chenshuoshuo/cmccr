package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.record.dao.CcrAccessLogRecordRepository;
import com.lqkj.web.cmccr2.modules.record.dao.CcrMenuRecordRepository;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAccessLogRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAppRequestRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMenuClickRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessLogRecordService {
    @Autowired
    private CcrAccessLogRecordRepository accessLogRecordRepository;

    public CcrAccessLogRecord add(CcrAccessLogRecord accessLog) {
        return accessLogRecordRepository.save(accessLog);
    }

    public int useCount(String startDate,String endDate){
        return accessLogRecordRepository.useCount(startDate,endDate);
    }

    public int useCountAll(){ return accessLogRecordRepository.useCountAll();}

    public int ipCount(){return accessLogRecordRepository.countByIpAddress();}

    public void deleteAllByLogTime(String startDate, String endDate){accessLogRecordRepository.deleteAllByLogTime(startDate,endDate);}

    public List<CcrAccessLogRecord> listQuery(String startDate, String endDate){return accessLogRecordRepository.listQuery(startDate,endDate);}
}
