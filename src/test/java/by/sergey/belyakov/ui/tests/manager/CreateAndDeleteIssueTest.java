package by.sergey.belyakov.ui.tests.manager;

import by.sergey.belyakov.ui.pages.CreateNewTaskPage;
import by.sergey.belyakov.ui.pages.IssuesListPage;
import by.sergey.belyakov.ui.pages.LoginPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class CreateAndDeleteIssueTest extends BaseTestUI {

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


		loginPage.login(getBaseUsername(),getBasePassword());

		ManagerMenuPage managerMenuPage = new ManagerMenuPage(getDriver());
		managerMenuPage.createNewTask();

		CreateNewTaskPage createTaskPage = new CreateNewTaskPage(getDriver());
		createTaskPage.createTask(header, description);

		createTaskPage.switchToNewTab(0);

		managerMenuPage.goToIssuesPage();

		IssuesListPage issuesListPage = new IssuesListPage(getDriver());

		issuesListPage.deleteIssue(header);

		boolean notExists = issuesListPage.isIssueDisplayed(header);

		assertFalse(notExists);
	}
}
