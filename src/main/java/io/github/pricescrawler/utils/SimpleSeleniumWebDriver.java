package io.github.pricescrawler.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class SimpleSeleniumWebDriver {
    private final String seleniumUrl;

    public SimpleSeleniumWebDriver() {
        seleniumUrl = System.getenv("SELENIUM_URL");
    }

    public Set<Cookie> getCookies(String url) throws MalformedURLException {
        var remoteWebDriver = new RemoteWebDriver(new URL(seleniumUrl), new FirefoxOptions());
        remoteWebDriver.get(url);
        var pageSource = new HashSet<>(remoteWebDriver.manage().getCookies());
        remoteWebDriver.quit();
        return pageSource;
    }

    public String getPageContent(String url) throws MalformedURLException {
        var remoteWebDriver = new RemoteWebDriver(new URL(seleniumUrl), new FirefoxOptions());
        remoteWebDriver.get(url);
        String pageSource = remoteWebDriver.getPageSource();
        remoteWebDriver.quit();
        return pageSource;
    }
}
