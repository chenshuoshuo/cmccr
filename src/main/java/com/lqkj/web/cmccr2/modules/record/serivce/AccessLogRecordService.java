package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.notification.dao.CcrAccessLogRecordSQLDao;
import com.lqkj.web.cmccr2.modules.record.dao.CcrAccessLogRecordRepository;
import com.lqkj.web.cmccr2.modules.record.dao.CcrMenuRecordRepository;
import com.lqkj.web.cmccr2.modules.record.doamin.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccessLogRecordService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CcrAccessLogRecordRepository accessLogRecordRepository;

    @Autowired
    DbSearcher dbSearcher;

    @Autowired
    CcrAccessLogRecordSQLDao ccrAccessLogRecordSQLDao;

    public CcrAccessLogRecord add(CcrAccessLogRecord accessLog) {
        return accessLogRecordRepository.save(accessLog);
    }

    public int useCount(String startDate,String endDate){
        return accessLogRecordRepository.useCount(startDate,endDate);
    }

    public int useCountAll(){ return accessLogRecordRepository.useCountAll();}

    public Integer ipCount(){return accessLogRecordRepository.countByIpAddress();}

    public void deleteAllByLogTime(String startDate, String endDate){accessLogRecordRepository.deleteAllByLogTime(startDate,endDate);}

    public List<CcrAccessLogRecord> listQuery(String startDate, String endDate){return accessLogRecordRepository.listQuery(startDate,endDate);}

    /**
     * 网关地理统计
     */
    //@Cacheable(cacheNames = "locationStatistics", key = "#startTime+'_'+#endTime")
    public List<CcrLocationRecord> locationStatistics(Timestamp startTime, Timestamp endTime) {

        List<Object[]> results;
        if (startTime == null || endTime == null) {
            String sql = "select ip_address,count(a) from ccr_access_log a group by ip_address";
            System.out.println(ccrAccessLogRecordSQLDao);
            results =  ccrAccessLogRecordSQLDao.execSql(sql,null);
        }else {
            results = accessLogRecordRepository.locationRecord(startTime, endTime);
        }

        List<CcrLocationRecord> locationRecords = new ArrayList<>(results.size() + 1);

        for (Object[] r : results) {
            try {
                String ip = String.valueOf(r[0]).split(",")[0];
                DataBlock block = dbSearcher.memorySearch(ip);
                String region = block.getRegion();
                int city = region.lastIndexOf("|");
                if("0".equals(region.substring(city-1,city))){
                    //把城市为0改为未知
                    region = region.replaceAll("(0)(.*?)(\\1)(.*?)", "$1$2未知$4");
                }
                locationRecords.add(new CcrLocationRecord(block.getRegion(), block.getCityId(),
                        ((BigInteger) r[1]).intValue()));
            } catch (Exception e) {
                logger.error("ip查询错误", e);
                locationRecords.add(new CcrLocationRecord((String) r[0], -1,
                        ((BigInteger) r[1]).intValue()));
            }
        }

        logger.info("流量统计结果:{}", locationRecords);

        return locationRecords;
    }

    /**
     * 导出网关地理统计
     */
    public void exportLocationStatistics(Timestamp startTime, Timestamp endTime, OutputStream outputStream) throws IOException {
        List<CcrLocationRecord> locationRecords = locationStatistics(startTime, endTime);

        SXSSFWorkbook workbook = new SXSSFWorkbook(10);

        Sheet sheet = workbook.createSheet();

        //设置头
        Row rootRow = sheet.createRow(0);
        rootRow.createCell(0).setCellValue("区域名称");
        rootRow.createCell(1).setCellValue("城市id");
        rootRow.createCell(2).setCellValue("访问数");

        for (int i = 0; i < locationRecords.size(); i++) {
            CcrLocationRecord record = locationRecords.get(i);

            Row dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(record.getRegion());
            dataRow.createCell(1).setCellValue(record.getCityId());
            dataRow.createCell(2).setCellValue(record.getCount());
        }

        workbook.write(outputStream);

        workbook.dispose();
    }

}
