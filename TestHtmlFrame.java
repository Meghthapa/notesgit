package com.dev2qa.webdriver.frame;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestHtmlFrame {

	public static void main(String[] args) throws InterruptedException {
		
		String filePath = "file:///C:/WorkSpace/dev2qa.com/Code/src/com/dev2qa/webdriver/frame/TestFramePage.html";
		WebDriver ffDriver = new FirefoxDriver();
		
		ffDriver.get(filePath);
		
		//TestHtmlFrame.getTotalFrameCountInCurrentPage(ffDriver);
		
		TestHtmlFrame.testGetFrame(ffDriver);
		
		Thread.sleep(3000);
		ffDriver.close();	
	}
	
	public static int getTotalFrameCountInCurrentPage(WebDriver driver)
	{
		int ret = 0;
		
		By byFrameTag = By.tagName("frame");
		List<WebElement> frameList = driver.findElements(byFrameTag);
		int frameSize = frameList.size();
		
		System.out.println("There are " + frameSize + " frame in current web page.");
		
		By byIFrameTag = By.tagName("iframe");
		List<WebElement> iframeList = driver.findElements(byIFrameTag);
		int iframeSize = iframeList.size();
		
		System.out.println("There are " + iframeSize + " iframe in current web page.");
		
		ret = frameSize + iframeSize;
				
		return ret;
	}
	
	public static void testGetFrame(WebDriver driver)
	{
		TestHtmlFrame.getFrameByIndex(driver, 0);
		TestHtmlFrame.returnToTopWebPage(driver);
		
		TestHtmlFrame.getFrameById(driver, "rightFrame");
		TestHtmlFrame.returnToTopWebPage(driver);
		
		TestHtmlFrame.getFrameByName(driver, "leftFrame");
		TestHtmlFrame.returnToTopWebPage(driver);
		
		TestHtmlFrame.getFrameByXpath(driver, "//frame[@id=\"rightFrame\"]");
		TestHtmlFrame.returnToTopWebPage(driver);
		
		TestHtmlFrame.getChildFrameByXPath(driver, "//frame[@id=\"rightFrame\"]","//iframe[@src=\"pageIFrame2.html\"]");
		TestHtmlFrame.returnToTopWebPage(driver);
	}
	
	public static void getFrameByIndex(WebDriver driver, int frameIndex)
	{
		// Switch to frame by index.
		driver.switchTo().frame(frameIndex);
		
		// Check whether the switch action successfully or not.
		int totalFrameCount = TestHtmlFrame.getTotalFrameCountInCurrentPage(driver);
		
		System.out.println("There are totaly " + totalFrameCount + " frames exist in current frame which index is " + frameIndex);
	}
	
	public static void getFrameById(WebDriver driver, String frameId)
	{
		driver.switchTo().frame(frameId);
		
		int totalFrameCount = TestHtmlFrame.getTotalFrameCountInCurrentPage(driver);
		
		System.out.println("There are totaly " + totalFrameCount + " frames exist in current frame with id " + frameId);
	}
	

	public static void getFrameByName(WebDriver driver, String frameName)
	{
		driver.switchTo().frame(frameName);
		
		int totalFrameCount = TestHtmlFrame.getTotalFrameCountInCurrentPage(driver);
		
		System.out.println("There are totaly " + totalFrameCount + " frames exist in current frame with name " + frameName);
	}
	
	public static void getFrameByXpath(WebDriver driver, String xpath)
	{
		By byXPath = By.xpath(xpath);
		
		// Get all web elements by xpath.
		List<WebElement> iframeList = driver.findElements(byXPath);
		
		if(iframeList.size()>0)
		{
			// Get the first frame web element.
			WebElement iframeElement1 = iframeList.get(0);
			
			// Switch to it.
			driver.switchTo().frame(iframeElement1);
			
			int totalFrameCount = TestHtmlFrame.getTotalFrameCountInCurrentPage(driver);
			
			System.out.println("There are totaly " + totalFrameCount + " frames exist in current frame with xpath " + xpath);
		}else
		{
			System.out.println("Do not find any frame with xpath " + xpath);
		}
	}
	
	
	public static void getChildFrameByXPath(WebDriver driver, String parentFrameXPath, String childFrameXPath)
	{
		// First switch to parent frame.
		TestHtmlFrame.getFrameByXpath(driver, parentFrameXPath);
		
		// Then switch to child frame in parent frame.
		TestHtmlFrame.getFrameByXpath(driver, childFrameXPath);
		
		// Check current frame content to verify the frame is correct frame.
		By byUserName = By.name("userName");
		List<WebElement> eleList = driver.findElements(byUserName);
		if(eleList.size()>0)
		{
			WebElement element = eleList.get(0);
			
			element.sendKeys("dev2qa.com");
			
			System.out.println("Find web element in current child frame, parent frame xpath : " + parentFrameXPath + " , child frame xpath : " + childFrameXPath);
			
			System.out.println("Found web element tage : " + element.getTagName() + " , value : " + element.getText());
		}
		
	}
	
	
	/* Return back to top web page. Otherwise, if there can not find any frame in subsequent action
	 * , it will throw NoSuchFrameException.*/
	public static void returnToTopWebPage(WebDriver driver)
	{ 
		driver.switchTo().defaultContent();
	}
	
}
