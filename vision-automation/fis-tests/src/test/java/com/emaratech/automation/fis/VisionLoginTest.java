package com.emaratech.automation.fis;

import org.springframework.context.annotation.Configuration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.emaratech.automation.model.common.LoginModel;
import com.emaratech.util.test.UnitTestClassBase;
import com.hp.lft.report.Reporter;
import com.hp.lft.report.Status;
import com.hp.lft.sdk.web.Browser;
import com.hp.lft.sdk.web.BrowserDescription;
import com.hp.lft.sdk.web.BrowserFactory;
import com.hp.lft.sdk.web.BrowserType;

@Configuration
public class VisionLoginTest extends UnitTestClassBase{

	public VisionLoginTest() {
		// Change this constructor to private if you supply your own public
		// constructor
	}

	@AfterClass
	public void tearDown() throws Exception {
	}

	@Test
	public void loginSuccussFlow() throws Exception {
		try {
			LoginModel loginModel = new LoginModel(browser);
			loginModel.Localhost8080VisioncoreIndexJspPage().LaunchApplicationButton().click();
			
			browser.close();
			
			Thread.sleep(3000);
			browser = BrowserFactory.attach(new BrowserDescription.Builder().title("VISION-GDRFA").type(BrowserType.CHROME).build());
			
			loginModel = new LoginModel(browser);
			loginModel.VISIONGDRFAPage().JUsernameEditField().setValue("cs_officer2");
			loginModel.VISIONGDRFAPage().JPasswordEditField().setValue("1");
			loginModel.VISIONGDRFAPage().LOGINLink().click();
			Reporter.reportEvent("Login", "Success", Status.Passed);
		} catch (Exception e) {
			Reporter.reportEvent("Login", e.getMessage(), Status.Failed);
			throw e;
		}
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}
}