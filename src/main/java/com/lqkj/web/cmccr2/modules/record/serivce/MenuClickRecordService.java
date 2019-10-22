package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import com.lqkj.web.cmccr2.modules.record.dao.CcrMenuRecordRepository;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrMenuClickRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class MenuClickRecordService {
    @Autowired
    private CcrMenuRecordRepository menuRecordRepository;

    public MenuClickRecordService(CcrMenuRecordRepository menuRecordRepository) {
        this.menuRecordRepository = menuRecordRepository;
    }

    public CcrMenuClickRecord add(CcrMenuClickRecord record) {
        return menuRecordRepository.save(record);
    }

    public List<Object[]> oneMenurecord() {
        return menuRecordRepository.oneMenurecord();
    }

    public List<Object[]> appMenurecord() {
        return menuRecordRepository.appMenurecord();

    }
}
