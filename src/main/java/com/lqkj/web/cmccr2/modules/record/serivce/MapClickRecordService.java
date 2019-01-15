package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.modules.record.dao.CcrMapClickRecordRepository;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMapClickRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapClickRecordService {
    private CcrMapClickRecordRepository clickRecordRepository;

    @Autowired
    public MapClickRecordService(CcrMapClickRecordRepository clickRecordRepository) {
        this.clickRecordRepository = clickRecordRepository;
    }

    public void add(CcrMapClickRecord clickRecord) {
        clickRecordRepository.save(clickRecord);
    }

    public List<Object[]> clickRecord() {
        return clickRecordRepository.clickRecord();
    }
}
