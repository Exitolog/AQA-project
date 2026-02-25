package by.sergey.belyakov.ui.tests.manager;

import by.sergey.belyakov.ui.pages.CreateNewTaskPage;
import by.sergey.belyakov.ui.pages.IssuesListPage;
import by.sergey.belyakov.ui.pages.ManagerMenuPage;
import by.sergey.belyakov.ui.tests.BaseTestUI;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CreateAndDeleteIssueTest extends BaseTestUI {


	@Test
	public void testCreateAndDeleteIssue() {
		singInBaseCredentials();

		String header = getBaseHeaderIssue();

		ManagerMenuPage managerMenuPage = new ManagerMenuPage(driver);
		managerMenuPage.createNewTask();

		CreateNewTaskPage createTaskPage = new CreateNewTaskPage(driver);
		createTaskPage.createTask(header, getBaseDescriptionIssue());

		createTaskPage.switchToTabByUrl("http://localhost:8080/dashboard");

		managerMenuPage.goToIssuesPage();

		IssuesListPage issuesListPage = new IssuesListPage(driver);

		issuesListPage.deleteIssue(header);
		try {
			issuesListPage.isIssueDisplayed(header);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Задача не найдена в списке с заголовком " + header));
		}
	}
}
