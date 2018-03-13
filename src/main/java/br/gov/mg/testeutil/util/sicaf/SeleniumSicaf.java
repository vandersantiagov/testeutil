package br.gov.mg.testeutil.util.sicaf;

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
 * Classe Utils para o Selenium Identifica qual o browser escolhido no
 * config.properties e inicializa o webdriver correspondente
 */
public class SeleniumSicaf {

	private static WebDriver driver = null;

//	public static final String USERNAME = "antoniobernardoc1";
//	public static final String AUTOMATE_KEY = "Fzys9wy8R1RxVfJPSu3k";
//	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

	/**
	 * Verifica qual o browser escolhido no arquivo de propriedades inicializa o
	 * driver apropriado e o retorna
	 * 
	 * @return retorna inst�ncia do WebDriver
	 */
	public static WebDriver getDriver() {
		String browser = PropertySicaf.BROWSER_NAME;
		String version = PropertySicaf.BROWSER_VERSION;
//		
//		//For HTTP http://proxy.fazenda.mg.gov.br:8083/proxy.pac
//		System.getProperties().put("http.proxyHost", "http://proxy.fazenda.mg.gov.br");
//		System.getProperties().put("http.proxyPort", "8083");
//		System.getProperties().put("http.proxyUser", "");
//		System.getProperties().put("http.proxyPassword", "");
//
//		DesiredCapabilities caps = new DesiredCapabilities();
//		caps.setCapability("browser", "Chrome");
//		caps.setCapability("browser_version", "65.0");
//		caps.setCapability("os", "Windows");
//		caps.setCapability("os_version", "10");
//		caps.setCapability("resolution", "1920x1080");
//		caps.setCapability("browserstack.debug", "true");
//
//		try {
//			driver = new RemoteWebDriver(new URL(URL), caps);
//			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//			driver.get("http://pintranet2.fazenda.mg.gov.br/sicaf/");
//		} catch (MalformedURLException e) {
//			System.out.println("houveram problemas com a URL" + e.getMessage());
//		}

		if (driver == null) {
			if (BrowserEnum.CHROME.toString().equals(browser)) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.CHROME, version);
				driver = new ChromeDriver();

			} else if (BrowserEnum.IE.toString().equals(browser)) {
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.IE, version);
				driver = new InternetExplorerDriver();
			} else if (BrowserEnum.PHANTOMJS.toString().equals(browser)) {
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.PHANTOMJS, version);
				driver = new PhantomJSDriver();

			} else if (BrowserEnum.FIREFOX.toString().equals(browser)) {
				new DesiredCapabilities();
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				// capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
				// true);
				capabilities.setCapability("marionette", true);
				FTPDownloadDriveBrowser.obterDriver(BrowserEnum.FIREFOX, version);
				driver = new FirefoxDriver();

				/*
				 * FTPDownloadDriveBrowser.obterDriver(BrowserEnum.FIREFOX,
				 * version); driver = new FirefoxDriver();
				 */
			}
		}
		return driver;
	}
}
