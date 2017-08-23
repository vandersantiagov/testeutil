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
import java.util.List;
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
		
	/*
	*****************************METODOS GLOBAIS PARA CHAMADA NAS CLASSES*****************************
	**/

	/**
	 * Método para instanciar objetos HTML
	 * @Author Fábio Heller
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
	 * Objetivo: Verificar se duas frases são iguais.
	 * @Author Fábio Heller
	 */
	public static void validarTexto(String texto, By campo){
		assertThat("Título Incorreto!!",  driver.findElement(campo).getText(), is(texto));
	}
	
	/**
	 * Método que efetua a ação de um clique
	 * @Author Fábio Heller
	 */
	 public static void umClique(By ElementoOpcaoClick1){
		By correctLocator = null;
		correctLocator = ElementoOpcaoClick1;
		driver.findElement(correctLocator).click();
	 }
	 
	/**
	 *  Método que efetua a ação de dois cliques
	 * @Author Fábio Heller
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
	 * @Author Fábio Heller
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
	 * @Author Fábio Heller
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
	 * @Author Fábio Heller
	 */
	public static void inserirDadoNoCampo(String textoAInserir, By nomeElemento) { 
		wait.until(ExpectedConditions.visibilityOfElementLocated(nomeElemento));
		driver.findElement(nomeElemento);
		driver.findElement(nomeElemento).clear();
		driver.findElement(nomeElemento).sendKeys(textoAInserir);
	}	

	/**
	 * Método que valida uma string em uma janela/popup aberta
	 * @Author Fábio Heller
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
	 * @Author Fábio Heller
	 */
	public static void fecharDriverAposJanelaPopUpDetalhamento (){
		driver.close();
	}
	
	/**
	 * Comando de aceitar o alerta de um Javascript
	 * @Author Antonio Bernardo
	 */
	@SuppressWarnings("unused")
	public static void aceitarCancelamentoJavaScriptSicaf(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSicaf runner;
		WebDriver driver =  (WebDriver ) SeleniumSicaf.getDriver();
		driver.switchTo().alert().accept();
	}
	
	/**
	 * Comando de cancelar o alerta de um Javascript
	 * @Author Antonio Bernardo
	 */ 
	@SuppressWarnings("unused")
	public static void cancelarAlertaCancelamentoJavaScriptSicaf(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSicaf runner;
		WebDriver driver =  (WebDriver ) SeleniumSicaf.getDriver();
		driver.switchTo().alert().dismiss();
	}
	
	/**
	 * Comando de aceitar o alerta de um Javascript
	 * @Author Antonio Bernardo
	 */
	@SuppressWarnings("unused")
	public static void aceitarCancelamentoJavaScriptSol(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSol runner;
		WebDriver driver =  (WebDriver ) SeleniumSol.getDriver();
		driver.switchTo().alert().accept();
	}
	
	/**
	 * Comando de cancelar o alerta de um Javascript
	 * @Author Antonio Bernardo
	 */ 
	@SuppressWarnings("unused")
	public static void cancelarAlertaCancelamentoJavaScriptSol(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSol runner;
		WebDriver driver =  (WebDriver ) SeleniumSol.getDriver();
		driver.switchTo().alert().dismiss();
	}
	
	/**
	 * Validar a existência do campo na Interface
	 * @Author Antonio Bernardo 
	 */
	public static void validarCampoVisivelNaInterface(By objetoVisivel ){
		wait.until(ExpectedConditions.visibilityOfElementLocated(objetoVisivel));
	}
	
	/**
	 * Método que clica em um item da lista de um Combobox
	 * @Author Fábio Heller
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

	* Método para criar uma subpasta no diretório ScreencShot e capturar Print. 
	* @param fileName - Nome do arquivo
	* @Athor Antonio Bernardo e Fábio Heller
	*/
	public static void capturaScreenDaTela(String subPastaProjeto, String fileName){
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 try {
			FileUtils.copyFile(scrFile, new File(diretorioPrincipal+subPastaProjeto+"\\"+fileName+".jpeg"),true);
		} catch (IOException e) {
		e.printStackTrace();
		}
	}

	/**
	* Método para excluir os arquivo na Subpasta do diretório onde estão sendo gerados os prints.	* @Author Antonio Bernardo e Fábio Heller
	*/
	public static void deletarArquivosDaSubpasta(String subPastaProjeto){
	try {
		File pasta = new File(diretorioPrincipal+subPastaProjeto+"\\");    
		File[] arquivos = pasta.listFiles();    
		for(File arquivo : arquivos) {
	    if(arquivo.getName().endsWith("jpeg") || arquivo.getName().endsWith("sql") || arquivo.getName().endsWith("out") || arquivo.getName().endsWith("txt") || arquivo.getName().endsWith("pdf")) {
	        arquivo.delete();
	    	}
		}
		}catch (NullPointerException ex){
			System.out.println("Diretorio e/ou subpastas inexistentes!");
		}catch (Exception ex){
			
		}
	}
	/**
	* Método para Criar o arquivo colocar a informação dentro do arquivo  Arquivo.txt
	* Por exemplo, copiar o número de um protocolo e colar em um arquivo texto para utilizá-lo posteriormente. (CTRL C + CTRL V)
	* @Author Antonio Bernardo e Fábio Heller
	*/
		
	public static void escreverEmArquivoTexto(By objetoCopiar, String subPastaProjeto, String nomeDoArquivo){
	try{
		FileWriter canal  = new FileWriter (new File(diretorioPrincipal+subPastaProjeto+"\\"+nomeDoArquivo+".txt"));
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
* Por exemplo, copiar o número de um protocolo que está em um arquivo txt e inserir no elemento que receberá a informação
	* @Author Antonio Bernardo
	*/
	public static void lerArquivoTexto(String subPastaProjeto, String nomeDoArquivo, By elementoRecebedorValor) throws IOException{
		@SuppressWarnings("unused")
		String conteudo = ""; 
		try{
			BufferedReader ler = new BufferedReader(new FileReader(diretorioPrincipal+subPastaProjeto+"\\"+nomeDoArquivo+".txt"));
			String linha = ler.readLine();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementoRecebedorValor));
			driver.findElement(elementoRecebedorValor).clear();
			driver.findElement(elementoRecebedorValor).sendKeys(linha);
						
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
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(diretorioPrincipal+subPastaProjeto+"\\"+nomeDoArquivo+".pdf")));
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
		file_input.sendKeys(diretorioPrincipal+subPastaProjeto+"\\"+nomeDoArquivo+".pdf");
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

	/**
	* Método para selecionar submenu quando houver a necessidade de rolar a barra de rolagem
	* @Author Jacqueline Lucas
	*/	
    public static void cliqueComBarraDeRolagem(By menu, By submenu){
        Actions action = new Actions(driver);
        driver.findElement(menu).click();       
        WebElement element = driver.findElement(menu);
        action.moveToElement(element).build().perform();
        action.click(element).build().perform();
        
        driver.findElement(submenu).click();   
        action.moveToElement(element).build().perform();
        action.click(element).build().perform();
 }
	
	/**
	* Método para clicar em um campo checkbox
	* esse método irá setar o elemento da tela conforme o value seja (true,false,0,1,2,3...)
	* @Author Jacqueline Lucas
	*/		
    public static void clickCampoCheckBox(By nomeCampo, String valueDoCampo) throws InterruptedException{
        final List<WebElement> rdBtn_Campo = driver.findElements((nomeCampo));
        int size = rdBtn_Campo.size();
        for (int i=0; i< size; i++)
        {
               String sValue = rdBtn_Campo.get(i).getAttribute("value"); 
               if (sValue.equalsIgnoreCase(valueDoCampo))
               {
                      rdBtn_Campo.get(i).click();
               }
        }
    }
	/**
	 * Método para um tempo para o 
	 * @Author Antonio Bernardo
	 */
    public static void aguardarOProximoPasso(int valorEmMilisegundos) throws InterruptedException{
    	Thread.sleep(valorEmMilisegundos);
    }
    	
	/*
	*****************************METODOS DEFINIDOS E JÁ UTLIZADOS NO ARCHETYPE*****************************
	**/
    
    public static String diretorioPrincipal = new String ("Z:\\ArtefatosWebdriver\\");
    
	/**
	 * Instância privada do WebDriver que virá da suite de teste
	 * Objetivo: Definir o objetivo que será utilizado. No caso, o WebDriver 
	 * @Author Fábio Heller
	 */
	private static WebDriver driver;
	private static WebDriverWait wait;
	
	/**
	 * Construtor que ira adicionar a instância do WebDriver para utilização dos métodos
	 * @Author Fábio Heller
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
	 * Construtor que irá fechar a instância que foi aberta na anotação BeforeClasse
	 * @Author Antonio Bernardo
	 */
	public static Boolean isAllTestsExecution = true;
	
	@AfterClass
	public static void quitAmbiente() throws Exception {
		driver.quit();
	}
}

