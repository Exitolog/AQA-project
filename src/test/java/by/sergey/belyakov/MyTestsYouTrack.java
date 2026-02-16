package by.sergey.belyakov;


import by.sergey.belyakov.ui.authorization.LoginPage;
import by.sergey.belyakov.ui.issues.IssuesListPage;
import by.sergey.belyakov.ui.manager.CreateNewTaskPage;
import by.sergey.belyakov.ui.manager.ManagerMenu;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MyTestsYouTrack {

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

    @DataProvider(name = "wrongCredentials", parallel = true)
    public Object[][] dataProviderMethodForWrongCredentials() {
        return new Object[][] {
                {"azamat", "passwordss"},
                {"kredit", "pazzwords"},
                {"wrong", "mypass" }};
    }

    @DataProvider(name = "optionsIssues", parallel = true)
    public Object[][] dataProviderMethodForNewIssues() {
        return new Object[][] {
                {"Первый заголовок", "Первое описание"},
                { "Второй заголовок","Второе описание"},
                { "Третий заголовок","Третье описание"}};
    }

    @DataProvider(name= "wrongHeaders", parallel = true)
    public Object[][]dataProviderMethodForWrongHeaders() {
        return new Object[][] {
                {"Несуществующий заголовок"},
                {"Абракадабра"},
                {"Двабракадабра"}};
    }

    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(2000);
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    @Test
    public void testAuthSuccess() {
        LoginPage loginPage = new LoginPage(driver.get());
        boolean isLoggedInSuccessfully = loginPage.login("admin", "admin");
        ManagerMenu managerMenu = new ManagerMenu(driver.get());

        assertTrue(isLoggedInSuccessfully);
        assertTrue(managerMenu.createButtonIsDisplayed());
    }

    @Test(dataProvider = "wrongCredentials")
    public void testAuthFailure(String username, String wrongPass) {
        LoginPage loginPage = new LoginPage(driver.get());
        boolean isLoggedInSuccessfully = loginPage.login(username, wrongPass);
        ManagerMenu managerMenu = new ManagerMenu(driver.get());

        assertFalse(isLoggedInSuccessfully);
        assertFalse(managerMenu.createButtonIsDisplayed());
    }

    @Test
    public void testCreateIssue() {
        LoginPage loginPage = new LoginPage(driver.get());
        loginPage.login("admin", "admin");

        ManagerMenu managerMenu = new ManagerMenu(driver.get());
        managerMenu.createNewTask();

        CreateNewTaskPage createTaskPage = new CreateNewTaskPage(driver.get());
        createTaskPage.createTask("Тестовая задача", "Описание задачи");

        // Переключаемся обратно на вкладку со списком
        createTaskPage.switchToNewTab(0);

        managerMenu.goToIssuesPage();

        IssuesListPage issuesListPage = new IssuesListPage(driver.get());
        boolean exists = issuesListPage.isIssueDisplayed("Тестовая задача");
        assertTrue(exists);
}

    @Test(dataProvider = "wrongHeaders")
    public void testGetNotExistsIssue(String header) {
        LoginPage loginPage = new LoginPage(driver.get());
        loginPage.login("admin", "admin");

        ManagerMenu managerMenu = new ManagerMenu(driver.get());
        managerMenu.goToIssuesPage();

        IssuesListPage issuesListPage = new IssuesListPage(driver.get());
        boolean notExists = issuesListPage.isIssueDisplayed(header);
        assertFalse(notExists);
    }

    @Test(dataProvider = "optionsIssues")
    public void testCreateAndDeleteIssue(String header, String description) {
        LoginPage loginPage = new LoginPage(driver.get());
        loginPage.login("admin", "admin");

        ManagerMenu managerMenu = new ManagerMenu(driver.get());
        managerMenu.createNewTask();

        CreateNewTaskPage createTaskPage = new CreateNewTaskPage(driver.get());
        createTaskPage.createTask(header, description);

        // Переключаемся обратно на вкладку со списком
        createTaskPage.switchToNewTab(0);

        managerMenu.goToIssuesPage();

        IssuesListPage issuesListPage = new IssuesListPage(driver.get());

        issuesListPage.deleteIssue(header);

        boolean notExists = issuesListPage.isIssueDisplayed(header);

        assertFalse(notExists);
    }
}
