package com.emaratech.automation.fis;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.emaratech.automation.model.fis.CSApplicationMenu;
import com.emaratech.util.test.UnitTestClassBase;

public class CSApplicationTest extends UnitTestClassBase{

	public CSApplicationTest() {
		// Change this constructor to private if you supply your own public
		// constructor
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
	}

	@Test
	public void PermanentClosure() throws Exception {
		VisionLoginTest loginTest = new VisionLoginTest();
		loginTest.loginSuccussFlow();
		browser = loginTest.getBrowser();
		browser.sync();
		
		CSApplicationMenu csApplicationMenu = new CSApplicationMenu(browser);
		
		csApplicationMenu.VISIONGDRFAPage().CSApplicationWebElement().hoverTap();
		browser.sync();
		
		csApplicationMenu.VISIONGDRFAPage().CancelClosureWebElement().hoverTap();
		browser.sync();
		
		csApplicationMenu.VISIONGDRFAPage().PermanentClosureWebElement().click();
	}
}