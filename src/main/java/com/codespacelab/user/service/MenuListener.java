package com.codespacelab.user.service;

import com.codespacelab.user.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("jms")
public class MenuListener {


    @JmsListener(destination = JmsConfig.MENU_QUEUE)
    public void listen(String menu){
      log.info(menu);
    }
}
