package by.sergey.belyakov.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class LoginPage extends BasePageUI {

	private By usernameField = By.xpath("//input[@name='username']");
	private By passwordField = By.xpath("//input[@name='password']");
	private By loginButtonField = By.xpath("//button[@type='submit']");
	private By errorMessage = By.xpath("//div[contains(text(), 'Некорректное имя пользователя или пароль')]");
	private By createButton = By.xpath("//span[text()='Создать']");


	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void enterUsername(String username) {
		WebElement fieldUserName = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
		fieldUserName.clear();
		fieldUserName.sendKeys(username);
	}

	public void enterPassword(String password) {
		WebElement fieldPassword = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
		fieldPassword.clear();
		fieldPassword.sendKeys(password);
	}

	public void clickLoginButton() {
		WebElement buttonLogin = wait.until(ExpectedConditions.elementToBeClickable(loginButtonField));
		buttonLogin.click();
	}

	public boolean login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(createButton));
			return true;
		} catch (Exception e) {
			boolean errorAppeared = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
			TakesScreenshot screenshot = ((TakesScreenshot) driver);
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("screenshots/screenshot" + LocalDateTime.now() + ".png"));
			} catch (IOException exception) {
				log.error(exception.getMessage());
			}
			if (errorAppeared) {
				log.error("Обнаружено сообщение об ошибке: Некорректное имя пользователя или пароль.");
				return false;
			}
		}
		TakesScreenshot screenshot = ((TakesScreenshot) driver);
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File("screenshots/screenshot" + LocalDateTime.now() + ".png"));
		} catch (IOException exception) {
			log.error(exception.getMessage());
		}
		throw new RuntimeException("Ошибка при попытке входа в систему!");
	}
}
