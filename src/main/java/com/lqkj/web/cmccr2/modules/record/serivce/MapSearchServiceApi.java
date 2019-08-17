package com.lqkj.web.cmccr2.modules.record.serivce;

import com.lqkj.web.cmccr2.message.MessageBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cmgis")
public interface MapSearchServiceApi {

    @GetMapping("/{contextPath}/map/v2/poi/record")
    MessageBean<Object[]> record(@PathVariable String contextPath);
}
