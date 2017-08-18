package br.gov.mg.testeutil.metodos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.gov.mg.testeutil.util.sicaf.PropertySicaf;
import br.gov.mg.testeutil.util.sicaf.SeleniumSicaf;
import br.gov.mg.testeutil.util.sol.PropertySol;
import br.gov.mg.testeutil.util.sol.SeleniumSol;
//import br.gov.mg.testeutil.util.sol.UtilsSol;

public class MetodosSiare {
	/**
	 * Instância privada do webDriver que vira da suite de teste
	 * @Athor Fábio Heller
	 */
	private static WebDriver driver;
	private static WebDriverWait wait;
	
	/**
	 * Construtor que ira adicionar a instancia do WebDriver para utilizacao dos metodos
	 * @Athor Fábio Heller
	 */
	@BeforeClass
	public static void setAmbienteSol (){
		driver = SeleniumSol.getDriver();
		wait = new WebDriverWait(driver, 30);
		driver.navigate().to(PropertySol.SITE_ADDRESS);
		driver.manage().window().maximize();
	}
	@BeforeClass
	public static void setAmbienteSicaf (){
		driver = SeleniumSicaf.getDriver();
		wait = new WebDriverWait(driver, 30);
		driver.navigate().to(PropertySicaf.SITE_ADDRESS);
		driver.manage().window().maximize();
		
	}
	
	/**
	 * Construtor que ira fechar a instântica que foi aberta na anotação BeforeClasse
	 * @Athor Antonio Bernardo
	 */
	public static Boolean isAllTestsExecution = true;
	
	@AfterClass
	public static void quitAmbiente() throws Exception {
		driver.quit();
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
	
	
	static {
		//driver = SeleniumSicaf.getDriver();
		wait = new WebDriverWait(driver, 30);
	}*/
	/**
	 * Metodo para instanciar objetos HTML
	 * @Athor Fábio Heller
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
	 * @Athor Fábio Heller
	 */
	public static void validarTexto(String texto, By campo){
		assertThat("Título Incorreto!!",  driver.findElement(campo).getText(), is(texto));
	}
	
	/**
	 * Método que efetua a ação de um clique
	 * @Athor Fábio Heller
	 */
	 public static void umClique(By ElementoOpcaoClick1){
		By correctLocator = null;
		correctLocator = ElementoOpcaoClick1;
		driver.findElement(correctLocator).click();
	 }
	 
	/**
	 *  Método que efetua a ação de dois cliques
	 * @Athor Fábio Heller
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
	 * @Athor Fábio Heller
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
	 * @Athor Fábio Heller
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
	 * @Athor Fábio Heller
	 */
	public static void inserirDadoNoCampo(String textoAInserir, By nomeElemento) { 
		wait.until(ExpectedConditions.visibilityOfElementLocated(nomeElemento));
		driver.findElement(nomeElemento);
		driver.findElement(nomeElemento).clear();
		driver.findElement(nomeElemento).sendKeys(textoAInserir);
	}	

	/**
	 * Método que valida uma string em uma janela/popup aberta
	 * @Athor Fábio Heller
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
	 * @Athor Fábio Heller
	 */
	public static void fecharDriverAposJanelaPopUpDetalhamento (){
		driver.close();
	}
	
	/**
	 * Comando de aceitar o alerta de um Javascript
	 * @Athor Antonio Bernardo
	 */
	@SuppressWarnings("unused")
	public static void aceitarCancelamentoJavaScriptSicaf(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSicaf runner;
		WebDriver driver =  (WebDriver) SeleniumSicaf.getDriver();
		driver.switchTo().alert().accept();
	}
	
	/**
	 * Comando de cancelar o alerta de um Javascript
	 * @Athor Antonio Bernardo
	 */
	//Objeto para o Cancelamento da mensagem JavaScritp na tela de Cancelamento AIAF 
	@SuppressWarnings("unused")
	public static void cancelarAlertaCancelamentoJavaScriptSicaf(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSicaf runner;
		WebDriver driver =  (WebDriver) SeleniumSicaf.getDriver();
		driver.switchTo().alert().dismiss();
	}
	
	/**
	 * Comando de aceitar o alerta de um Javascript
	 * @Athor Antonio Bernardo
	 */
	@SuppressWarnings("unused")
	public static void aceitarCancelamentoJavaScriptSol(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSol runner;
		WebDriver driver =  (WebDriver) SeleniumSol.getDriver();
		driver.switchTo().alert().accept();
	}
	
	/**
	 * Comando de cancelar o alerta de um Javascript
	 * @Athor Antonio Bernardo
	 */
	//Objeto para o Cancelamento da mensagem JavaScritp na tela de Cancelamento AIAF 
	@SuppressWarnings("unused")
	public static void cancelarAlertaCancelamentoJavaScriptSol(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSol runner;
		WebDriver driver =  (WebDriver) SeleniumSol.getDriver();
		driver.switchTo().alert().dismiss();
	}
	
	/**
	 * Validar a existência do campo na Interface
	 * @Athor Antonio Bernardo 
	 */
	public static void validarCampoVisivelNaInterface(By objetoVisivel ){
		wait.until(ExpectedConditions.visibilityOfElementLocated(objetoVisivel));
	}
	
	/**
	 * @throws InterruptedException  
	 * Método de clique no Combobox e seleção de uma opção na lista
	 * @Athor Fábio Heller
	 */
	public static void selecionarOpcaoEmCombobox(By combobox, By opcaoCombobox) throws InterruptedException{
		Actions action = new Actions(driver);
		driver.findElement(combobox).click(); 	
		WebElement element = driver.findElement(opcaoCombobox);
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		element.click();
	}
	
	/**
	* Método para exluir os arquivo na Subpasta do diretório ScreencShot 
	* @Athor Antonio Bernardo e Fábio Heller
	*/
	public static void deletarArquivosDaSubpasta(String subPastaProjeto){
	File pasta = new File("Z:\\Artefatos Selenium Webdriver\\"+subPastaProjeto+"\\");    
	File[] arquivos = pasta.listFiles();    
	for(File arquivo : arquivos) {
	    if(arquivo.getName().endsWith("jpeg") || arquivo.getName().endsWith("sql") || arquivo.getName().endsWith("out") || arquivo.getName().endsWith("txt") || arquivo.getName().endsWith("pdf")) {
	        arquivo.delete();
	    	}
		}
	}
	
	/**
	* Método para criar uma subpasta no diretório ScreencShot e capturar Print. 
	* @param fileName - Nome do arquivo
	* @Athor Antonio Bernardo e Fábio Heller
	*/
	public static void capturaScreenDaTela(String subPastaProjeto, String fileName){
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 try {
			FileUtils.copyFile(scrFile, new File("Z:\\Artefatos Selenium Webdriver\\"+subPastaProjeto+"\\"+fileName+".jpeg"),true);
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	/**
	* Método para Criar o arquivo colocar a informação dentro do arquivo  Arquivo.txt
	* @param fileName - Nome do arquivo
	* @Athor Antonio Bernardo e Fábio Heller
	*/
		
	public static void escreverEmArquivoTexto(By objetoCopiar, String subPastaProjeto, String nomeDoArquivo){
	try{
		FileWriter canal  = new FileWriter (new File("Z:\\Artefatos Selenium Webdriver\\"+subPastaProjeto+"\\"+nomeDoArquivo+".txt"));
		PrintWriter escrever = new PrintWriter(canal);
		String guardaValor = null;
		guardaValor = driver.findElement(objetoCopiar).getText();
		escrever.println (guardaValor);
		escrever.close();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	public static WebDriverWait getWait() {
		return wait;
	}
	
	/**
	* Método para ler o Arquivo.txt que foi criado e inserido um valor
	* @param fileName - Nome do arquivo
	* @Athor Antonio Bernardo
	*/
	public static void lerArquivoTexto(String subPastaProjeto, String nomeDoArquivo, By lerArquivo) throws IOException{
		@SuppressWarnings("unused")
		String conteudo = ""; 
		try{
			BufferedReader ler = new BufferedReader(new FileReader("Z:\\Artefatos Selenium Webdriver\\"+subPastaProjeto+"\\"+nomeDoArquivo+".txt"));
			String linha = ler.readLine();
			wait.until(ExpectedConditions.visibilityOfElementLocated(lerArquivo));
			driver.findElement(lerArquivo).clear();
			driver.findElement(lerArquivo).sendKeys(linha);
						
			try{ 
				linha = ler.readLine();
				while (linha != null){
					conteudo += linha+"\r\n";
					linha =ler.readLine();
				}
				ler.close();
			
			}catch (IOException ex){
				System.out.println("Erro: Não foi possivel ler arquivo!");
			}
	} catch (FileNotFoundException ex){
		
	}
}
	/**
	* Método para criar um arquivo .pdf e inserido um valor
	* @Athor Jacqueline Lucas
	*/
	public static void criarArquivoPDFEInserirTexto(String subPastaProjeto, String nomeDoArquivo, String inserirTexto){
	 try{
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("Z:\\Artefatos Selenium Webdriver\\"+subPastaProjeto+"\\"+nomeDoArquivo+".pdf")));
		document.open();
		document.add(new Paragraph(inserirTexto));
		document.close();
		writer.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
				}
			}
	/**
	* Método para anexar um arquivo 
	* @Athor Jacqueline Lucas e Antonio Bernardo
	*/	
	public static void comandoAnexarArquivo(By nomeElemento, String subPastaProjeto, String nomeDoArquivo){
		wait.until(ExpectedConditions.visibilityOfElementLocated(nomeElemento));
		WebElement file_input = driver.findElement(nomeElemento);
		file_input.sendKeys("Z:\\Artefatos Selenium Webdriver\\"+subPastaProjeto+"\\"+nomeDoArquivo+".pdf");
	}
	
	/**
	* Método para acessar o Menu e o Submenu1.
	* @Athor Jacqueline Lucas 
	*/	
	public static void menuSubMenuNivel1(By Menu, By subMenu1){
		Actions action = new Actions(driver);
		driver.findElement(Menu).click(); 	
		WebElement element = driver.findElement(Menu);
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		
		driver.findElement(subMenu1).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
	}
	
	/**
	* Método para acessar o Menu, Submenu1 e Submenu2  
	* @Athor Jacqueline Lucas 
	*/	
	public static void menuSubMenuNivel2(By Menu, By subMenu1, By subMenu2){
		Actions action = new Actions(driver);
		driver.findElement(Menu).click(); 	
		WebElement element = driver.findElement(Menu);
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		
		driver.findElement(subMenu1).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		
		driver.findElement(subMenu2).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
	}
	
	/**
	* Método para acessar o Menu, Submenu1, Submenu2 e Submenu3.   
	* @Athor Jacqueline Lucas 
	*/	
	public static void menuSubMenuNivel3(By Menu, By subMenu1, By subMenu2, By subMenu3){
		Actions action = new Actions(driver);
		driver.findElement(Menu).click(); 	
		WebElement element = driver.findElement(Menu);
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		
		driver.findElement(subMenu1).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		
		driver.findElement(subMenu2).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();

		driver.findElement(subMenu3).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
	}
	
	/**
	* Método para acessar o Menu, Submenu1, Submenu2 e Submenu3 e Submenu4.   
	* @Athor Jacqueline Lucas 
	*/	
	public static void menuSubMenuNivel4(By Menu, By subMenu1, By subMenu2, By subMenu3, By subMenu4){
		Actions action = new Actions(driver);
		driver.findElement(Menu).click(); 	
		WebElement element = driver.findElement(Menu);
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		
		driver.findElement(subMenu1).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		
		driver.findElement(subMenu2).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();

		driver.findElement(subMenu3).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		
		driver.findElement(subMenu4).click(); 	
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
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
