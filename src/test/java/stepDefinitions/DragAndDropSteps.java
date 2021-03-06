package stepDefinitions;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import webApplication.testingFramework.base.TestContext;
import webApplication.testingFramework.common.JavascriptFunctions;
import webApplication.testingFramework.common.Screenshots;
import webApplication.testingFramework.common.StringUtility;
import webApplication.testingFramework.pages.HomePage;
import webApplication.testingFramework.pages.interactions.Droppable;
import webApplication.testingFramework.pages.interactions.Interactions;
import webApplication.testingFramework.reporting.ExtentReportHandler;

public final class DragAndDropSteps {
	
	private WebDriver driver = null;
	public static Logger log = LogManager.getLogger(DragAndDropSteps.class.getName());
	private TestContext testContext = null;
	private HomePage homePage = null;
	private Interactions interactions = null;
	private Droppable droppable = null;

	public DragAndDropSteps(TestContext context) {
		try {
			testContext = context;
			homePage = testContext.pageObjectManager().homePage();
			interactions = testContext.pageObjectManager().interactions();
			droppable = testContext.pageObjectManager().droppable();
			driver = testContext.driver();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@When("^user clicks Interactions$")
	public void clickInteractions() throws Throwable {
		
		ExtentTest stepInfo = null;
		String stepName = StringUtility.getTitleCase("user clicks Interactions");
		try {
			stepInfo = ExtentReportHandler.createStepInfo("When", stepName);
			Thread.sleep(200);

			// hover on the element to be clicked
			homePage.hoverOnInteractions();
			homePage.clickInteractions();

			// take screenshot of the element to be clicked
			stepInfo.addScreenCaptureFromPath(Screenshots.saveScreenshot(driver, "Interactions_Click"));
			Thread.sleep(50);

			// pass the step status
			ExtentReportHandler.testStepStatus("PASS", stepName, stepInfo, null);
		} catch (Throwable t) {
			t.printStackTrace();
			log.error("Error occurred clickInteractions()!");
			stepInfo.addScreenCaptureFromPath(Screenshots.saveScreenshot(driver, "Interactions_Click"));
			ExtentReportHandler.testStepStatus("FAIL", stepName, stepInfo, t);
			throw t;
		}
	}

	@Then("^Interactions page is displayed$")
	public void interactionsPage() throws Throwable {
		
		ExtentTest stepInfo = null;
		try {
			stepInfo = ExtentReportHandler.createStepInfo("When", "Interactions page opens");
			interactions.verifyInteractionsHeader();
			// take screenshot of the element to be clicked
			stepInfo.addScreenCaptureFromPath(Screenshots.saveScreenshot(driver, "Interactions_Page"));
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println("Error occurred!");
			stepInfo.addScreenCaptureFromPath(Screenshots.saveScreenshot(driver, "Interactions_Page"));
			ExtentReportHandler.testStepStatus("FAIL", "user clicks Interactions", stepInfo, t);
			throw t;
		}
	}

	@When("^user selects Droppable$")
	public void selectDroppable() throws Throwable {
		try {
			Thread.sleep(5);
			interactions.hoverOnDroppable();
			Thread.sleep(5);
			interactions.clickDroppable();
			Thread.sleep(50);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Error in selectDragDrop().");
			throw e;
		}
	}

	@Then("^Droppable page is displayed$")
	public void verifyDroppablePage() throws Throwable {
		try {
			Thread.sleep(50);
			droppable.verifyDroppableHeader();
			Thread.sleep(10);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Error in selectDragDrop().");
			throw e;
		}
	}

	@And("^user drags and drops item$")
	public void performDragAndDrop() throws Throwable {
		try {
			//DO NOT remove the sleep otherwise next action fails
			Thread.sleep(500);
			droppable.dragAndDrop();
			Thread.sleep(50);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Error in performDragAndDrop().");
			throw e;
		}
	}

	@Then("^text changes$")
	public void verifyDragDroppedItems() throws Throwable {
		try {
			droppable.isDroppedVisible();
			Thread.sleep(100);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Error in verifyDragDroppedItems().");
			throw e;
		}
	}
}