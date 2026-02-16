package by.sergey.belyakov.ui.manager;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class ManagerMenu {

	private WebDriver driver;
	private WebDriverWait wait;

	private By createButton = By.xpath("//span[text()='Создать']");
	private By createTaskButton = By.xpath("//a[@href='newIssue' and text()='Новая задача']");
	private By issuesListLink = By.xpath("//a[@href='issues']");

	public ManagerMenu(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(7));
	}

	public void clickCreate() {
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));
		button.click();
		log.info("Нажимаем кнопку 'Создать'");
		System.out.println("Нажимаем кнопку 'Создать'");
	}

	public boolean createButtonIsDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(createButton)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickCreateTask() {
		try {
			WebElement taskButton = wait.until(ExpectedConditions.elementToBeClickable(createTaskButton));
			taskButton.click();
			log.info("Нажимаем кнопку 'Новая задача'");
			System.out.println("Нажимаем кнопку 'Новая задача'");
		} catch (TimeoutException e) {
			log.error("Опция 'Новая задача' не найдена", e);
			System.err.println("Опция 'Новая задача' не найдена");
		}
	}

	public void createNewTask() {
		try {
			clickCreate();
			clickCreateTask();
		} catch (Exception e) {
			throw new RuntimeException("Ошибка при создании новой задачи");
		}
	}

public void goToIssuesPage() {
	WebElement link = wait.until(ExpectedConditions.elementToBeClickable(issuesListLink));
	link.click();
	System.out.println("Переходим на страницу задач");
	log.info("Нажали кнопку перехода на 'Задачи'");
}
}
