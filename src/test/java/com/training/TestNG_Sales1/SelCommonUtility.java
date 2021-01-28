package com.training.TestNG_Sales1;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SelCommonUtility {

	public  WebDriver driver;

	public WebDriver getDriver() {
		return this.driver;
	}

	public  void launchBrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	public  void goToSalesForceURL() throws InterruptedException {
		driver.get("https://login.salesforce.com/");

		Thread.sleep(3000);
	}

	public  void waitExplicitly(int iSeconds, WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, iSeconds);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public  void loginToSalesForcePortal() throws InterruptedException, IOException {
		Properties prop = readPropertiesFile();
		WebElement ele = driver.findElement(By.xpath("//input[@type='email']"));
		waitExplicitly(5, ele);
		ele.clear();

		ele.sendKeys(prop.getProperty("username"));
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(prop.getProperty("password"));

		driver.findElement(By.xpath("//input[@type='submit']")).click();

		Thread.sleep(3000);

	}

	public  void LogOut() throws InterruptedException {

		WebElement webe = driver.findElement(By.xpath("//div[@id='userNav']"));
		waitExplicitly(5, webe);
		webe.click();
		Thread.sleep(3000);
		driver.findElement(By.linkText("Logout")).click();

	}

	public  void quitBrowser() {
		driver.quit();
	}

	public  Properties readPropertiesFile() throws IOException {

		// Properties prop = readPropertiesFile("Input.properties");

		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream("Input.properties");
			prop = new Properties();
			prop.load(fis);

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}

}
