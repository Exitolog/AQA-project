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
		long threadId = Thread.currentThread().getId();
		System.out.println("Поток " + threadId + " : Ищем вкладку с URL, содержащим: " + expectedUrl);

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

		wait.until(d -> {
			for (String handle : d.getWindowHandles()) {
				d.switchTo().window(handle);
				if (d.getCurrentUrl().contains(expectedUrl)) {
					System.out.println("Поток : " + threadId + " Нашли вкладку с URL: " + d.getCurrentUrl());
					return true;
				}
			}
			return false;
		});
	}

//	public void switchToNewTab(Integer index) {
//		Set<String> windows = getDriver().getWindowHandles();
//
//		if (windows.size() < index + 1) {
//			throw new NoSuchElementException("Не открылась новая вкладка после клика");
//		}
//
//		String newTab = windows.toArray()[index].toString();
//		getDriver().switchTo().window(newTab);
//
//		wait.until(d -> !d.getCurrentUrl().equals("about:blank") && d.getTitle().length() > 0);
//
//		System.out.println("Переключились на новую вкладку: " + getDriver().getCurrentUrl());
//	}

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
