package com.scraping.config;

import com.scraping.controller.CallLoop;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail myJobDetail() {
        return JobBuilder.newJob(CallLoop.class)
                .withIdentity("CallLoop")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger myJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("myTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?"))
                .build();
    }
}
