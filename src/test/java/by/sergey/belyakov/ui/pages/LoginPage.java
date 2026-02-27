package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Slf4j
public class LoginPage extends BasePageUI {

	private By usernameField = By.xpath("//input[@name='username']");
	private By passwordField = By.xpath("//input[@name='password']");
	private By loginButtonField = By.xpath("//button[@type='submit']");
	private By createButton = By.xpath("//span[text()='Создать']");


	public LoginPage(ThreadLocal<WebDriver> driver) {
		super(driver);
	}

	public void enterUsername(String username) {
		enterText(usernameField, username);
	}

	public void enterPassword(String password) {
		enterText(passwordField, password);
	}

	public void clickLoginButton() {
		click(loginButtonField);
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
