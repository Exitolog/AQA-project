package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

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
		click(issueRowByHeaderText(header));
	}

	private void findAndClickMoreOptionsInIssue() {
		click(moreOptionsButton);
	}

	private void clickDeleteButtonAndConfirm() {
		click(deleteButtonLocator);
		click(confirmDeleteButton);

	}

	public void deleteIssue(String header){
			reloadPage();
			findAndClickIssueInTable(header);
			findAndClickMoreOptionsInIssue();
			clickDeleteButtonAndConfirm();
	}
}
