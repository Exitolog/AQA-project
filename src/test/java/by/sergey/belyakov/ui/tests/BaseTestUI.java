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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static by.sergey.belyakov.utills.FilePathList.filePathToBaseCredentialsForLogin;

@Slf4j
public class BaseTestUI {

	protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	protected String[] baseCredentials;

	@BeforeMethod
	public void setUp()  {
		System.setProperty("java.net.preferIPv4Stack", "true");
		WebDriverManager.chromedriver().setup();
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(12));
		webDriver.get("http://localhost:8080");
		driver.set(webDriver);
		this.baseCredentials = getBaseCredentialsForLogin();
	}

	@AfterMethod
	public void closeBrowser()  {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	public void singInBaseCredentials() {
		LoginPage loginPage = new LoginPage(driver.get());
		loginPage.login(getBaseUsername(), getBasePassword());
	}

	public WebDriver getDriver() {
		return driver.get();
	}

	public String[] getBaseCredentialsForLogin() {
		return CredentialsReader.getCredentials(filePathToBaseCredentialsForLogin);
	}

	public String getBaseUsername() {
		return baseCredentials[0];
	}

	public String getBasePassword() {
		return baseCredentials[1];
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
