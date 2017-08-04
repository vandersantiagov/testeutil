package br.gov.mg.testeutil.util.sol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.Date;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.gov.mg.testeutil.util.sol.SeleniumSol;

/**
 * Classe com m�todos de apoio, que otimizam a codifica��o das classes de p�gina.
 * @author Antonio Bernardo
 *
 */

public class UtilsSol {
	private static final WebDriver driver;
	private static final WebDriverWait wait;
	
	static{
		driver = SeleniumSol.getDriver();
		wait = new WebDriverWait(driver, 10);
	}

	/**
	 * Método para verificar a visibilidade de um elemento utilizando o locator
	 * @param locator
	 */
	public static void isVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * Método para verificar a visibilidade de um elemento utilizando o ID
	 * @param id
	 */
	public static void isVisible(String id) {
		isVisible(By.id(id));
	}
	
	/**
	 * Método para verificar a presen�a de um elemento utilizando o locator
	 * @param locator
	 */
	public static void isLocated(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/**
	 * Método para verificar a presen�a de um elemento utilizando o ID
	 * @param id
	 */
	public static void isLocated(String id) {
		isLocated(By.id(id));
	}
	
	/**
	 * Método para verificar se um elemento � clic�vel utilizando o locator
	 * @param locator
	 */
	public static void isClickable(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	/**
	 * Método para verificar se um elemento � clic�vel utilizando o ID
	 * @param id
	 */
	public static void isClickable(String id) {
		isClickable(By.id(id));
	}
	
	/**
	 * Método para exluir os arquivo no ScreencShot
	 */
	
	public static void deltarArquivosScreenshot (){
	File pasta = new File("Z:\\SeleniumScreenShots");    
	File[] arquivos = pasta.listFiles();    
	for(File arquivo : arquivos) {
	    if(arquivo.getName().endsWith("jpeg") || arquivo.getName().endsWith("sql") || arquivo.getName().endsWith("out") || arquivo.getName().endsWith("txt")) {
	        arquivo.delete();
	    	}
		}
	}
	
	/**
	 * Método para capturar screenshot
	 * @param fileName - Nome do arquivo
	 */
	public static void takeScreenshot(String fileName){
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//	    Date data = new Date();
	    try {
			FileUtils.copyFile(scrFile, new File("Z:\\SeleniumScreenShots\\"+fileName+".jpeg"),true);
//		    FileUtils.copyFile(scrFile, new File("Z:\\SeleniumScreenShots\\"+fileName+" - "+data.getTime()+".jpeg"),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	* Método para Criar o arquivo Copiar e Colocar a informação dentro do arquivo  Arquivo.txt
	* @param fileName - Nome do arquivo
	*/
	static By testeCopiarLer = By.className("itemOrdenacao");
	
	public static void escreverEmArquivoTexto(){
	try{
		FileWriter canal  = new FileWriter (new File("Z:\\SeleniumScreenShots\\Arquivo.txt"));
		PrintWriter escrever = new PrintWriter(canal);
		escrever.println(driver.findElement(testeCopiarLer).getText());
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
	* Definição Técnica dos locators utilizados na página
	* Método para Leitura de um valor em arquivo e inserir o valor no campo editável. 
	*/		
	static By escreverArquivo = By.id("query");
	
	public static void lerArquivo(String caminho) throws IOException{
		@SuppressWarnings("unused")
		String conteudo = ""; 
		try{
			BufferedReader ler = new BufferedReader(new FileReader("Z:\\SeleniumScreenShots\\Arquivo.txt"));
			String linha = ler.readLine();
			wait.until(ExpectedConditions.visibilityOfElementLocated(escreverArquivo));
			driver.findElement(escreverArquivo).clear();
			driver.findElement(escreverArquivo).sendKeys(linha);
						
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
	*###########################EXLUIR ARQUIVOS QUE ESTÃO NAS PASTAS###########################
	* Método para exluir os arquivo no ScreencShot Atividades_Fiscais_QA
	*/
	public static void deltarArquivosScreenshotAtividades_Fiscais_QA(){
	File pasta = new File("Z:\\SeleniumScreenShots\\Atividades_Fiscais_QA\\");    
	File[] arquivos = pasta.listFiles();    
	for(File arquivo : arquivos) {
	    if(arquivo.getName().endsWith("jpeg") || arquivo.getName().endsWith("sql") || arquivo.getName().endsWith("out") || arquivo.getName().endsWith("txt")) {
	        arquivo.delete();
	    	}
		}
	}
	/**
	* Método para exluir os arquivo no ScreencShot DAF_QA
	*/
	public static void deltarArquivosScreenshotDAF_QA(){
	File pasta = new File("Z:\\SeleniumScreenShots\\DAF_QA\\");    
	File[] arquivos = pasta.listFiles();    
	for(File arquivo : arquivos) {
	    if(arquivo.getName().endsWith("jpeg") || arquivo.getName().endsWith("sql") || arquivo.getName().endsWith("out") || arquivo.getName().endsWith("txt")) {
	        arquivo.delete();
	    	}
		}
	}
	/**
	*###########################CRIAR OS ARQUIVOS DE IMAGENS DAS TELAS###########################
	* Método para capturar screenshot
	* @param fileName - Nome do arquivo
	*/
	public static void takeScreenshotAtividades_Fiscais_QA(String fileName){
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		 Date data = new Date();
		 try {
			FileUtils.copyFile(scrFile, new File("Z:\\SeleniumScreenShots\\Atividades_Fiscais_QA\\"+fileName+".jpeg"),true);
//		    FileUtils.copyFile(scrFile, new File("Z:\\SeleniumScreenShots\\"+fileName+" - "+data.getTime()+".jpeg"),true);
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	/**
	* Método para capturar screenshot
	* @param fileName - Nome do arquivo
	*/
	public static void takeScreenshotDAF_QA(String fileName){
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//	    Date data = new Date();
	    try {
			FileUtils.copyFile(scrFile, new File("Z:\\SeleniumScreenShots\\DAF_QA\\"+fileName+".jpeg"),true);
//		    FileUtils.copyFile(scrFile, new File("Z:\\SeleniumScreenShots\\"+fileName+" - "+data.getTime()+".jpeg"),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pause for X milliseconds.
	 * 
	 * @param iTimeInMillis
	 */
	@Deprecated
	public static void wait(final int iTimeInMillis) {
		try {
			Thread.sleep(iTimeInMillis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}


}
