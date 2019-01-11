package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.modules.record.dao.CcrClickRecordRepository;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrClickRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClickRecordService {
    private CcrClickRecordRepository clickRecordRepository;

    @Autowired
    public ClickRecordService(CcrClickRecordRepository clickRecordRepository) {
        this.clickRecordRepository = clickRecordRepository;
    }

    public void add(CcrClickRecord clickRecord) {
        clickRecordRepository.save(clickRecord);
    }

    public List<Object[]> clickRecord() {
        return clickRecordRepository.clickRecord();
    }
}
