package com.lqkj.web.cmccr2.modules.log.service;

import com.lqkj.web.cmccr2.modules.log.dao.CcrSystemLogRepository;
import com.lqkj.web.cmccr2.modules.log.domain.CcrSystemLog;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CcrSystemLogService {

    @Autowired
    CcrSystemLogRepository systemLogRepository;

    /**
     * 增加一条日志记录
     */
    public void addLog(String source, String method, String description) {
        CcrSystemLog systemLog = new CcrSystemLog(source, method, description);
        systemLog.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        systemLogRepository.save(systemLog);
    }

    /**
     * 分页
     */
    public Page<CcrSystemLog> page(Timestamp startTime, Timestamp endTime,
                                   Integer page, Integer pageSize) {
        if (startTime!=null && endTime!=null) {
            return systemLogRepository.pageByTime(startTime, endTime, PageRequest.of(page, pageSize));
        }

        return systemLogRepository.findAll(PageRequest.of(page, pageSize));
    }

    public void export(Timestamp startTime, Timestamp endTime, OutputStream os) throws IOException {
        List<CcrSystemLog> logs = systemLogRepository.findAllByTime(startTime, endTime);

        SXSSFWorkbook workbook = new SXSSFWorkbook(10);

        Sheet sheet = workbook.createSheet();

        //设置头
        Row rootRow = sheet.createRow(0);
        rootRow.createCell(0).setCellValue("日志id");
        rootRow.createCell(1).setCellValue("创建时间");
        rootRow.createCell(2).setCellValue("介绍");
        rootRow.createCell(3).setCellValue("执行方法");
        rootRow.createCell(4).setCellValue("来源");
        rootRow.createCell(5).setCellValue("执行者用户名");

        for (int i = 0; i < logs.size(); i++) {
            CcrSystemLog log = logs.get(i);

            Row dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(log.getLogId().toString());
            dataRow.createCell(1).setCellValue(log.getCreateTime().toString());
            dataRow.createCell(2).setCellValue(log.getDescription());
            dataRow.createCell(3).setCellValue(log.getMethod());
            dataRow.createCell(4).setCellValue(log.getSource());
            dataRow.createCell(5).setCellValue(log.getUserName());
        }

        workbook.write(os);

        workbook.dispose();
    }
}
