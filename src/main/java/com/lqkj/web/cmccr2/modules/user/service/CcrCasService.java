package com.lqkj.web.cmccr2.modules.user.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * cas ticket处理服务
 */
@Service
@Transactional
public class CcrCasService {

    @Autowired
    CcrUserRepository userRepository;

    @Autowired
    CcrSystemLogService systemLogService;

    @Value("${cas.base}")
    String casBaseURL;

    /**
     * 更新用户ticket
     */
    public CcrUser updateTicket(String username, String ticket) throws DocumentException {
        systemLogService.addLog("cas ticket处理服务", "updateTicket",
                "更新用户ticket");

        CcrUser ccrUser = userRepository.findByUserName(username);

        ccrUser.setCasTicket(ticket);

        return userRepository.save(ccrUser);
    }
}
