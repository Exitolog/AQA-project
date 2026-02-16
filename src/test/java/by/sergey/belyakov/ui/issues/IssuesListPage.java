package by.sergey.belyakov.ui.issues;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class IssuesListPage {

	private WebDriver driver;
	private WebDriverWait  wait;

	public IssuesListPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(7));
	}

	private By issueRowByHeaderText(String header) {
		return By.xpath("//table[@data-test='ring-table']//tbody/tr[.//a[text()='" + header + "']]");
	}

	public boolean isIssueDisplayed(String summary) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(issueRowByHeaderText(summary)));
			return true;
		} catch (Exception e) {
			System.err.println("Задача с заголовком " + summary +   " отсутствует");
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

			WebElement moreOptionsButton = wait.until(ExpectedConditions.
					visibilityOfElementLocated(By.cssSelector("button[aria-label='Показать больше']")));

			moreOptionsButton.click();

			System.out.println("Все хорошо, задача выбрана и меню 'More options' открыто");
			log.info("Задача выбрала и меню More Options появилось");

			//FIXME БЕЗ ЭТОГО Actions не отрабатывает.
			Thread.sleep(3000);

			WebElement deleteButton = wait.until(ExpectedConditions.
					elementToBeClickable(By.xpath("//span[text()= 'Удалить задачу']")));

				new Actions(driver).moveToElement(deleteButton).click().perform();

			System.out.println("Нажали на кнопку удаления задачи");
			log.info("Кликнули по кнопке удалить");

			WebElement confirmDeleteTask = wait.until(ExpectedConditions.
					elementToBeClickable(By.xpath("//span[text()='Удалить']")));

			confirmDeleteTask.click();

			System.out.println("Задача с заголовком "+ header +" успешно удалена");
			log.info("Удаление задачи с заголовком'{}', завершено", header);

		} catch (InterruptedException e) {
			System.err.println("Ошибка при ожидании потоком");
		}
	}
}
