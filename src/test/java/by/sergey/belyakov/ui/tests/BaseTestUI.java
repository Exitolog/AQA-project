package by.sergey.belyakov.ui.tests;

import by.sergey.belyakov.ui.utills.CredentialsReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

import static by.sergey.belyakov.ui.utills.FilePathList.filePathToBaseCredentialsForLogin;

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
}
