package com.lqkj.web.cmccr2.modules.request.serivce;

import com.lqkj.web.cmccr2.modules.request.dao.CcrRequestRecordRepository;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrRequestRecord;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrStatisticsFrequency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CcrRequestRecordService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CcrRequestRecordRepository requestRecordRepository;

    public void add(CcrRequestRecord requestRecord) {
        requestRecordRepository.save(requestRecord);
    }

    /**
     * 数据统计
     */
    @Transactional
    public List<Object[]> dataStatistics(Timestamp startTime, Timestamp endTime, CcrStatisticsFrequency frequencyEnum,
                                         Boolean successed) {
        String frequency = enumToFrequency(frequencyEnum);

        List<Object[]> result = requestRecordRepository.dataRecord(startTime,
                endTime, frequency, successed);

        logger.info("数据统计结果:{}", result);

        return result;
    }

    private String enumToFrequency(CcrStatisticsFrequency frequencyEnum) {
        if (frequencyEnum.equals(CcrStatisticsFrequency.one_day)) {
            return "YYYY-MM-DD";
        } else if (frequencyEnum.equals(CcrStatisticsFrequency.one_hour)) {
            return "YYYY-MM-DD HH24";
        } else {
            return "YYYY-MM";
        }
    }

    public List<Object[]> urlStatistics(Timestamp startTime, Timestamp endTime, CcrStatisticsFrequency frequencyEnum) {
        //String frequency = enumToFrequency(frequencyEnum);

        List<Object[]> result = requestRecordRepository.urlRecord(startTime, endTime);

        logger.info("流量统计结果:{}", result);

        return null;
    }
}
