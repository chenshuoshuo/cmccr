package com.lqkj.web.cmccr2.modules.record.serivce;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.Check;
import com.lqkj.web.cmccr2.modules.application.dao.CcrVersionApplicationRepository;
import com.lqkj.web.cmccr2.modules.record.dao.CcrRequestRecordRepository;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrLocationRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrRequestRecord;
import com.lqkj.web.cmccr2.modules.record.doamin.CcrStatisticsFrequency;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserAuthorityRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestRecordService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CcrRequestRecordRepository requestRecordRepository;

    @Autowired
    CcrUserAuthorityRepository authorityRepository;

    @Autowired(required = false)
    ConsulClient consulClient;

    @Autowired
    DbSearcher dbSearcher;

    @Autowired
    CcrVersionApplicationRepository versionApplicationRepository;

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

    /**
     * 网关流量统计
     */
    public Page<Object[]> urlStatistics(Timestamp startTime, Timestamp endTime,
                                        Integer page, Integer pageSize) {
        Page<Object[]> result = requestRecordRepository.urlRecord(startTime, endTime,
                PageRequest.of(page, pageSize));

        logger.info("流量统计结果:{}", result);

        return result;
    }

    /**
     * 网关地理统计
     */
    public List<CcrLocationRecord> locationStatistics(Timestamp startTime, Timestamp endTime) {
        List<Object[]> results = requestRecordRepository.locationRecord(startTime, endTime);

        List<CcrLocationRecord> locationRecords = new ArrayList<>(results.size() + 1);

        for (Object[] r : results) {
            try {
                DataBlock block = dbSearcher.memorySearch((String) r[0]);
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

    /**
     * 异常列表
     */
    public Page<CcrRequestRecord> errorRecord(Timestamp startTime, Timestamp endTime,
                                              Integer page, Integer pageSize) {
        return this.requestRecordRepository.errorRecord(startTime, endTime,
                PageRequest.of(page, pageSize));
    }

    /**
     * 系统状态记录
     */
    public Map<String, List<Check.CheckStatus>> systemRecord() {
        Map<String, List<Check.CheckStatus>> statusMap = new HashMap<>();

        Response<Map<String, List<String>>> services = consulClient
                .getCatalogServices(QueryParams.DEFAULT);

        for (String serviceName : services.getValue().keySet()) {
            if (serviceName.equals("consul")) continue;

            Response<List<Check>> check = consulClient
                    .getHealthChecksForService(serviceName, QueryParams.DEFAULT);

            List<Check.CheckStatus> statuses = check.getValue().stream().map(Check::getStatus)
                    .collect(Collectors.toList());

            String chineseName = authorityRepository.findNameByContent(serviceName);

            statusMap.put(chineseName!=null ? chineseName : serviceName, statuses);
        }

        return statusMap;
    }

    /**
     * 用户详细请求记录
     */
    public List<Object[]> urlRecordDetail(Timestamp startTime, Timestamp endTime, String name) {
        return this.requestRecordRepository.urlRecordDetail(startTime, endTime, name);
    }

    /**
     * ip访问总数
     */
    @Cacheable(cacheNames = "ipRecord")
    public Integer ipRecord() {
        return this.requestRecordRepository.ipRecord();
    }

    /**
     * app访问统计
     */
    public List<Object[]> appRecord() {
        return versionApplicationRepository.downloadRecord();
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
}
