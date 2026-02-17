package by.sergey.belyakov.ui.tests.manager;

import by.sergey.belyakov.ui.pages.CreateNewTaskPage;
import by.sergey.belyakov.ui.pages.IssuesListPage;
import by.sergey.belyakov.ui.pages.LoginPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.Test;


import static org.testng.Assert.assertTrue;

public class CreateIssueTest extends BaseTestUI {

	@Test
	public void testCreateIssue() {
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.login(getBaseUsername(), getBasePassword());

		ManagerMenuPage managerMenuPage = new ManagerMenuPage(getDriver());
		managerMenuPage.createNewTask();

		CreateNewTaskPage createTaskPage = new CreateNewTaskPage(getDriver());
		createTaskPage.createTask("Тестовая задача", "Описание задачи");

		createTaskPage.switchToNewTab(0);

		managerMenuPage.goToIssuesPage();

		IssuesListPage issuesListPage = new IssuesListPage(getDriver());
		boolean exists = issuesListPage.isIssueDisplayed("Тестовая задача");
		assertTrue(exists);
	}
}
