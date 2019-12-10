package com.lqkj.web.cmccr2.modules.sensitivity.service;

import com.lqkj.web.cmccr2.index.WordTree;
import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.sensitivity.dao.CcrSensitivityRecordRepository;
import com.lqkj.web.cmccr2.modules.sensitivity.dao.CcrSensitivityWordRepository;
import com.lqkj.web.cmccr2.modules.sensitivity.domain.CcrSensitivityRecord;
import com.lqkj.web.cmccr2.modules.sensitivity.domain.CcrSensitivityWord;
import com.lqkj.web.cmccr2.modules.sensitivity.domain.CheckResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 违禁字服务
 */
@Service
@Transactional
public class SensitivityWordService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CcrSensitivityWordRepository sensitivityWordDao;

    @Autowired
    CcrSystemLogService systemLogService;

    @Autowired
    CcrSensitivityRecordRepository sensitivityRecordRepository;

    private WordTree wordTree;

    public CcrSensitivityWord add(String word, CcrSensitivityWord.HandleType handleType,
                                  String replaceContent) throws IOException {
        systemLogService.addLog("违禁词服务", "add",
                "增加一个违禁词");

        CcrSensitivityWord sensitivityWord = this.sensitivityWordDao.save(new CcrSensitivityWord(word, replaceContent,
                handleType));

        this.initSensitivityWords();

        return sensitivityWord;
    }

    public void delete(Long id) throws IOException {
        systemLogService.addLog("违禁词服务", "delete",
                "删除一个违禁词");

        this.sensitivityWordDao.deleteById(id);

        this.initSensitivityWords();
    }

    public CcrSensitivityWord update(Long id, CcrSensitivityWord sensitivityWord) throws IOException {
        systemLogService.addLog("违禁词服务", "update",
                "更新一个违禁词");

        CcrSensitivityWord oldSensitivityWord = sensitivityWordDao.getOne(id);

        oldSensitivityWord.setWord(sensitivityWord.getWord());

        oldSensitivityWord.setReplaceContent(sensitivityWord.getReplaceContent());

        oldSensitivityWord.setHandleType(sensitivityWord.getHandleType());

        CcrSensitivityWord savedWord = this.sensitivityWordDao.save(oldSensitivityWord);

        this.initSensitivityWords();

        return savedWord;
    }

    public CcrSensitivityWord info(Long id) {
        systemLogService.addLog("违禁词服务", "info",
                "查询一个违禁词");

        return this.sensitivityWordDao.findById(id).get();
    }

    public Page<CcrSensitivityWord> page(String keyword, Integer page, Integer pageSize) {
        systemLogService.addLog("违禁词服务", "page",
                "分页一个违禁词");

        CcrSensitivityWord word = new CcrSensitivityWord();
        word.setWord(keyword);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("word", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id");

        return this.sensitivityWordDao.findAll(Example.of(word, exampleMatcher),
                PageRequest.of(page, pageSize));
    }

    public List<CheckResult> checkWords(String[] words) {
        systemLogService.addLog("违禁词服务", "checkWords",
                "违禁词检查");

        List<CheckResult> results = new ArrayList<>(words.length);

        Arrays.stream(words).forEach(v -> {
            results.add(checkWord(v));
        });

        return results;
    }

    private CheckResult checkWord(String word) {
        CheckResult result = new CheckResult();

        List<String> checkWords = wordTree.check(word);

        result.setContent(checkWords);
        result.setSensitivity(!checkWords.isEmpty());

        if(result.getSensitivity()){
            CcrSensitivityRecord record = new CcrSensitivityRecord("未知", word, StringUtils.join(checkWords, ","),
                    CcrSensitivityWord.HandleType.prevent);
            record.setUserName(SecurityContextHolder.getContext().getAuthentication()
                    .getName());
            sensitivityRecordRepository.save(record);
        }

        return result;
    }

    /**
     * 记录列表
     */
    public Page<CcrSensitivityRecord> recordPage(String keyword, Integer page, Integer pageSize) {
        systemLogService.addLog("违禁词服务", "recordPage",
                "记录列表");

        CcrSensitivityRecord record = new CcrSensitivityRecord();
        record.setContent(keyword);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("recordId");

        return this.sensitivityRecordRepository.findAll(Example.of(record, exampleMatcher),
                PageRequest.of(page, pageSize));
    }

    /**
     * 初始化敏感词数据库数据
     */
    @PostConstruct
    @Async
    public void initSensitivityWords() throws IOException {
        List<CcrSensitivityWord> words = sensitivityWordDao.findAll();

        if (!words.isEmpty()) {
            logger.info("已有敏感词数据,跳过数据库录入");
            initTree(words);
            return;
        }

        String csv = IOUtils.resourceToString("csv/sensitivity.csv", Charset.forName("utf-8"),
                this.getClass().getClassLoader());

        try {
            logger.info("开始导入敏感词数据");

            List<CcrSensitivityWord> ws = new ArrayList<>(2000);

            Arrays.stream(csv.split("\r\n")).forEach(v -> {
                CcrSensitivityWord word = new CcrSensitivityWord(v);
                ws.add(word);
            });

            sensitivityWordDao.saveAll(ws);

            logger.info("导入敏感词数据完成");

            initTree(ws);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 初始化搜索树
     */
    private void initTree(List<CcrSensitivityWord> words) {
        logger.info("开始构建分词树");

        wordTree = new WordTree();

        words.forEach(w -> {
            wordTree.insertText(w.getWord());
        });

        logger.info("分词树构建完成");
    }

    /**
     * @Author Wells
     * @Description //TODO
     * @Date 11:41 2019/7/17
     * @Param [ids]
     * @return void
     **/
    public void bulkDelete(Long[] ids) throws IOException {
        systemLogService.addLog("违禁词服务", "delete",
                "批量删除违禁词");
        for (int i = 0; i < ids.length; i++){
            this.sensitivityWordDao.deleteById(ids[i]);
        }

        this.initSensitivityWords();
    }
}
