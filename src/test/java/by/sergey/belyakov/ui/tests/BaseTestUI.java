package by.sergey.belyakov.ui.tests;

import by.sergey.belyakov.ui.pages.LoginPage;
import by.sergey.belyakov.utills.CredentialsReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


import static by.sergey.belyakov.utills.FilePathList.filePathToBaseCredentialsForLogin;
import static by.sergey.belyakov.utills.FilePathList.filePathToBaseIssueInfo;


@Slf4j
public class BaseTestUI {

	protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	protected String[] baseCredentials;
	protected String[] baseIssueInfo;

	@BeforeMethod
	public void setUp()  {
		System.setProperty("java.net.preferIPv4Stack", "true");
		WebDriverManager.chromedriver().setup();
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.get("http://localhost:8080");
		driver.set(webDriver);
		this.baseCredentials = getBaseCredentialsForLogin();
		this.baseIssueInfo = getBaseIssueInfo();
	}

	@AfterMethod
	public void commonAfterMethod(ITestResult result)  {
		if (result.getStatus() == ITestResult.FAILURE) {
			createScreenshot(getDriver());
		}

		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	public void singInBaseCredentials() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(getBaseUsername(), getBasePassword());
	}

	public WebDriver getDriver() {
		return driver.get();
	}

	public String[] getBaseCredentialsForLogin() {
		return CredentialsReader.getCredentials(filePathToBaseCredentialsForLogin);
	}

	public String[] getBaseIssueInfo() {
		return CredentialsReader.getCredentials(filePathToBaseIssueInfo);
	}

	public String getBaseUsername() {
		return baseCredentials[0];
	}

	public String getBasePassword() {
		return baseCredentials[1];
	}

	public String getBaseHeaderIssue() {
		return baseIssueInfo[0];
	}

	public String getBaseDescriptionIssue() {
		return baseIssueInfo[1];
	}

	public void createScreenshot(WebDriver driver) {
		TakesScreenshot screenshot = ((TakesScreenshot) driver);
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File("screenshots/screenshot" + LocalDateTime.now() + ".png"));
		} catch (IOException exception) {
			log.error(exception.getMessage());
		}
	}
}
