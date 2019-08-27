package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.modules.record.dao.CcrAccessLogRecordRepository;
import com.lqkj.web.cmccr2.modules.record.dao.KeyValueVORepository;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrAccessLogRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.KeyValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyValueVOService {
    @Autowired
    private KeyValueVORepository keyValueVORepository;


    public List<KeyValueVO> userGroupCount(){ return keyValueVORepository.userGroupCount();}
}
