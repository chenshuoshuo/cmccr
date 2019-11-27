package com.lqkj.web.cmccr2.modules.user.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "cmdbe")
@FeignClient(name = "cmdbe", url = "http://192.168.4.121:13579")
public interface CmdbeApi {

    /**
     * 分页查询教职工
     * @param staffNumber 教职工号
     * @param realName 姓名
     * @param page 页码
     * @param pageSize 每页数据条数
     *
     */
    @RequestMapping(value = "/os/teachingStaff/pageQuery", method = RequestMethod.GET)
    ObjectNode pageQueryTeachingStaff(@RequestParam(name = "staffNumber", required = false) String staffNumber,
                                      @RequestParam(name = "realName", required = false) String realName,
                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);
    /**
     * 分页查询学生
     * @param studentNo 学号
     * @param realName 姓名
     * @param page 特码
     * @param pageSize 每页数据条数
     *
     */
    @RequestMapping(value = "/os/studentInfo/pageQuery", method = RequestMethod.GET)
    ObjectNode pageQueryStudentInfo(@RequestParam(name = "studentNo", required = false) String studentNo,
                                @RequestParam(name = "realName", required = false) String realName,
                                @RequestParam(name = "page", defaultValue = "0") Integer page,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);
}
