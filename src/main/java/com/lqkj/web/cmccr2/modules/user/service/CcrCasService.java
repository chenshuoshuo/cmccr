package com.lqkj.web.cmccr2.modules.user.service;

import com.lqkj.web.cmccr2.modules.log.service.CcrSystemLogService;
import com.lqkj.web.cmccr2.modules.user.dao.CcrUserRepository;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 更新用户ticket
     */
    public void updateTicket(String username, String ticket) {
        systemLogService.addLog("cas ticket处理服务","updateTicket",
                "更新用户ticket");

        CcrUser ccrUser = userRepository.findByUserName(username);

        ccrUser.setCasTicket(ticket);

        userRepository.save(ccrUser);
    }
}
