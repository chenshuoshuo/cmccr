package com.lqkj.web.cmccr2.modules.sensitivity.controller;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.message.MessageBean;
import com.lqkj.web.cmccr2.message.MessageListBean;
import com.lqkj.web.cmccr2.modules.sensitivity.domain.CcrSensitivityRecord;
import com.lqkj.web.cmccr2.modules.sensitivity.domain.CcrSensitivityWord;
import com.lqkj.web.cmccr2.modules.sensitivity.service.SensitivityWordService;
import com.lqkj.web.cmccr2.modules.sensitivity.domain.CheckResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(tags = "敏感词")
@RestController
public class SensitivityController {

    @Autowired
    SensitivityWordService sensitivityWordService;

    @ApiOperation("检查是否为敏感词")
    @ApiImplicitParam(name = "words", value = "需要检查的内容")
    @RequestMapping(value = "/center/sensitivity/" + APIVersion.V1 + "/checkWords", method = {RequestMethod.GET,
            RequestMethod.POST})
    public MessageListBean<CheckResult> check(String[] words) {
        List<CheckResult> results = sensitivityWordService.checkWords(words);
        return MessageListBean.ok(results);
    }

    @ApiOperation("增加一个违禁字")
    @PutMapping("/center/sensitivity/" + APIVersion.V1 + "/add/{word}")
    public MessageBean<CcrSensitivityWord> add(@PathVariable String word,
                                               @RequestParam(required = false) String replaceContent,
                                               @RequestParam CcrSensitivityWord.HandleType handleType) throws IOException {
        return MessageBean.ok(sensitivityWordService.add(word, handleType, replaceContent));
    }

    @ApiOperation("删除一个违禁字")
    @DeleteMapping("/center/sensitivity/" + APIVersion.V1 + "/delete/{id}")
    public MessageBean<Long> delete(@PathVariable Long id) throws IOException {
        sensitivityWordService.delete(id);
        return MessageBean.ok(id);
    }

    @ApiOperation("更新一个违禁字")
    @PostMapping("/center/sensitivity/" + APIVersion.V1 + "/update/{id}")
    public MessageBean<CcrSensitivityWord> update(@RequestBody CcrSensitivityWord sensitivityWord,
                                                  @PathVariable Long id) throws IOException {
        return MessageBean.ok(sensitivityWordService.update(id, sensitivityWord.getWord()));
    }

    @ApiOperation("查询一个违禁字信息")
    @GetMapping("/center/sensitivity/" + APIVersion.V1 + "/info/{id}")
    public MessageBean<CcrSensitivityWord> info(@PathVariable Long id) {
        return MessageBean.ok(sensitivityWordService.info(id));
    }

    @ApiOperation("分页查询违禁字列表")
    @GetMapping("/center/sensitivity/" + APIVersion.V1 + "/page")
    public MessageBean<Page<CcrSensitivityWord>> page(@RequestParam(required = false) String keyword,
                                                      @RequestParam Integer page,
                                                      @RequestParam Integer pageSize) {
        return MessageBean.ok(sensitivityWordService.page(keyword, page, pageSize));
    }

    @ApiOperation("分页查询违禁字记录")
    @GetMapping("/center/sensitivity/" + APIVersion.V1 + "/record/page")
    public MessageBean<Page<CcrSensitivityRecord>> recordPage(@RequestParam(required = false) String keyword,
                                                              @RequestParam Integer page,
                                                              @RequestParam Integer pageSize) {
        return MessageBean.ok(sensitivityWordService.recordPage(keyword, page, pageSize));
    }
}
