package org.selenide.examples;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

public class RedirectTest {
    @Before
    public void before(){
	// WebDriver設定
	Configuration.browser = WebDriverRunner.CHROME;
	System.setProperty("webdriver.chrome.driver",RedirectConstants.CHROME_DRIVER);
    }
    
    
    @Test
    public void confirmWhetherRedirected() {
	long start = System.currentTimeMillis();
	List<RedirectBeans> list = CsvFileService.csvReader(RedirectConstants.IMPORT_CSV_FILE);
	List<String> resultList = new LinkedList<>();
	for (RedirectBeans u : list) {
	    open(u.originUrl);
	    if ($(By.id(RedirectConstants.HEAD_DOM)).isDisplayed()) {
		String redirectUrl = executeJavaScript("return location.href;");
		if (u.redirectUrl.equals(redirectUrl)) {
		    resultList.add(u.originUrl + "," + u.redirectUrl+ ",OK,なし");
		} else {
		    resultList.add(u.originUrl + "," + u.redirectUrl+ ",NG,リダイレクト先が" + redirectUrl);
		}
	    }else{
		resultList.add(u.originUrl + "," + u.redirectUrl+ ",NG,元URLにアクセスできない");
	    }
	}
	CsvFileService.csv_writer(resultList,RedirectConstants.OUTPUT_CSV_FILE);
	long end = System.currentTimeMillis();
	System.out.println("executeTime : " + (end - start)  + "ms");
    }
}
