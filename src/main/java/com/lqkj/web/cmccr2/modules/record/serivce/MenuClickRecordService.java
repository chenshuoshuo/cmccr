package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.modules.record.dao.CcrMenuRecordRepository;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMenuClickRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuClickRecordService {
    private CcrMenuRecordRepository menuRecordRepository;

    public MenuClickRecordService(CcrMenuRecordRepository menuRecordRepository) {
        this.menuRecordRepository = menuRecordRepository;
    }

    public void add(CcrMenuClickRecord record) {
        menuRecordRepository.save(record);
    }

    public List<Object[]> record() {
        return menuRecordRepository.record();
    }
}
