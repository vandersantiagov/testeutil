package exemplo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.gov.mg.testeutil.util.BrowserEnum;
import br.gov.mg.testeutil.util.FTPDownloadDriveBrowser;



public class SampleHeadlessEx {
	private WebDriver driver;

	private String baseUrl;
	protected static DesiredCapabilities dCaps;

	@Before
	public void setUp() throws Exception {
		
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();  
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		FTPDownloadDriveBrowser.obterDriver(BrowserEnum.IE, "11");
		FTPDownloadDriveBrowser.obterDriver(BrowserEnum.CHROME, "58");
		FTPDownloadDriveBrowser.obterDriver(BrowserEnum.FIREFOX, "53");
		//driver = new ChromeDriver(); 
		driver = new InternetExplorerDriver();
		
		baseUrl = "http://assertselenium.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void getLinksOfAssertSelenium() throws Exception {
		driver.get(baseUrl);
		//Getting all the links present in the page by a HTML tag.
		List<WebElement>  links = driver.findElements(By.tagName("a"));

		//Printing the size, will print the no of links present in the page.
		System.out.println("Total Links present is "+links.size());

		//Printing the links in the page, we get through the href attribute.
		for(int i = 0; i<links.size();i++){

			System.out.println("Links are listed "+links.get(i).getAttribute("href"));
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
//		String verificationErrorString = verificationErrors.toString();
//		if (!"&quot;&quot;".equals(verificationErrorString)) {
//			fail(verificationErrorString);
//		}
	}

}