package by.sergey.belyakov.ui.manager;

import by.sergey.belyakov.ui.pages.CreateNewTaskPage;
import by.sergey.belyakov.ui.pages.IssuesListPage;
import by.sergey.belyakov.ui.pages.LoginPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertFalse;

public class CreateAndDeleteIssueTest {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	@BeforeMethod
	public void setUp() throws InterruptedException {
		System.setProperty("java.net.preferIPv4Stack", "true");
		WebDriverManager.chromedriver().setup();
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		webDriver.get("http://localhost:8080");
		driver.set(webDriver);
		Thread.sleep(3000);
	}

	@AfterMethod
	public void closeBrowser() throws InterruptedException {
		Thread.sleep(2000);
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	@DataProvider(name = "optionsIssues", parallel = true)
	public Object[][] dataProviderMethodForNewIssues() {
		return new Object[][] {
				{"Первый заголовок", "Первое описание"},
				{ "Второй заголовок","Второе описание"},
				{ "Третий заголовок","Третье описание"}};
	}

	@Test(dataProvider = "optionsIssues")
	public void testCreateAndDeleteIssue(String header, String description) {
		LoginPage loginPage = new LoginPage(driver.get());
		loginPage.login("admin", "admin");

		ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver.get());
		managerMenuPage.createNewTask();

		CreateNewTaskPage createTaskPage = new CreateNewTaskPage(driver.get());
		createTaskPage.createTask(header, description);

		createTaskPage.switchToNewTab(0);

		managerMenuPage.goToIssuesPage();

		IssuesListPage issuesListPage = new IssuesListPage(driver.get());

		issuesListPage.deleteIssue(header);

		boolean notExists = issuesListPage.isIssueDisplayed(header);

		assertFalse(notExists);
	}
}
