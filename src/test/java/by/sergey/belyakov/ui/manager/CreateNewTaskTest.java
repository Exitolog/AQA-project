package by.sergey.belyakov.ui.manager;

import by.sergey.belyakov.ui.authorization.LoginPage;
import by.sergey.belyakov.ui.issues.IssuesListPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CreateNewTaskTest {

	public static void main(String[] args) {

		System.setProperty("java.net.preferIPv4Stack", "true");

		WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		try {

			driver.manage().timeouts().implicitlyWait(10, SECONDS);

			driver.get("http://localhost:8080");
			System.out.println("Страница загружена");

			LoginPage loginPage = new LoginPage(driver);

			loginPage.login("admin", "admin");


			ManagerMenu managerMenu = new ManagerMenu(driver);
			managerMenu.createNewTask();

			CreateNewTaskPage createNewTaskPage = new CreateNewTaskPage(driver);

			createNewTaskPage.createTask("Тестовая задача", "Тестовое описание");

			createNewTaskPage.switchToNewTab(0);

			managerMenu.goToIssuesPage();

			IssuesListPage issuesListPage = new IssuesListPage(driver);

			boolean exists = issuesListPage.isIssueDisplayed("Тестовая задача");

			if (exists) {
				System.out.println("Все хорошо, задача создана!");
			} else {
				System.err.println("Что-то пошло не так... :-/");
			}

			issuesListPage.deleteIssue("Тестовая задача");

			Thread.sleep(10000);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			driver.quit();
		}
	}
}
