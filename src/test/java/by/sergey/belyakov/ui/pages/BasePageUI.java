package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class BasePageUI {

	protected ThreadLocal<WebDriver> driver;
	protected WebDriverWait wait;

	public BasePageUI(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
	}

	public WebDriver getDriver() {
		return driver.get();
	}

	public void switchToTabByUrl(String expectedUrl) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
		wait.until(d -> {
			for (String handle : d.getWindowHandles()) {
				d.switchTo().window(handle);
				if (d.getCurrentUrl().contains(expectedUrl)) {
					return true;
				}
			}
			return false;
		});
	}

	public void reloadPage() {
		getDriver().navigate().refresh();
	}

	public WebElement waitByVisibility(By locator) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			throw new RuntimeException("Элемент не найден или недоступный для нажатия");
		}
	}

	public WebElement waitByClickable(By locator) {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception ex) {
			throw new RuntimeException("Элемент не найден или недоступный для нажатия");
		}
	}
}
