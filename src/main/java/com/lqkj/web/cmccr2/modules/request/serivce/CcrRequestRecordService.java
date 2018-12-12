package com.lqkj.web.cmccr2.modules.request.serivce;

import com.lqkj.web.cmccr2.modules.request.dao.CcrRequestRecordRepository;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrDateResult;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrRequestRecord;
import com.lqkj.web.cmccr2.modules.request.doamin.CcrStatisticsFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CcrRequestRecordService {

    @Autowired
    CcrRequestRecordRepository requestRecordRepository;

    public void add(CcrRequestRecord requestRecord) {
        requestRecordRepository.save(requestRecord);
    }

    /**
     * 数据统计
     */
    @Transactional
    public void dataStatistics(Timestamp startTime, Timestamp endTime, CcrStatisticsFrequency frequencyEnum) {
        List<CcrDateResult> results = new ArrayList<>(10);

        long frequency = frequencyToSeconds(frequencyEnum);

        //requestRecordRepository.selectByTimeRegion(startTime, endTime, frequency, true);
    }

    public long frequencyToSeconds(CcrStatisticsFrequency frequencyEnum) {
        if (frequencyEnum.equals(CcrStatisticsFrequency.one_day)) {
            return TimeUnit.DAYS.toSeconds(1);
        } else if (frequencyEnum.equals(CcrStatisticsFrequency.one_hour)) {
            return TimeUnit.HOURS.toSeconds(1);
        } else {
            return TimeUnit.DAYS.toSeconds(30);
        }
    }
}
