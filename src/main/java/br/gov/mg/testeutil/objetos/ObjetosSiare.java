package br.gov.mg.testeutil.objetos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.gov.mg.testeutil.util.sicaf.PropertySicaf;
import br.gov.mg.testeutil.util.sicaf.SeleniumSicaf;
import br.gov.mg.testeutil.util.sol.PropertySol;
import br.gov.mg.testeutil.util.sol.SeleniumSol;
//import br.gov.mg.testeutil.util.sol.UtilsSol;

public class ObjetosSiare {
	/**
	 * Instância privada do webDriver que vira da suite de teste
	 */
	private static WebDriver driver;
	private static WebDriverWait wait;
	
	/**
	 * Construtor que ira adicionar a instancia do WebDriver para utilizacao dos metodos
	 */

	public static void setAmbienteSol (int tempoDeEspera){
		driver = SeleniumSol.getDriver();
		//wait = new WebDriverWait(driver, tempoDeEspera);
		driver.navigate().to(PropertySol.SITE_ADDRESS);
		driver.manage().window().maximize();
	}

	public static void setAmbienteSicaf (int tempoDeEspera){
		driver = SeleniumSol.getDriver();
		//wait = new WebDriverWait(driver, tempoDeEspera);
		driver.navigate().to(PropertySicaf.SITE_ADDRESS);
		driver.manage().window().maximize();
	}
	
	/*
	public static void setAmbienteSol (WebDriver setDriverAmbienteSol, int tempoDeEspera){
		setDriverAmbienteSol = SeleniumSol.getDriver();
		wait = new WebDriverWait(setDriverAmbienteSol, tempoDeEspera);
		setDriverAmbienteSol.navigate().to(PropertySol.SITE_ADDRESS);
		setDriverAmbienteSol.manage().window().maximize();
	}
	

	public static void setAmbienteSicaf (WebDriver setDriverAmbienteSicaf, int tempoDeEspera){
		setDriverAmbienteSicaf = SeleniumSol.getDriver();
		wait = new WebDriverWait(setDriverAmbienteSicaf, tempoDeEspera);
		setDriverAmbienteSicaf.navigate().to(PropertySicaf.SITE_ADDRESS);
		setDriverAmbienteSicaf.manage().window().maximize();
	}
	
	/*
	
	private static WebDriver driver;
	private static WebDriverWait wait;
	
	*/
	static {
		driver = SeleniumSol.getDriver();
		wait = new WebDriverWait(driver, 30);
	}
	/**
	 * Metodo para instanciar objetos HTML
	 */
	public static By campoID (String nomeElementoID){
		By cpoIDBy = By.id(nomeElementoID);
		return cpoIDBy;
	}
	
	public static By campoName (String nomeElementoName){
		By cpoNameBy = By.name(nomeElementoName);
		return cpoNameBy;
	}
	
	public static By campoXpath (String nomeElementoXpath){
		By cpoXpathBy = By.xpath(nomeElementoXpath);
		return cpoXpathBy;
	}
	
	public static By campoLinkText (String nomeElementoLinkText){
		By cpoLinkTextBy = By.linkText(nomeElementoLinkText);
		return cpoLinkTextBy;
	}
	
	public static By campoCssSelector (String nomeElementocssSelector){
		By cpoCssSelectorBy = By.linkText(nomeElementocssSelector);
		return cpoCssSelectorBy;
	}

	/**
	 * Método que valida a igualdade entre duas strings
	 */
	public static void validarTexto(String texto, By campo){
		assertThat("Título Incorreto!!",  driver.findElement(campo).getText(), is(texto));
	}
	
	/**
	 * Método que efetua a ação de um clique
	 * 
	 */
	 public static void umClique(By ElementoOpcaoClick1){
		By correctLocator = null;
		correctLocator = ElementoOpcaoClick1;
		driver.findElement(correctLocator).click();
	 }
	 
	/**
	 *  Método que efetua a ação de dois cliques
	 * 
	 */
	 public static void doisCliques(By ElementoOpcaoClick1, By ElementoOpcaoClik2){
		By correctLocator = null;
		correctLocator = ElementoOpcaoClick1;
		driver.findElement(correctLocator).click();
		correctLocator = ElementoOpcaoClik2;
		driver.findElement(correctLocator).click();
	 }
		
	/**
	 * Método que efetua a ação de três cliques
	 * 
	 */
	 public static void tresCliques(By ElementoOpcaoClick1, By ElementoOpcaoClik2, By ElementoOpcaoClik3){
		By correctLocator = null;
		correctLocator = ElementoOpcaoClick1;
		driver.findElement(correctLocator).click();
		correctLocator = ElementoOpcaoClik2;
		driver.findElement(correctLocator).click();
		correctLocator = ElementoOpcaoClik3;
		driver.findElement(correctLocator).click();
	 }
		
	/**
	 * Método que efetua a ação de quatro cliques
	 * 
	 */
	 public static void quatroCliques(By ElementoOpcaoClick1, By ElementoOpcaoClik2, By ElementoOpcaoClik3, By ElementoOpcaoClik4){
		By correctLocator = null;
		correctLocator = ElementoOpcaoClick1;
		driver.findElement(correctLocator).click();
		correctLocator = ElementoOpcaoClik2;
		driver.findElement(correctLocator).click();
		correctLocator = ElementoOpcaoClik3;
		driver.findElement(correctLocator).click();
		correctLocator = ElementoOpcaoClik4;
		driver.findElement(correctLocator).click();
	 }
	 
	/**
	 * Método que insere um valor em um campo
	 * 
	 */
	public static void inserirDadoNoCampo(String textoAInserir, By nomeElemento) { 
		wait.until(ExpectedConditions.visibilityOfElementLocated(nomeElemento));
		driver.findElement(nomeElemento);
		driver.findElement(nomeElemento).clear();
		driver.findElement(nomeElemento).sendKeys(textoAInserir);
	}	

	/**
	 * Método que valida uma string em uma janela/popup aberta
	 * 
	 */
	public static void validaJanelaPopUpDetalhamento (String NomePopup){
		Set<String> janelas = driver.getWindowHandles();
		for (String janela : janelas) {
			driver.switchTo().window(janela);
			if (janela.equals("NomePopup")) {
				break;
			}
		}
	}	

	/**
	 * Fechar janela/popup 
	 * 
	 */
	public static void fecharDriverAposJanelaPopUpDetalhamento (){
		driver.close();
	}
	
	/*
		public static void campocheckBoxUtilizaPEDNao() throws InterruptedException{
			wait.until(ExpectedConditions.visibilityOfElementLocated(checkBoxUtilizaPEDNao));
			driver.findElement(checkBoxUtilizaPEDNao).click(); 		
		}
		
		

	public static void ClickcampocheckBox(By nomeElemento, boolean opc) throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(nomeElemento));
		driver.findElement(nomeElemento).equals(opc);
	}
		
		public static void campocheckBoxUtilizaPEDSim() throws InterruptedException{
			wait.until(ExpectedConditions.visibilityOfElementLocated(checkBoxUtilizaPEDSim));
			driver.findElement(checkBoxUtilizaPEDSim).click(); 	
	}
	
		/**
	 * @throws InterruptedException  
	 * Esse Método é utilizado somente quando o valor que deseja selecionar não é exibido na tela.
	 * Clique no Combobox Selecionar Municicipo de Belo Horizonte
	 
	public static void selecionarMunicipioBeloHorizonte() throws InterruptedException{
		Actions action = new Actions(driver);
		driver.findElement(selecionarMunicipiodaOcorrencia).click(); 	
		WebElement element = driver.findElement(selecionarMunicipioBH);
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
	} 
	
	*/
	
	
		
}
