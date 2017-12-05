package br.gov.mg.testeutil.util.sol;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.gov.mg.testeutil.util.BrowserEnum;
import br.gov.mg.testeutil.util.FTPDownloadDriveBrowser;

/**
 * Classe Utils para o Selenium
 * Identifica qual o browser escolhido no config.properties e inicializa o webdriver correspondente
 */
public class SeleniumSol {
	
private static WebDriver driver = null;
	
	
	/**
	 * Verifica qual o browser escolhido no arquivo de propriedades
	 * inicializa o driver apropriado e o retorna
	 * @return retorna inst�ncia do WebDriver
	 */
	public static WebDriver getDriver() {
		String browser = PropertySol.BROWSER_NAME;
		String version = PropertySol.BROWSER_VERSION;
		if (driver == null) {
			if (BrowserEnum.CHROME.toString().equals(browser)) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.CHROME, version);
				driver = new ChromeDriver();
					
				
			} else if (BrowserEnum.IE.toString().equals(browser)) {
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.ACCEPT);
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.IE, version);
				driver = new InternetExplorerDriver();
			}
			else if (BrowserEnum.PHANTOMJS.toString().equals(browser)) {
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.PHANTOMJS, version);
				driver = new PhantomJSDriver();
							
			}else  if (BrowserEnum.FIREFOX.toString().equals(browser)){
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.FIREFOX, version);
				driver = new FirefoxDriver(); 
			}
		}
		return driver;
	}

}
