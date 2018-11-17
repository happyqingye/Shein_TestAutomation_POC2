package POM;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import junit.framework.Assert;

public class SheinGoods {
	Actions actions = new Actions();
	String addToBagBtnLocator = "body > div.c-outermost-ctn > div.mgds-goodsd.j-mgds-goodsd.container-fluid-1200.j-detail-page > div.row.c-goodsdc > div.goodsd-right.j-goodsd-right-ctn.col-sm-5 > div.j-vue-dt-addbag-opt > div.goodsd-btn > button.she-btn-black.she-btn-xl",
			coockiesAcceptBtnLocator = ".accept-btn",
			presenceOfElement = "presenceOfElement" ,
			sizeElementLabel = "label[size='",
			bagItems = "a[class='gd-name']",
			bagIcon = "a[href='https://www.shein.com/cart']>i[class*='iconfont']",
			elementToBeClickable = "elementToBeClickable";
				
	
	public void navigateToItem(String URL) {
		actions.navigateToPage(URL, By.cssSelector(addToBagBtnLocator));
		
	}
	public void addToBag() {
		actions.clickOn(By.cssSelector(addToBagBtnLocator));
	}
	
	void handleCookies() {
		if(Actions.driver.findElement(By.cssSelector(coockiesAcceptBtnLocator)).isDisplayed() ) {
			actions.clickOn(By.cssSelector(coockiesAcceptBtnLocator));
		}
	}
	
	public void chooseSize(String size) {
		size = sizeElementLabel + size +"']";
		actions.clickOn(By.cssSelector(size));
	}
	
	public void verifyBag(String [] goods) {
		actions.clickOn(By.cssSelector(bagIcon));
		Arrays.sort(goods);
		List<WebElement> items = Actions.driver.findElements(By.cssSelector(bagItems));
		assertEquals(items.size(), goods.length,"Assert Number of Items in the Bag");
		List <String> actualItems = new ArrayList<String>();
		for(int i=0;i<items.size();i++) {
			actualItems.add(items.get(i).getAttribute("href"));
		}
		
		for(int i=0;i<items.size();i++) {
			if(!actualItems.contains(goods[i])) {
				Assert.fail("Actual Items doesn't contain "+ goods[i]);
			}
		}
	}
}
