package com.scraping.controller;

import com.scraping.services.SaveProduct;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class CallLoop implements Job {

    @Autowired
    SaveProduct saveProduct;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        saveProduct.executeScraping();
    }
}
