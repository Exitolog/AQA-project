package by.sergey.belyakov.utills;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class CreateScreenshotService {

	public static void createScreenshot(WebDriver driver) {
		TakesScreenshot screenshot = ((TakesScreenshot) driver);
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File("screenshots/screenshot" + LocalDateTime.now() + ".png"));
		} catch (IOException exception) {
			log.error(exception.getMessage());
		}
	}
}
