package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static by.sergey.belyakov.utills.CreateScreenshotService.createScreenshot;

@Slf4j
public class IssuesListPage extends BasePageUI{

	private By moreOptionsButton = By.cssSelector("button[aria-label='Показать больше']");
	private By deleteButtonLocator = By.xpath("//span[text()= 'Удалить задачу']");
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
			createScreenshot(driver);
			log.error("Задача с заголовком '{}' не найдена ", summary);
			return false;
		}
	}

	public void deleteIssue(String header){
		try {
		WebElement row = wait.until(ExpectedConditions.
					visibilityOfElementLocated(issueRowByHeaderText(header)));

						new Actions(driver).moveToElement(row).click().perform();

			String newTab = driver.getWindowHandles().toArray()[1].toString();
			driver.switchTo().window(newTab);

			WebElement moreOptions = wait.until(ExpectedConditions.
					visibilityOfElementLocated(this.moreOptionsButton));

			moreOptions.click();

			WebElement deleteButton = wait.until(ExpectedConditions.
					elementToBeClickable(deleteButtonLocator));

				new Actions(driver).moveToElement(deleteButton).click().perform();

			WebElement confirmDeleteTask = wait.until(ExpectedConditions.
					elementToBeClickable(confirmDeleteButton));

			confirmDeleteTask.click();

		} catch (Exception ex) {
			createScreenshot(driver);
			log.error("Ошибка при ожидании потоком");
		}
	}
}
