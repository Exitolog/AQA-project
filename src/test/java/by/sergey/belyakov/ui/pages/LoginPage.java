package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class LoginPage extends BasePageUI {

	private By usernameField = By.xpath("//input[@name='username']");
	private By passwordField = By.xpath("//input[@name='password']");
	private By loginButtonField = By.xpath("//button[@type='submit']");
	private By createButton = By.xpath("//span[text()='Создать']");


	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void enterUsername(String username) {
		WebElement fieldUserName = waitByClickable(usernameField);
		fieldUserName.clear();
		fieldUserName.sendKeys(username);
	}

	public void enterPassword(String password) {
		WebElement fieldPassword = waitByClickable(passwordField);
		fieldPassword.clear();
		fieldPassword.sendKeys(password);
	}

	public void clickLoginButton() {
		WebElement buttonLogin = waitByClickable(loginButtonField);
		buttonLogin.click();
	}

	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
		try {
			waitByClickable(createButton);
		} catch (Exception e) {
			throw new RuntimeException("Авторизация не удалась");
		}
	}
}
