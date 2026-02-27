package by.sergey.belyakov.ui.pages;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


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
		enterText(headerField, text);
	}

	public void enterDescription(String text) {
		enterText(descriptionField, text);
	}

	public void clickCreate() {
		click(createButton);
	}

	public void createTask(String header, String description) {
		switchToTabByUrl("http://localhost:8080/newIssue");
		enterHeader(header);
		enterDescription(description);
		clickCreate();
	}

}



