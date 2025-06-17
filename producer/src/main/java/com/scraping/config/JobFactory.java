package com.scraping.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

//Permits dependency injection from Spring on Quartz's Jobs
public class JobFactory extends SpringBeanJobFactory {
    private final AutowireCapableBeanFactory beanFactory;

    public JobFactory(ApplicationContext context) {
        this.beanFactory = context.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {

        Object jobInstance = super.createJobInstance(bundle);
        beanFactory.autowireBean(jobInstance);

        return jobInstance;
    }
}
