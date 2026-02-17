package by.sergey.belyakov.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePageUI {

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePageUI(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}
}
