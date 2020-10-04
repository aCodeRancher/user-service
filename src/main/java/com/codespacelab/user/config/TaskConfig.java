package com.codespacelab.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@EnableAsync
@Profile("jms")
public class TaskConfig {

    @Bean
    TaskExecutor taskExecutor (){
        return new SimpleAsyncTaskExecutor();
    }

}
