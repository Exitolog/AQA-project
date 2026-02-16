package by.sergey.belyakov.ui.manager;


import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class CreateNewTaskPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private By headerField = By.cssSelector("textarea[data-test='summary']");
	private By descriptionField = By.cssSelector("div[data-test='wysiwyg-editor-content']");
	private By createButton = By.cssSelector("button[data-test='submit-button']");

	public CreateNewTaskPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(7));
	}

	public void waitForPageLoad() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void enterHeader(String text) {
		try {
			switchToNewTab(1);
			WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(headerField));
			header.clear();
			header.sendKeys(text);
			log.info("Вводим заголовок задачи " + text);
			System.out.println("Вводим заголовок задачи " + text);
		} catch (TimeoutException e) {
			waitForPageLoad();
			throw new NoSuchElementException("Не удалось найти поле 'Заголовок'", e);
		}
	}

	public void enterDescription(String text) {
		try {
			WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionField));
			field.click();
			field.clear();
			field.sendKeys(text);
			log.info("Вводим описание задачи " + text);
			System.out.println("Вводим описание задачи " + text);
		} catch (TimeoutException e) {
			System.err.println("Поле описания не доступно");
			waitForPageLoad();
			throw new NoSuchElementException("Не удалось найти редактор описания", e);
		}
	}

	public void clickCreate() {
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));
		button.click();
		log.info("Нажимаем кнопку Создать");
		System.out.println("Нажимаем кнопку Создать");
	}

	public void createTask(String header, String description) {
		enterHeader(header);
		enterDescription(description);
		clickCreate();
	}

	public void switchToNewTab(Integer index) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		var windows = driver.getWindowHandles();
		if (windows.size() < index + 1) {
			throw new NoSuchElementException("Не открылась новая вкладка после клика");
		}

		String newTab = windows.toArray()[index].toString();
		driver.switchTo().window(newTab);

		log.info("Переключились на новую вкладку: " + driver.getCurrentUrl());
		System.out.println("Переключились на новую вкладку: " + driver.getCurrentUrl());
		System.out.println("Текущий URL: " + driver.getCurrentUrl());
	}
}

