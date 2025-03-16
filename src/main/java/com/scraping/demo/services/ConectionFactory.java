package com.scraping.demo.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class ConectionFactory {
    public WebDriver conexao() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        try {
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
