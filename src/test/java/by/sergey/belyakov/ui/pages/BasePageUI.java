package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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

	protected void enterText(By locator, String text) {
		try {
			WebElement element = waitByVisibility(locator);
			element.clear();
			element.sendKeys(text);
			log.info("Введен текст '{}' в поле {}", text, locator);
		} catch (TimeoutException e) {
			throw new NoSuchElementException("Элемент не найден или не доступен: " + locator, e);
		}
	}

	protected void click(By locator) {
		try {
			WebElement button = waitByClickable(locator);
			button.click();
			log.info("Нажата кнопка {}", locator);
		} catch (TimeoutException e) {
			throw new NoSuchElementException("Элемент не найден или не доступен: " + locator, e);
		}
	}

	public void switchToTabByUrl(String expectedUrl) {
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
