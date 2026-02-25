package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@Slf4j
public class IssuesListPage extends BasePageUI{

	private By moreOptionsButton = By.xpath("//button[contains(@aria-label, 'Показать больше')]");
	private By deleteButtonLocator = By.xpath("//span[text()='Удалить задачу']");
	private By confirmDeleteButton = By.xpath("//span[text()='Удалить']");

	public IssuesListPage(ThreadLocal<WebDriver> driver) {
		super(driver);
	}

	private By issueRowByHeaderText(String header) {
		return By.xpath("//table[@data-test='ring-table']//tbody/tr[.//a[text()='" + header + "']]");
	}

	public boolean isIssueDisplayed(String summary) {
		try {
			return waitByVisibility(issueRowByHeaderText(summary)).isDisplayed();
		} catch (Exception exception) {
			throw new TimeoutException("Задача не найдена в списке с заголовком " + summary);
		}
	}

	private void findAndClickIssueInTable(String header) {
		WebElement row = waitByVisibility(issueRowByHeaderText(header));
		new Actions(getDriver()).moveToElement(row).click().perform();
	}

	private void findAndClickMoreOptionsInIssue() {
		WebElement moreOptions = waitByClickable(moreOptionsButton);
		moreOptions.click();
	}

	private void clickDeleteButtonAndConfirm() {
		WebElement deleteButton = waitByClickable(deleteButtonLocator);
		new Actions(getDriver()).moveToElement(deleteButton).click().perform();

		WebElement confirmDeleteTask = waitByClickable(confirmDeleteButton);
		confirmDeleteTask.click();
	}

	public void deleteIssue(String header){
		try {
			reloadPage();
			findAndClickIssueInTable(header);
			findAndClickMoreOptionsInIssue();
			clickDeleteButtonAndConfirm();
		} catch (Exception ex) {
			throw new RuntimeException("Ошибка при удалении задачи с заголовком " + header);
		}
	}
}
