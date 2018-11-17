package PageObjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class Actions {

	public static ArrayList<WebDriver> allDrivers;
	public static WebDriver driver;
	
	public void initiateMultipleWebDrivers(int numbers,String [] paths) {
		
	}
	
	public void initiateTheWebDriver() {
		//System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeDriverManager.getInstance().setup();
		Actions.driver = new ChromeDriver();
		Actions.driver.manage().window().maximize();
		Actions.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void takeScreenShot(String name) {
		File src= ((TakesScreenshot)Actions.driver).getScreenshotAs(OutputType.FILE);
		try {
		  FileUtils.copyFile(src, new File("ScreenShots\\"+name+".png"));
		}
		catch (IOException e)
		 {
		  System.out.println(e.getMessage());
		  
		 }

	}
	public void setText(By b, String text) {
		waitUntil(b, "presenceOfElement");
		//System.out.println(element);
		try {
			Actions.driver.findElement(b).clear();
			Actions.driver.findElement(b).sendKeys(text);
			String actualValue = ( Actions.driver.findElement(b).getAttribute("value")==null) ? Actions.driver.findElement(b).getAttribute("innerHTML") : Actions.driver.findElement(b).getAttribute("value");
			
			assertEquals(actualValue,text );
		} catch (Exception e) {
			Assert.fail("Couldn't set text because of " + e.getMessage());
		}

	}

	public void clickOn(By b) {
		waitUntil(b, "elementToBeClickable");
		try {
			Actions.driver.findElement(b).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			Assert.fail("Couldn't click because of" + e.getMessage());
		}
		
	}

	public void navigateToPage(String url,By b) {
		Actions.driver.get(url);
		WebElement element = waitUntil(b, "presenceOfElement");
		assertNotNull(element, "Navigation Failed to this Website "+url);
	}
	
	
	public WebElement waitUntil(By b, String condition) {
		try {
			WebElement element = null;
			if (condition == "presenceOfElement") {
				element = (new WebDriverWait(driver,6)).until(ExpectedConditions.presenceOfElementLocated(b));
				return element;
			}
			else if(condition== "elementToBeClickable") {
				element = (new WebDriverWait(driver, 6)).until(ExpectedConditions.elementToBeClickable(b));
				return element;
			}

			else {
				element = null;
				Assert.fail("Wrong condition");
			}
		return element ;
		} catch (Exception e) {
			//Assert.fail("Couldn't find the element because of " + e.getMessage());
			return null;
		}
	}

	public void closeTheBrowser() {
		try {
			Actions.driver.close();
		} catch (Exception e) {
			Assert.fail("Couldn't close the browser because of " + e.getMessage());
		}
	}
}
