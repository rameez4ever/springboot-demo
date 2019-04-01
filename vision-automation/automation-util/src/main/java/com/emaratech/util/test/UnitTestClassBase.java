package com.emaratech.util.test;

import java.net.URI;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.hp.lft.report.Reporter;
import com.hp.lft.report.Status;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;
import com.hp.lft.sdk.web.Browser;
import com.hp.lft.sdk.web.BrowserFactory;
import com.hp.lft.sdk.web.BrowserType;
import com.hp.lft.unittesting.UnitTestBase;

public class UnitTestClassBase extends UnitTestBase {

    protected static UnitTestClassBase instance;
    protected Browser browser;

    public static void globalSetup(Class<? extends UnitTestClassBase> testClass) throws Exception {
        if (instance == null)
            instance = testClass.newInstance();
        instance.classSetup();
    }
    
    @BeforeSuite(alwaysRun=true)
	public void Start_Engine() throws Exception
	{							
    	String leaftServer = "ws://localhost:5095";
		ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
		config.setServerAddress(new URI(leaftServer));
		SDK.init(config);
		Reporter.init();
	}
    
    @BeforeMethod(alwaysRun=true)
	public void launchBrowser() throws Exception
	{		    
	    browser = BrowserFactory.launch(BrowserType.CHROME);
	    logger.info("Browser Launched");
	    browser.navigate("http://localhost:8080/visioncore/index.jsp");
	}

    public static void globalTearDown() throws Exception {
        instance.classTearDown();
        getReporter().generateReport();
    }

    @ClassRule
    public static TestWatcher classWatcher = new TestWatcher() {
        @Override
        protected void starting(org.junit.runner.Description description) {
            className = description.getClassName();
        }
    };

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void starting(org.junit.runner.Description description) {
            testName = description.getMethodName();
        }
        @Override
        protected void failed(Throwable e, org.junit.runner.Description description) {
            setStatus(Status.Failed);
        }
        
        @Override
        protected void succeeded(org.junit.runner.Description description) {
            setStatus(Status.Passed);
        }
    };

    @Override
    protected String getTestName() {
        return testName;
    }
    
    @Override
    protected String getClassName() {
        return className;
    }

    protected static String className;
    protected String testName;
    
}