package by.sergey.belyakov.ui.authorization;

import by.sergey.belyakov.ui.pages.LoginPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class AuthSuccessTest {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	@BeforeMethod
	public void setUp() throws InterruptedException {
		System.setProperty("java.net.preferIPv4Stack", "true");
		WebDriverManager.chromedriver().setup();
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		webDriver.get("http://localhost:8080");
		driver.set(webDriver);
		Thread.sleep(3000);
	}

	@AfterMethod
	public void closeBrowser() throws InterruptedException {
		Thread.sleep(2000);
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	@Test
	public void testAuthSuccess() {
		LoginPage loginPage = new LoginPage(driver.get());
		boolean isLoggedInSuccessfully = loginPage.login("admin", "admin");
		ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver.get());

		assertTrue(isLoggedInSuccessfully);
		assertTrue(managerMenuPage.createButtonIsDisplayed());
	}
}
