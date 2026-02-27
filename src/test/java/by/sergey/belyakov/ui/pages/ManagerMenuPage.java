package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Slf4j
public class ManagerMenuPage extends BasePageUI {

	private By createButton = By.xpath("//span[text()='Создать']");
	private By createTaskButton = By.xpath("//a[@href='newIssue' and text()='Новая задача']");
	private By issuesListLink = By.xpath("//a[@href='issues']");

	public ManagerMenuPage(ThreadLocal<WebDriver> driver) {
		super(driver);
	}

	public void clickCreate() {
		click(createButton);
	}

	public boolean createButtonIsDisplayed() {
		try {
			return waitByVisibility(createButton).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickCreateTask() {
		click(createTaskButton);
	}

	public void createNewTask() {
			clickCreate();
			clickCreateTask();
	}

	public void goToIssuesPage() {
		click(issuesListLink);
	}
}
