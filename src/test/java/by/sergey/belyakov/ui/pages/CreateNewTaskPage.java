package by.sergey.belyakov.ui.pages;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
public class CreateNewTaskPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private By headerField = By.cssSelector("textarea[data-test='summary']");
	private By descriptionField = By.cssSelector("div[data-test='wysiwyg-editor-content']");
	private By createButton = By.cssSelector("button[data-test='submit-button']");

	public CreateNewTaskPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	public void enterHeader(String text) {
		try {
			WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(headerField));
			header.clear();
			header.sendKeys(text);
		} catch (TimeoutException e) {
			TakesScreenshot screenshot = ((TakesScreenshot) driver);
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("screenshots/screenshot" + LocalDateTime.now() + ".png"));
			} catch (IOException exception) {
				System.err.println(exception.getMessage());
			}
			throw new NoSuchElementException("Не удалось найти поле 'Заголовок'", e);
		}
	}

	public void enterDescription(String text) {
		try {
			WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionField));
			field.click();
			field.clear();
			field.sendKeys(text);
		} catch (TimeoutException e) {
			log.error("Поле описания не доступно");
			TakesScreenshot screenshot = ((TakesScreenshot) driver);
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("screenshots/screenshot" + LocalDateTime.now() + ".png"));
			} catch (IOException exception) {
				log.error(exception.getMessage());
			}
			throw new NoSuchElementException("Не удалось найти редактор описания", e);
		}
	}

	public void clickCreate() {
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));
		button.click();
	}

	public void createTask(String header, String description) {
		switchToNewTab(1);
		enterHeader(header);
		enterDescription(description);
		clickCreate();
	}

	public void switchToNewTab(Integer index) {
		Set<String> windows = driver.getWindowHandles();
		if (windows.size() < index + 1) {
			TakesScreenshot screenshot = ((TakesScreenshot) driver);
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("screenshots/screenshot" + LocalDateTime.now() + ".png"));
			} catch (IOException exception) {
				log.error(exception.getMessage());
			}
			throw new NoSuchElementException("Не открылась новая вкладка после клика");
		}

		String newTab = windows.toArray()[index].toString();
		driver.switchTo().window(newTab);

		wait.until(d -> !d.getCurrentUrl().equals("about:blank") && d.getTitle().length() > 0);

		log.info("Переключились на новую вкладку: " + driver.getCurrentUrl());
	}
}

