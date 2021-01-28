package com.training.TestNG_Sales1;


import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.*;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Login extends SelCommonUtility {

	ExtentReports reports;
	ExtentHtmlReporter html_report;
	String filepath;
	ExtentTest test;
	String dateformat = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

	@BeforeTest
	public void Initialization() throws InterruptedException, IOException {
		launchBrowser();
		goToSalesForceURL();
		// loginToSalesForcePortal();
		reports = new ExtentReports();
		html_report = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "//Reports//" + dateformat + "_SFDCreport.html");
		reports.attachReporter(html_report);

	}

	@AfterClass
	public void closereport() {
		reports.flush();
		driver.quit();
	}
	
	@Test(dataProvider = "loginDatas")
	private void Test_01(Method name, String usename, String password) throws InterruptedException, IOException {
		test = reports.createTest(name.getName());
		
		WebElement ele = driver.findElement(By.id("username"));
		waitExplicitly(5, ele);
		ele.clear();
		ele.sendKeys(usename);
		test.info("Entered username");

		WebElement web_password = driver.findElement(By.xpath("//input[@type='password']"));
		web_password.clear();
		
		test.info("Entered password");
		
		WebElement web_Submit = driver.findElement(By.xpath("//input[@type='submit']"));
		web_Submit.click();
		
		
		WebElement error_msg = driver.findElement(By.id("error"));

		if (error_msg.getText().equals("Please enter your password.")) {
			test.log(Status.PASS, "Login_TC01 Passed");
			AssertJUnit.assertEquals(error_msg.getText(), "Please enter your password.");
		} else {
			//test.addScreenCaptureFromPath(takescreenshot());
			test.log(Status.FAIL, "Login_TC01 FAILED");
			Assert.fail("Login_TC01 FAILED");
		}

	}

	@Test(dataProvider = "loginDatas", priority = 1)
	public void Login_02(Method name, String usename, String password) throws InterruptedException, IOException {
		test = reports.createTest(name.getName());

		test.log(Status.INFO, "Login page launched");
		driver.findElement(By.id("username")).sendKeys(usename);
		test.info("Entered username");
		driver.findElement(By.id("password")).sendKeys(password);
		test.info("Entered password");
		driver.findElement(By.id("Login")).click();

		LogOut();
		test.info("Loggin Off");
		Thread.sleep(3000);

	}

	public String takescreenshot() throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot) driver;

		String dstpath = System.getProperty("user.dir") + "//Reports//Screenshots" + dateformat + "_SFDC.PNG";
		File srcfile = screenshot.getScreenshotAs(OutputType.FILE);
		File dstfile = new File(dstpath);
		FileUtils.copyFile(srcfile, dstfile);
		test.info("Screenshot captured");
		return dstpath;
	}

	@Test(dataProvider = "loginDatas", priority = 2)
	private void Test_03(Method name, String usename, String password) throws InterruptedException, IOException {
		test = reports.createTest(name.getName());
		test.log(Status.INFO, "Login page launched");
		
		WebElement email = driver.findElement(By.xpath("//input[@type='email']"));
		waitExplicitly(5, email);
		email.clear();
		email.sendKeys(usename);
		
		test.info("Entered username");

		WebElement web_password = driver.findElement(By.xpath("//input[@type='password']"));
		web_password.clear();
		web_password.sendKeys(password);
		
		test.info("Entered password");

		
		WebElement  web_checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
		if (!web_checkbox.isSelected())
			web_checkbox.click();
		
		test.info("Remember Checkbox is selected");
		WebElement web_Submit = driver.findElement(By.xpath("//input[@type='submit']"));
		web_Submit.click();

		LogOut();

		test.info("Logging out");
		
		
		Thread.sleep(3000);
		if (driver.findElement(By.id("username")).getAttribute("value").equals(usename)) {
			test.log(Status.PASS, name + "Passed");
			AssertJUnit.assertEquals(driver.findElement(By.id("username")).getAttribute("value"), usename);
		} else {
			//test.addScreenCaptureFromPath(takescreenshot());
			test.log(Status.FAIL, name + "FAILED");
			Assert.fail(name + " FAILED");
		}

		if (driver.findElement(By.xpath("//input[@type='checkbox']")).isSelected()) {
			test.log(Status.PASS, name + "Passed");
			AssertJUnit.assertEquals(driver.findElement(By.xpath("//input[@type='checkbox']")).isSelected(), true);
		} else {
			//test.addScreenCaptureFromPath(takescreenshot());
			test.log(Status.FAIL, name + "FAILED");
			Assert.fail(name + " FAILED");
		}
		
		Thread.sleep(3000);

	}



		
	@Test (dataProvider = "loginDatas" , priority = 3)
	private void  Test_04(Method name, String usename, String password) throws InterruptedException {
		
		test = reports.createTest(name.getName());
		

		WebElement ele = driver.findElement(By.id("clear_link"));
	     waitExplicitly(5, ele);
		ele.click();
		

		 WebElement email = driver.findElement(By.id("username"));
	 
		email.clear();
		email.sendKeys(usename);
		
		WebElement forgot_Password  = driver.findElement(By.linkText("Forgot Your Password?"));
		forgot_Password.click();

		test.info("Forgot Password Checkbox is selected");
		
		WebElement web_Submit = driver.findElement(By.xpath("//input[@type='submit']"));
		web_Submit.click();
		
		
		
		WebElement email_1 =   driver.findElement(By.id("un"));
		waitExplicitly(5, email_1);
		
		email_1.clear();
		email_1.sendKeys(usename);
	     
	     driver.findElement(By.id("continue")).click();
	     Thread.sleep(1000);
	     
	     WebElement login_again =   driver.findElement(By.xpath(" //a[contains(text(),'Return to Login')]"));
			waitExplicitly(5, login_again);
			
			login_again.click();
			Thread.sleep(3000);

	}
	
	
	@Test (  priority = 4)
	private void Test_04B(Method name) throws InterruptedException, IOException {
		test = reports.createTest(name.getName());
		
		WebElement ele = driver.findElement(By.id("clear_link"));
	     waitExplicitly(5, ele);
		ele.click();
		
		
	   WebElement email = driver.findElement(By.id("username"));
	   waitExplicitly(5, email);
	    email.clear();
		email.sendKeys("123");
		
		WebElement web_password = driver.findElement(By.xpath("//input[@type='password']"));
		web_password.clear();
		web_password.sendKeys("1234");
		
		test.info("Entered password");
		
		
		WebElement  web_checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
		if (web_checkbox.isSelected())
			web_checkbox.click();
		
		WebElement web_Submit = driver.findElement(By.xpath("//input[@type='submit']"));
		web_Submit.click();
		
		Thread.sleep(3000);
		WebElement error_msg = driver.findElement(By.id("error"));

		if (error_msg.getText().equals("Please check your username and password. If you still can't log in, contact your Salesforce administrator.")) {
			test.log(Status.PASS, name.getName()+" Passed");
			AssertJUnit.assertEquals(error_msg.getText(), "Please check your username and password. If you still can't log in, contact your Salesforce administrator.");
		} else {
			//test.addScreenCaptureFromPath(takescreenshot());
			test.log(Status.FAIL, name.getName()+"FAILED");
			Assert.fail(name.getName()+" FAILED");
		}

	}


	@DataProvider(name = "loginDatas")
	public Object[][] listOfLoginCredentials() throws FileNotFoundException, IOException {

		// HSSFWorkbook myExBook = new HSSFWorkbook(fi);
		String sFilePath = System.getProperty("user.dir") + "//resources//" + "input.xls";
		HSSFWorkbook myExBook = new HSSFWorkbook(new FileInputStream(sFilePath));
		HSSFSheet myExcelShet = myExBook.getSheet("Login Data");

		int numRows = myExcelShet.getLastRowNum() + 1;
		int numCols = myExcelShet.getRow(0).getLastCellNum();
		Object[][] excelData = new String[numRows][numCols];

		System.out.println("Populating Array");
		for (int i = 0; i < numRows; i++) {
			HSSFRow row = myExcelShet.getRow(i);
			for (int j = 0; j < numCols; j++) {
				HSSFCell cell = row.getCell(j);
				String value = cellToString(cell);
				excelData[i][j] = value;
				System.out.println("Array population complete" + excelData[i][j]);
			}
		}

		return excelData;
	}

	public String cellToString(HSSFCell cell) {
		int type = cell.getCellType();
		String result;

		// Formulas can't be evaluated, so throw an Exception if one is encountered
		if (type == HSSFCell.CELL_TYPE_FORMULA) {
			throw new RuntimeException("Cannot process a formula. Please change field to result of formula.");
		}
		// If blanks are ever able to be evaluated by Apache POI, set them to empty
		// string
		else if (type == HSSFCell.CELL_TYPE_BLANK) {
			result = " ";
		}
		// Convert cell contents to String
		else {
			result = String.valueOf(cell);
		}
		return result;
	}

}
