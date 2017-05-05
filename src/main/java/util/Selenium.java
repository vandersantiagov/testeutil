package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Classe Utils para o Selenium
 * Identifica qual o browser escolhido no config.properties e inicializa o webdriver correspondente
 */
public class Selenium {
	
private static WebDriver driver = null;
	
	
	/**
	 * Verifica qual o browser escolhido no arquivo de propriedades
	 * inicializa o driver apropriado e o retorna
	 * @return retorna instância do WebDriver
	 */
	public static WebDriver getDriver() {
		String browser = Property.BROWSER_NAME;
		String version = Property.BROWSER_VERSION;
		if (driver == null) {
			if (BrowserEnum.CHROME.toString().equals(browser)) {
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.CHROME, version);
				driver = new ChromeDriver();
				
			} else if (BrowserEnum.IE.toString().equals(browser)) {
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.IE, version);
				driver = new InternetExplorerDriver(capabilities);
			}
			else if (BrowserEnum.PHANTOMJS.toString().equals(browser)) {
				DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
				desiredCapabilities.setJavascriptEnabled(true);
				desiredCapabilities.setCapability("takeScreenshot", true);
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.PHANTOMJS, version);
				driver = new PhantomJSDriver(desiredCapabilities);
							
			}else  if (BrowserEnum.FIREFOX.toString().equals(browser)){
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.FIREFOX, version);
				driver = new FirefoxDriver(); 
			}
		}
		return driver;
	}

}
