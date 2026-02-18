package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static by.sergey.belyakov.utills.CreateScreenshotService.createScreenshot;

@Slf4j
public class ManagerMenuPage extends BasePageUI {

	private By createButton = By.xpath("//span[text()='Создать']");
	private By createTaskButton = By.xpath("//a[@href='newIssue' and text()='Новая задача']");
	private By issuesListLink = By.xpath("//a[@href='issues']");

	public ManagerMenuPage(WebDriver driver) {
		super(driver);
	}

	public void clickCreate() {
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));
		button.click();
	}

	public boolean createButtonIsDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(createButton)).isDisplayed();
		} catch (Exception e) {
			createScreenshot(driver);
			return false;
		}
	}

	public void clickCreateTask() {
		try {
			WebElement taskButton = wait.until(ExpectedConditions.elementToBeClickable(createTaskButton));
			taskButton.click();
		} catch (TimeoutException e) {
			log.error("Опция 'Новая задача' не найдена", e);
			createScreenshot(driver);
		}
	}

	public void createNewTask() {
		try {
			clickCreate();
			clickCreateTask();
		} catch (Exception e) {
			createScreenshot(driver);
			throw new RuntimeException("Ошибка при создании новой задачи");
		}
	}

	public void goToIssuesPage() {
		WebElement link = wait.until(ExpectedConditions.elementToBeClickable(issuesListLink));
		link.click();
	}
}
