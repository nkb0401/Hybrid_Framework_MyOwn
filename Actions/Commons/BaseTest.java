package Commons;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	private WebDriver driver;
	private String project_location = System.getProperty("user.dir");
	
	protected final Log log;

	// Constructor
	protected BaseTest() {
		log = LogFactory.getLog(getClass());
	}
		
	private enum BROWSER{
		CHROME, FIREFOX, IE, SAFARI, EDGE, H_CHROME, H_FIREFOX;
	}
	//add them thu vien ben thu 3 (webdriver_manager)
	protected WebDriver getBrowserDriver1(String browserName, String appUrl) {
		BROWSER browser = BROWSER.valueOf(browserName.toUpperCase());
		switch (browser) {
		case CHROME:			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		default:
			break;
		}
	
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(appUrl);
		return driver;
	}
	// tu viet
	protected WebDriver getBrowserDriver(String browserName, String appUrl) {
		
			BROWSER browser = BROWSER.valueOf(browserName.toUpperCase());
			switch (browser) {
			case CHROME:			
				System.setProperty("webdriver.chrome.driver", project_location + getSlash("BrowersDriver") + "chromedriver.exe");
				driver = new ChromeDriver();
				break;
			case FIREFOX:
				System.setProperty("webdriver.gecko.driver", project_location + getSlash("BrowersDriver") + "geckodriver.exe");
				driver = new FirefoxDriver();
				break;
			default:
				break;
			}
		
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(appUrl);
			return driver;
		
		}
	
	public WebDriver getWebDriver(){
		return this.driver;
	}
	
	private String getSlash(String folderName) {
		String separator = File.separator;
		return separator + folderName + separator;
	}

	private boolean checkTrue(boolean condition) {
		boolean pass = true;
		try {
			if (condition == true) {
				log.info(" -------------------------- PASSED -------------------------- ");
			} else {
				log.info(" -------------------------- FAILED -------------------------- ");
			}
			Assert.assertTrue(condition);
		} catch (Throwable e) {
			pass = false;

			// Add lỗi vào ReportNG
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyTrue(boolean condition) {
		return checkTrue(condition);
	}

	private boolean checkFailed(boolean condition) {
		boolean pass = true;
		try {
			if (condition == false) {
				log.info(" -------------------------- PASSED -------------------------- ");
			} else {
				log.info(" -------------------------- FAILED -------------------------- ");
			}
			Assert.assertFalse(condition);
		} catch (Throwable e) {
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyFalse(boolean condition) {
		return checkFailed(condition);
	}

	private boolean checkEquals(Object actual, Object expected) {
		boolean pass = true;
		try {
			Assert.assertEquals(actual, expected);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			pass = false;
			log.info(" -------------------------- FAILED -------------------------- ");
			
			//add loi vao ReportNG
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		return checkEquals(actual, expected);
	}
	
	public void cleanDriverInstance()
	{
		driver.quit();
	}
	
	@BeforeTest
	public void deleteALlFilesInReportScreenshot() {  
		System.out.println("----------------Start DELETE FILE IN FOLDER------------------");
		deleteALlFileInFolder();
		System.out.println("----------------END DELETE FILE IN FOLDER------------------");
	}
	public void deleteALlFileInFolder() {
		try {
			String workingDir = System.getProperty("user.dir");
			String pathFolderDownload = workingDir + "/test-output/ReportNGScreenShots";
			File file=new File(pathFolderDownload);
			File[] listOfFiles= file.listFiles();
			for (int i =0; i<listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println(listOfFiles[i].getName());
					new File(listOfFiles[i].toString()).delete();
				}
			}
		}catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
