package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

@Slf4j
public class BasePageUI {

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePageUI(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	public void switchToNewTab(Integer index) {
		Set<String> windows = driver.getWindowHandles();

		if (windows.size() < index + 1) {
			throw new NoSuchElementException("Не открылась новая вкладка после клика");
		}

		String newTab = windows.toArray()[index].toString();
		driver.switchTo().window(newTab);

		wait.until(d -> !d.getCurrentUrl().equals("about:blank") && d.getTitle().length() > 0);

		log.info("Переключились на новую вкладку: " + driver.getCurrentUrl());
	}

	public WebElement waitByVisibility(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitByClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}



}
