package Shein.SheinTestAutomationMaven;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import PageObjects.*;


public class Shein {
	static Actions actions = new Actions();
	private static Properties properties = new Properties();
    SheinPageObject shein = new SheinPageObject(); 
    SheinGoods sheinGoods = new SheinGoods();
    
    
    @BeforeSuite
    public void setUp() throws Exception {
    	
	    	File screenShot = new File("ScreenShots");
	    	File[] listFiles = screenShot.listFiles();
			for(File file : listFiles){
				file.delete();
			}
    	
    	properties.load(new FileReader(new File("test.properties")));
        actions.initiateTheWebDriver();
    }

    @AfterSuite
    public void tearDown() throws Exception {
        actions.closeTheBrowser();  
    }
    
    
    @Test(priority = 3)
    public void sheinHappyScenario() throws InterruptedException {
    	shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
    	if(properties.getProperty("newClient")=="true") {
    		assertTrue(shein.createAccount(
    	    		properties.getProperty("email"),properties.getProperty("pass")),"Sign Up Error");
    		properties.setProperty("newClient", "false");
    	}
    	else {
    		assertTrue(shein.signIn(
    	    		properties.getProperty("email"),properties.getProperty("pass")),"SignIn Error");		
    	}
    	
    	String [] items = {properties.getProperty("firstItemLink"),
    			properties.getProperty("secondItemLink"),
    			properties.getProperty("thirdItemLink")};
    	
    	shein.EditAndVerifyAccountSettings(properties.getProperty("email"),properties.getProperty("phone"),
    			properties.getProperty("date") ,properties.getProperty("country"));
    	
    	
    	sheinGoods.navigateToItem(properties.getProperty("firstItemLink"));
    	sheinGoods.addToBag();
    	
    	sheinGoods.navigateToItem(properties.getProperty("secondItemLink"));
    	sheinGoods.chooseSize(properties.getProperty("eurSize"));
    	sheinGoods.addToBag();
    	
    	sheinGoods.navigateToItem(properties.getProperty("thirdItemLink"));
    	sheinGoods.chooseSize(properties.getProperty("size"));
    	sheinGoods.addToBag();
    	
    	sheinGoods.verifyBag(items);
    	
    }
    
    @Test(priority = 1)
    public void invalidSignUp(){
    	shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
    	assertFalse(shein.createAccount(properties.getProperty("email")
    			,properties.getProperty("wrongPass")),"SignUp Error");
    	
	    
    }
    @Test (priority = 2)
    public void invalidSignIn(){
    	shein.navigateToHomePage(properties.getProperty("sheinWebsite"));
    	assertFalse(shein.signIn(
	    		properties.getProperty("email"),properties.getProperty("wrongPass")),"SignIn Error");		

    }
}
