package by.sergey.belyakov.ui.authorization;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private By usernameField = By.xpath("//input[@name='username']");
	private By passwordField = By.xpath("//input[@name='password']");
	private By loginButtonField = By.xpath("//button[@type='submit']");
	private By registrationField = By.xpath("//span[text()='Регистрация']");
	private By errorMessage = By.xpath("//div[contains(text(), 'Некорректное имя пользователя или пароль')]");


	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void enterUsername(String username) {
		WebElement fieldUserName = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
		fieldUserName.clear();
		fieldUserName.sendKeys(username);
		System.out.println("Вводим логин " + username);
	}

	public void enterPassword(String password) {
		WebElement fieldPassword = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
		fieldPassword.clear();
		fieldPassword.sendKeys(password);
		System.out.println("Вводим пароль " + password);
	}

	public void clickLoginButton() {
		WebElement buttonLogin = wait.until(ExpectedConditions.elementToBeClickable(loginButtonField));
		buttonLogin.click();
		System.out.println("Нажимаем кнопку Войти");
	}

	public void clickRegistrationLink() {
		WebElement link = wait.until(ExpectedConditions.elementToBeClickable(registrationField));
		link.click();
		System.out.println("Переходим на страницу регистрации");
	}

	public boolean login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Создать']")));
			return true;
		} catch (Exception e) {
			boolean errorAppeared = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
			if (errorAppeared) {
				System.err.println("Обнаружено сообщение об ошибке: Некорректное имя пользователя или пароль.");
				return false;
			}
		}
		throw new RuntimeException("Ошибка при попытке входа в систему!");
	}
}
