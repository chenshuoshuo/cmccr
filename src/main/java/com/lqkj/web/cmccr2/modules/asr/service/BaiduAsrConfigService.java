package com.lqkj.web.cmccr2.modules.asr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lqkj.web.cmccr2.modules.asr.dao.BaiduAsrConfigDao;
import com.lqkj.web.cmccr2.modules.asr.domain.BaiduAsrConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class BaiduAsrConfigService {

    @Autowired
    BaiduAsrConfigDao baiduAsrConfigDao;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取配置
     */
    public ObjectNode loadConfig(){
        BaiduAsrConfig baiduAsrConfig = baiduAsrConfigDao.findById(1).get();
        return loadConfigFromModel(baiduAsrConfig);
    }

    /**
     * 更新配置
     * @param config
     * @return
     */
    public ObjectNode saveConfig(BaiduAsrConfig config){
        BaiduAsrConfig baiduAsrConfig = baiduAsrConfigDao.findById(1).get();
        baiduAsrConfig.setClientId(config.getClientId());
        baiduAsrConfig.setClientSecret(config.getClientSecret());
        return loadConfigFromModel(baiduAsrConfigDao.save(baiduAsrConfig));
    }

    /**
     * 获取token
     */
    public ObjectNode loadToken(){
        BaiduAsrConfig baiduAsrConfig = baiduAsrConfigDao.findById(1).get();
        return loadTokenFromModel(baiduAsrConfig);
    }

    /**
     * 刷新token
     */
    public ObjectNode refreshToken() throws IOException {
        BaiduAsrConfig baiduAsrConfig = baiduAsrConfigDao.findById(1).get();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("https://openapi.baidu.com/oauth/2.0/token?grant_type={grant_type}&client_id={client_id}&client_secret={client_secret}",
                null, String.class, loadVariableMapFromModel(baiduAsrConfig));

        ObjectNode nodes = objectMapper.readValue(result, ObjectNode.class);
        baiduAsrConfig.setAccessToken(nodes.get("access_token").textValue());

        Calendar calendar = Calendar.getInstance();
        baiduAsrConfig.setUpdateTime(calendar.getTime());

        calendar.add(Calendar.DATE, 30);
        baiduAsrConfig.setInvalidTime(calendar.getTime());

        return loadTokenFromModel(baiduAsrConfigDao.save(baiduAsrConfig));
    }

    /**
     * 从对象获取配置信息对象
     */
    private ObjectNode loadConfigFromModel(BaiduAsrConfig config){
        ObjectNode node = objectMapper.valueToTree(config);
        node.remove("configId");
        node.remove("accessToken");
        node.remove("updateTime");
        node.remove("invalidTime");
        return node;
    }

    /**
     * 从对象获取token信息对象
     */
    private ObjectNode loadTokenFromModel(BaiduAsrConfig config){
        ObjectNode node = objectMapper.valueToTree(config);
        node.remove("configId");
        node.remove("clientId");
        node.remove("updateTime");
        node.remove("clientSecret");
        return node;
    }

    /**
     * 从对象获取请求参数map
     */
    private Map<String, Object> loadVariableMapFromModel(BaiduAsrConfig config){
        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", "client_credentials");
        map.put("client_id", config.getClientId());
        map.put("client_secret", config.getClientSecret());

        return map;
    }
}
