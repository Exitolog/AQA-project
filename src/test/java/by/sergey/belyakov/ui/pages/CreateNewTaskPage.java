package by.sergey.belyakov.ui.pages;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@Slf4j
public class CreateNewTaskPage extends BasePageUI  {

	private By headerField = By.xpath("//textarea[@data-test='summary']");
	private By descriptionField = By.xpath("//div[@data-test='wysiwyg-editor-content']");
	private By createButton = By.xpath("//button[@data-test='submit-button']");
	private By moreOptionsButton = By.xpath("//span[text()='Показать больше']");

	public CreateNewTaskPage(ThreadLocal<WebDriver> driver) {
		super(driver);
	}

	public void enterHeader(String text) {
		try {
			WebElement header = waitByVisibility(headerField);
			header.clear();
			header.sendKeys(text);
		} catch (TimeoutException e) {
			throw new NoSuchElementException("Не удалось найти поле 'Заголовок'", e);
		}
	}

	public void enterDescription(String text) {
		try {
			WebElement field = waitByVisibility(descriptionField);
			field.click();
			field.clear();
			field.sendKeys(text);
		} catch (TimeoutException e) {
			throw new NoSuchElementException("Не удалось найти поле описания", e);
		}
	}

	public void clickCreate() {
		WebElement button = waitByClickable(createButton);
		new Actions(getDriver()).moveToElement(button).click().perform();
	}

	public void createTask(String header, String description) {
		switchToTabByUrl("http://localhost:8080/newIssue");
		enterHeader(header);
		enterDescription(description);
		clickCreate();
	}

}



