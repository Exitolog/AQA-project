package by.sergey.belyakov.ui.authorization;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SeleniumAuth {

	public static void main(String[] args) {

		System.setProperty("java.net.preferIPv4Stack", "true");

		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		try {

			driver.manage().timeouts().implicitlyWait(10, SECONDS);

			driver.get("http://localhost:8080");
			System.out.println("Страница загружена");

			LoginPage loginPage = new LoginPage(driver);

			loginPage.login("admin", "admin");

			Thread.sleep(5000);

			boolean isLoggedIn = driver.getCurrentUrl().contains("/dashboard");

			if(isLoggedIn) {
				System.out.println("Тест успешно пройден!");
			} else {
				System.out.println("Тест провален...");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("Закрываем браузер");
			driver.quit();
		}
	}
}
