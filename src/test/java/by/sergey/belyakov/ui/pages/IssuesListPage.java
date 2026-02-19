package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class IssuesListPage extends BasePageUI{

	private By moreOptionsButton = By.xpath("//span[text()='Показать больше']");
	private By deleteButtonLocator = By.xpath("//span[text()='Удалить задачу']");
	private By confirmDeleteButton = By.xpath("//span[text()='Удалить']");

	public IssuesListPage(WebDriver driver) {
		super(driver);
	}

	private By issueRowByHeaderText(String header) {
		return By.xpath("//table[@data-test='ring-table']//tbody/tr[.//a[text()='" + header + "']]");
	}

	public boolean isIssueDisplayed(String summary) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(issueRowByHeaderText(summary)));
			return true;
		} catch (Exception e) {
			log.error("Задача с заголовком '{}' не найдена ", summary);
			return false;
		}
	}

	private void findAndClickIssueInTable(String header) {
		WebElement row =waitByVisibility(issueRowByHeaderText(header));
		new Actions(driver).moveToElement(row).click().perform();
	}

	private void findAndClickMoreOptionsInIssue() {
		WebElement moreOptions = waitByVisibility(this.moreOptionsButton);
		moreOptions.click();
	}

	private void clickDeleteButtonAndConfirm() {
		WebElement deleteButton = waitByClickable(deleteButtonLocator);
		new Actions(driver).moveToElement(deleteButton).click().perform();

		WebElement confirmDeleteTask = waitByClickable(confirmDeleteButton);
		confirmDeleteTask.click();
	}

	public void deleteIssue(String header){
		try {
			findAndClickIssueInTable(header);
			switchToNewTab(1);
			findAndClickMoreOptionsInIssue();
			clickDeleteButtonAndConfirm();
		} catch (Exception ex) {
			log.error("Ошибка при удалении задачи {}", header);
		}
	}
}
