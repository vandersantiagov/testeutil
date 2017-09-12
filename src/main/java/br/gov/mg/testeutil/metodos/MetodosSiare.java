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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import br.gov.mg.testeutil.objetos.ObjetosMetodosComuns;
import br.gov.mg.testeutil.telas.login.ObjetosTelaLoginSicaf;
import br.gov.mg.testeutil.util.sicaf.PropertySicaf;
import br.gov.mg.testeutil.util.sicaf.SeleniumSicaf;
import br.gov.mg.testeutil.util.sol.PropertySol;
import br.gov.mg.testeutil.util.sol.SeleniumSol;
//import br.gov.mg.testeutil.util.sol.UtilsSol;
//import gov.sefmg.CopiarColar.objetos.sicaf.ObjetosConsultaProtocolo;
//import gov.sefmg.CopiarColar.objetos.sicaf.ObjetosCopiarEColarValores;


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
	public static boolean validarTexto(String texto, By campo){
		boolean achou = false;
		if(driver.findElement(campo).getText().equals(texto))
		 achou = true;
		else
			assertThat("Título Incorreto!!",  driver.findElement(campo).getText(), is(texto));
		return achou;
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
	 * @throws InterruptedException  
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
	* Atualizado dia 25/08/2017 - Antonio Bernardo
	*/
		
	public static void escreverEmArquivoTexto(By objetoCopiar, String subPastaProjeto, String nomeDoArquivo){
	try{
		boolean success = (new File(diretorioPrincipal+subPastaProjeto)).mkdirs();
		if (!success) {
		// Falha no momento de criar o diretório
		}
		FileWriter canal  = new FileWriter (new File(diretorioPrincipal+subPastaProjeto+"\\"+nomeDoArquivo+".txt"));
		PrintWriter escrever = new PrintWriter(canal);
		String guardaValor = null;
		guardaValor = driver.findElement(objetoCopiar).getText();
		String str = guardaValor;
	    while (str.indexOf("-") != -1) {
		      if (str.indexOf("-") != 0) {
		        str = str.substring(0, str.indexOf("-")) +
		            str.substring(str.indexOf("-") + 1);
		      }
		      else {
		        str = str.substring(str.indexOf("-") + 1);
		      }
		    }
		    while (str.indexOf(".") != -1) {
		      if (str.indexOf(".") != 0) {
		        str = str.substring(0, str.indexOf(".")) +
		            str.substring(str.indexOf(".") + 1);
		      }
		      else {
		        str = str.substring(str.indexOf(".") + 1);
		      }
		    }
		escrever.println (str);
		escrever.close();
		}
	catch (Exception ex){
			ex.printStackTrace();
		}
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
		WebElement element = driver.findElement(Menu);		
		action.moveToElement(element).build().perform();
		driver.findElement(Menu).click(); 	
		action.click(element).build().perform();		
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu1).click(); 
	}
	
	/**
	* Método para acessar o Menu, Submenu1 e Submenu2  
	* @Athor Jacqueline Lucas 
	*/	
	public static void menuSubMenuNivel2(By Menu, By subMenu1, By subMenu2){
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(Menu);		
		action.moveToElement(element).build().perform();		
		driver.findElement(Menu).click(); 	
		action.click(element).build().perform();		
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu1).click(); 		
		element = driver.findElement(subMenu1);
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu2).click(); 
	}
	
	/**
	* Método para acessar o Menu, Submenu1, Submenu2 e Submenu3.   
	* @Athor Jacqueline Lucas 
	*/	
	public static void menuSubMenuNivel3(By Menu, By subMenu1, By subMenu2, By subMenu3){
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(Menu);		
		action.moveToElement(element).build().perform();		
		driver.findElement(Menu).click(); 	
		action.click(element).build().perform();		
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu1).click(); 		
		element = driver.findElement(subMenu1);
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu2).click(); 		
		element = driver.findElement(subMenu2);
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu3).click(); 
	}
	
	/**
	* Método para acessar o Menu, Submenu1, Submenu2 e Submenu3 e Submenu4.   
	* @Athor Jacqueline Lucas 
	*/	
	public static void menuSubMenuNivel4(By Menu, By subMenu1, By subMenu2, By subMenu3, By subMenu4){
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(Menu);		
		action.moveToElement(element).build().perform();		
		driver.findElement(Menu).click(); 	
		action.click(element).build().perform();		
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu1).click(); 		
		element = driver.findElement(subMenu1);
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu2).click(); 		
		element = driver.findElement(subMenu2);
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu3).click(); 	
		element = driver.findElement(subMenu3);
		action.moveToElement(element).build().perform();
		driver.findElement(subMenu4).click(); 
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

	/**
	* Método para capturar a data atual do sistema e acrescentar ou diminuir em dias a data capturada
	* set true com mascara ("dd/MM/yyyy") // set false sem mascara ("ddMMyyyy")
	* @Author Rogerio Cesar e Fabio Heller
	*/		    
	@SuppressWarnings("deprecation")
	public static String retornaDataAtualOuPosteriorOuAnteriorDaDataAtualDoSistema(int dias, boolean comMascara) {
           DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");	
		if (comMascara){
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		}else{
	        dateFormat = new SimpleDateFormat("ddMMyyyy");
		}
    	   Date data = new Date();
    	   data.setDate(data.getDate()+dias);
    	   return dateFormat.format(data);
    	}

	/**
	* Método para capturar a hora atual do sistema
	* @Author Fábio Heller
	*/	
	public static String retornaHoraAtualDoSistema() {
           DateFormat dateFormat = new SimpleDateFormat("HHmmss");	
    	   Date data = new Date();
    	   return dateFormat.format(data);
    	}
	
    /**
    * Método que efetua a ação de um clique em uma opção existente em Tela
    * String texto = título da tela modal
    * By campo = passar um elemento para validar o título da tela Modal 
    * By ElementoOpcaoClick1 = passar o elemento que vai receber a ação click int
    * int tempo = tempo de espera do carregamento DOM
    * 
     * @Author Jacqueline Lucas
    */
    public static void modalValidareClicar(String texto, By campo, By ElementoOpcaoClick1, int tempo) throws InterruptedException{
         Thread.sleep(tempo);
         validarTexto(texto, campo);
         By correctLocator = null;
         correctLocator = ElementoOpcaoClick1;
         driver.findElement(correctLocator).click();
    }
    
	/**
	 * Método para Criar o arquivo colocar a informação dentro do arquivo
	 * Arquivo .txt (Esse método só vai preencher se o campo a qual for
	 * recuperado tiver o valor, caso o campo não tiver o valor foi vazio, o
	 * método vai tentar novamente daqui 30 segundos) Por exemplo, copiar o
	 * responsável pelo Protocolo Gerado até que o mesmo protocolo caia na caixa
	 * do Servidor Resposável. (CTRL C + CTRL V)
	 * 
	 * @Author Antonio Bernardo e Fábio Heller Atualizado dia 28/08/2017 -
	 *         Antonio Bernardo
	 */

	public static void escreverEmArquivoTextoCasoNaoEncontradoValorNoObjeto(By objetoCopiar, String subPastaProjeto,
			String nomeDoArquivo, By elementoPesquisar, By consultaCampoProtocolo, By priorizarCampoProtocolo) {
		String dadoDoRetornoDoArquivo = "";
		try {
			boolean success = (new File(diretorioPrincipal + subPastaProjeto)).mkdirs();
			if (!success) {
				// Falha no momento de criar o diretório
			}
			boolean achou = false;
			do {
				FileWriter canal = new FileWriter(
						new File(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo + ".txt"));
				PrintWriter escrever = new PrintWriter(canal);
				String guardaValor = null;
				int contador = 0, limiteDoContador = 15;
				guardaValor = driver.findElement(objetoCopiar).getText();
				String str = guardaValor;
				if (!str.equals("")) {
					while (str.indexOf("-") != -1) {
						if (str.indexOf("-") != 0) {
							str = str.substring(0, str.indexOf("-")) + str.substring(str.indexOf("-") + 1);
						} else {
							str = str.substring(str.indexOf("-") + 1);
						}
					}
					while (str.indexOf(".") != -1) {
						if (str.indexOf(".") != 0) {
							str = str.substring(0, str.indexOf(".")) + str.substring(str.indexOf(".") + 1);
						} else {
							str = str.substring(str.indexOf(".") + 1);
						}
					}
					escrever.println(str);
					escrever.close();
					achou = true;
				} else {
					// codificar a estrutura caso não seja encontrado o
					// responsavel pelo protocolo
					MetodosSiare.umClique(elementoPesquisar);
					System.out.println("Aguardando o protocolo cair para um Servidor Responsável...");
					Thread.sleep(20000);
					contador++;
				}
				if (contador == limiteDoContador) {
					// metodo que ira priorizar o protocolo
					dadoDoRetornoDoArquivo = driver.findElement(consultaCampoProtocolo).getAttribute("value");
					System.out.println("Priorizar Protocolo.");
					MetodosSiare.umClique(ObjetosMetodosComuns.linkHomeSiare);
					MetodosSiare.validarTexto("Suas tarefas para o momento :",
							ObjetosMetodosComuns.textoValidarTituloHome);
					MetodosSiare.tresCliques(ObjetosMetodosComuns.menuAdiministracaoDeServico,
							ObjetosMetodosComuns.subMenuServico, ObjetosMetodosComuns.subMenuPriorizacao);
					MetodosSiare.validarTexto("Manutenção de Priorização de Serviços",
							ObjetosMetodosComuns.textoValidarTituloHome);
					MetodosSiare.inserirDadoNoCampo(dadoDoRetornoDoArquivo, priorizarCampoProtocolo);
					MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisar);
					MetodosSiare.validarTexto("Manutenção de Priorização de Serviços",
							ObjetosMetodosComuns.textoValidarTituloHome);
					MetodosSiare.umClique(ObjetosMetodosComuns.selecionarProtocoloPesquisado);
					MetodosSiare.umClique(ObjetosMetodosComuns.linkPriorizar);
					MetodosSiare.validarTexto("Priorizar Serviços", ObjetosMetodosComuns.textoValidarTituloHome);
					MetodosSiare.umClique(ObjetosMetodosComuns.comandoPriorizar);
					MetodosSiare.aguardarOProximoPasso(3000);
					MetodosSiare.validarTexto("Solicitação efetuada com sucesso.",
							ObjetosMetodosComuns.mensagemDeSucesso);
					MetodosSiare.umClique(ObjetosMetodosComuns.linkSairSiareSICAF);
					logarComAdministrador();
					escreverEmArquivoTextoCasoNaoEncontradoValorNoObjeto(objetoCopiar, subPastaProjeto, nomeDoArquivo,
							elementoPesquisar, consultaCampoProtocolo, priorizarCampoProtocolo);

				}
			} while (!achou);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Método para logar com o Administrador Siare e ir até o Consulta de
	 * Histório de Protocolo
	 * 
	 * @author Antonio Benardo
	 * @throws IOException
	 */
	public static void logarComAdministrador() throws IOException {
		// Logar com o administrador para consulatar o Protocolo
		ObjetosTelaLoginSicaf.tituloPaginaLoginCorreto(
				"SEF/MG - Rodovia Papa João Paulo II, nº 4001. Edifício Gerais.  7º andar.  Bairro Serra Verde - "
						+ "Belo Horizonte-MG. CEP 31.630-901");
		ObjetosTelaLoginSicaf.performSearchCpf("88888888888");
		ObjetosTelaLoginSicaf.performSearchSenha("12345678");
		ObjetosTelaLoginSicaf.clickSearchButtonLogin();
		
	}
	/**
	 * Método para acessar a tela Consulta Histórico de Protocolo
	 * Histório de Protocolo
	 * @author Antonio Benardo
	 * @throws IOException
	 */
	
	public static void acessarMenuconsultaDeHistoriocoProtocolo()throws IOException{
		// Acessar a tela de consluta de protocolo
		MetodosSiare.umClique(ObjetosMetodosComuns.abaConultaSiareSICAF);
		MetodosSiare.validarTexto("Principais Consultas", ObjetosMetodosComuns.textoTituloDaAbaConsulta);
		MetodosSiare.doisCliques(ObjetosMetodosComuns.menuAdiministracaoDeServico,
				ObjetosMetodosComuns.subMenuHistoricoDeServicoPorProtocolo);
		MetodosSiare.validarTexto("Consultar Histórico de Serviço por Protocolo",
				ObjetosMetodosComuns.textoTituloDaAbaConsulta);
	}

	/**
	 * Método para preenhcer o Check da tela de Consulta de Histório de
	 * Protocolo
	 * 
	 * @author Antonio Bernardo
	 */
	public static void metodoCheckNosCampos() {
		// Marcar os campos com Checkbox da tela de Consulta de protocolo e
		// realizar a pesquisar
		MetodosSiare.umClique(ObjetosMetodosComuns.checkOcorrencia);
		MetodosSiare.umClique(ObjetosMetodosComuns.checkPriorizacao);
		MetodosSiare.umClique(ObjetosMetodosComuns.checkRedirecionamento);
		MetodosSiare.umClique(ObjetosMetodosComuns.checkRegistroEntrega);
		MetodosSiare.umClique(ObjetosMetodosComuns.checkSuspensao);
		MetodosSiare.umClique(ObjetosMetodosComuns.checkTramitacao);
		MetodosSiare.umClique(ObjetosMetodosComuns.checkTransferencia);
		MetodosSiare.doisCliques(ObjetosMetodosComuns.comboPendencia, ObjetosMetodosComuns.selecionarOpcaoTodas);
		MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisar);
	}

	/**
	 * Método para Consluta o responsável pelo o Protoclo Gerado na tela de
	 * Servidores na Aba Controle de Acesso Recupera o CPF do Responsável pela
	 * protocolo para logar no SIARE.
	 * 
	 * @author Antonio Bernardo
	 */

	public static void metodoConsultaOResponsavelPeloProtocolo() {
		// Pegar o numero do CPF para logar e executar o protorolo
		MetodosSiare.umClique(ObjetosMetodosComuns.linkControleDeAcesso);
		MetodosSiare.doisCliques(ObjetosMetodosComuns.menuCadastroControleDeAcesso,
				ObjetosMetodosComuns.subMenuServidorControleDeAcesso);
	}

	/**
	 * Método para Pesquisar o Responsável depois do preenchimento do CPF
	 * realizado pelo outro Método de Ler arquivo.
	 * 
	 * @author Antonio Bernardo
	 */
	public static void metodoParaRecuperarOResponsavelPeloProcolo() {
		// Criar o arquivo para guardar o CPF do Responsável pelo responsável
		MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisaServidores);

	}

	/**
	 * Método para Criar o arquivo colocar a informação dentro do arquivo
	 * Arquivo.txt Por exemplo, copiar o número de um protocolo e colar em um
	 * arquivo texto para utilizá-lo posteriormente. (CTRL C + CTRL V)
	 * 
	 * @Author Antonio Bernardo e Fábio Heller Atualizado dia 25/08/2017 -
	 *         Antonio Bernardo
	 */

	public static void escreverEmArquivoTextoComFormatacao(By objetoCopiar, String subPastaProjeto,
			String nomeDoArquivo) {
		try {
			boolean success = (new File(diretorioPrincipal + subPastaProjeto)).mkdirs();
			if (!success) {
				// Falha no momento de criar o diretório
			}
			FileWriter canal = new FileWriter(
					new File(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo + ".txt"));
			PrintWriter escrever = new PrintWriter(canal);
			String guardaValor = null;
			guardaValor = driver.findElement(objetoCopiar).getText();
			escrever.println(guardaValor);
			escrever.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static WebDriverWait getWait1() {
		return wait;
	}

	/**
	 * Método para comparar os protocolo com formatção na caixa de serviço e
	 * suspendento até achar o protocolo gerado e clicar no comando executar.
	 * 
	 * @author Antonio Bernardo e Fábio Heller
	 * 
	 * @param subPastaDiretorio
	 * @param nomeDoArquivoComFormatacao
	 * @param checkCaixaDeServico
	 * @param linkExecutarProtocolo
	 * @param linkSuspenderProtocolo
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void compararProtocoloGeradoComACaixaDeServicoDoServidorResponsavel(String subPastaDiretorio,
			String nomeDoArquivoComFormatacao, By checkCaixaDeServico, By linkExecutarProtocolo,
			By linkSuspenderProtocolo) throws IOException, InterruptedException {
		String protocoloCaixaDeServicoGravadoEmArquivo = MetodosSiare
				.lerArquivoTextoERetornaDadoDaPrimeiraLinha(subPastaDiretorio + "\\", nomeDoArquivoComFormatacao),
				protocoloCaixaDeServico = driver.findElement(ObjetosMetodosComuns.protocoloCaixaDeServico).getText();
		boolean achou = false;
		MetodosSiare.lerArquivoTextoERetornaDadoDaPrimeiraLinha(subPastaDiretorio + "\\", nomeDoArquivoComFormatacao);
		do {
			if (protocoloCaixaDeServicoGravadoEmArquivo.equals(protocoloCaixaDeServico)) {
				// codificar a estrutura quando o protocolo for encontrado
				MetodosSiare.umClique(checkCaixaDeServico);
				MetodosSiare.umClique(linkExecutarProtocolo);
				achou = true;
			} else {
				// inserir o metodo que ira susoender o protocolo
				MetodosSiare.umClique(checkCaixaDeServico);
				MetodosSiare.umClique(linkSuspenderProtocolo);
				MetodosSiare.validarTexto("Suspender Serviço", ObjetosMetodosComuns.textoTituloDaAbaConsulta);
				MetodosSiare.doisCliques(ObjetosMetodosComuns.comboMotivoDaSuspensao,
						ObjetosMetodosComuns.selecionarOpcaoTipoDeSuspensao);
				MetodosSiare.umClique(ObjetosMetodosComuns.comandoConfirmarSuspensaoProtocolo);
				MetodosSiare.aguardarOProximoPasso(3000);
				protocoloCaixaDeServico = driver.findElement(ObjetosMetodosComuns.protocoloCaixaDeServico).getText();
			}
		} while (!achou);

	}

	/**
	 * Método para ler o Arquivo.txt que foi criado e inserido um valor Por
	 * exemplo, copiar o número de um protocolo que está em um arquivo txt e
	 * inserir no elemento que receberá a informação
	 * 
	 * @Author Antonio Bernardo
	 */
	public static String lerArquivoTextoERetornaDadoDaPrimeiraLinha(String subPastaProjeto, String nomeDoArquivo)
			throws IOException {
		BufferedReader ler = new BufferedReader(
				new FileReader(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo + ".txt"));
		String linha = null;
		try {
			linha = ler.readLine();
			ler.close();

		} catch (IOException ex) {
			System.out.println("Erro: Não foi possivel ler arquivo!");
		}
		return linha;
	}
	
	/**
	 *  Método que que retorna o texto de um xpath de acordo com o parâmetro informado no método.
	 *  Utilizado na chamada dos métodos (solCaixadeServicosSolicitadosEncontrarProtocolo e sicafCaixaDeTarefasEncontrarProtocolo)
	 *  a chamada do .gettext no proprio método estava ocasionando erro, daí a necessidade de criar um método a parte.
	 * @Author Fábio Heller
	 */ 	
	public static String textoDoElemento(By campo){
		String textoDoElelemto = driver.findElement(campo).getText();
		return textoDoElelemto;
	}
	
	/**
	 *  Método que que retorna o primetiro inteiro de uma string de acordo com o parâmetro informado no método.
	 *  Utilizado na chamada dos métodos (solCaixadeServicosSolicitadosEncontrarProtocolo e sicafCaixaDeTarefasEncontrarProtocolo)
	 *  afim de obter a quantidade de regitros/protocolos informados na caixa de protocolo.
	 * @Author Fábio Heller
	 */ 	
	public static Integer obterQTDDeRegistrosNaCaixa(By campo){
		int qtdRegistros = 0;
		String meuteste = " ", registros = "", textoDoElelemto = driver.findElement(campo).getText();
		for(int i = 0; i< textoDoElelemto.length(); i++){
			if(meuteste.charAt(0) == textoDoElelemto.charAt(i))
				break;
			else
			registros = registros + textoDoElelemto.charAt(i);
		}
		qtdRegistros = Integer.parseInt(registros);
		return qtdRegistros;
	}
	 
	/**
	 *  Método que percorre a caixa de serviços afim de localizar o protocolo informado, quando encontrado 
	 *  o sistema irá selecionar o registro e acionar o botão infomado no parâmetro do método.
	 * @Author Fábio Heller
	 */ 
	 public static void solCaixadeServicosSolicitadosEncontrarProtocolo(String protocolo, By botaoAcao) throws InterruptedException{
		 boolean achou = false;
		 int contador = 0, iteracaoQtdDeRegistros = 0, qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]"));
	     String [][] arrayprotocolos = { 
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[2]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[2]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[3]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[3]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[4]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[4]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[5]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[5]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[6]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[6]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[7]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[7]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[8]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[8]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[9]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[9]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[10]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[10]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[11]/td[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[11]/td[1]/input"}
			};
		 do{
			 if(iteracaoQtdDeRegistros + 1 > qtdDeRegistros){
				 contador = 0;
				 iteracaoQtdDeRegistros = 0;
				 MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
				 qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]"));
				 MetodosSiare.inserirDadoNoCampo("1", MetodosSiare.campoXpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]/a[2]/input"));
				 MetodosSiare.umClique(MetodosSiare.campoXpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]/a[3]"));
				 MetodosSiare.aguardarOProximoPasso(5000);
			 }
			if(protocolo.equals(textoDoElemento(MetodosSiare.campoXpath(arrayprotocolos[contador][0])))){
				 achou = true;
				 MetodosSiare.umClique(MetodosSiare.campoXpath(arrayprotocolos[contador][1]));
				 MetodosSiare.umClique(botaoAcao);
			 }
			if(qtdDeRegistros > 10){
				 if(contador == 9){
					 contador = 0;
					 MetodosSiare.umClique(MetodosSiare.campoLinkText(">"));
					 iteracaoQtdDeRegistros++;
				 }else{
					 contador++;
					 iteracaoQtdDeRegistros++;
				 }
			}else{
				 if(contador+1 == qtdDeRegistros){
					 contador = 0;
					 iteracaoQtdDeRegistros = 0;
					 MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
					 qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]"));
				 }else{
					 contador++;
					 iteracaoQtdDeRegistros++;
				 }
			}
		 }while (!achou);
	 }
	 
	/**
	 *  Método que percorre a caixa de tarefas afim de localizar o protocolo informado, quando encontrado 
	 *  o sistema irá selecionar o registro e acionar o botão infomado no parâmetro do método.
	 * @Author Fábio Heller
	 */
	 public static void sicafCaixaDeTarefasEncontrarProtocolo(String protocolo, By botaoAcao) throws InterruptedException{
		 boolean achou = false;
		 int contador = 0, iteracaoQtdDeRegistros = 0, qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(MetodosSiare.campoXpath(".//*[@id='ufw_total_linhas']"));
		 String [][] arrayprotocolosAnalista = {  
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[4]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[4]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[5]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[5]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[6]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[6]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[7]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[7]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[8]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[8]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[9]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[9]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[10]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[10]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[11]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[11]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[12]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[12]/td[1]/input"},
					{".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[13]/td[2]/p[2]",
					 ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[13]/td[1]/input"}
			};
		 do{
			 if(iteracaoQtdDeRegistros + 1 > qtdDeRegistros){
				 contador = 0;
				 iteracaoQtdDeRegistros = 0;
				 MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
				 qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(MetodosSiare.campoXpath(".//*[@id='ufw_total_linhas']"));
				 MetodosSiare.inserirDadoNoCampo("1", MetodosSiare.campoXpath(".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody/tr/td/input"));
				 MetodosSiare.umClique(MetodosSiare.campoLinkText("Ir"));
				 MetodosSiare.aguardarOProximoPasso(5000);
			 }
			if(protocolo.equals(textoDoElemento(MetodosSiare.campoXpath(arrayprotocolosAnalista[contador][0])))){
				 achou = true;
				 MetodosSiare.umClique(MetodosSiare.campoXpath(arrayprotocolosAnalista[contador][1]));
				 MetodosSiare.umClique(botaoAcao);
			 }
			if(qtdDeRegistros > 10){
				 if(contador == 9){
					 contador = 0;
					 MetodosSiare.umClique(MetodosSiare.campoLinkText(">"));
					 iteracaoQtdDeRegistros++;
				 }else{
					 contador++;
					 iteracaoQtdDeRegistros++;
				 }
			}else{
				 if(contador+1 == qtdDeRegistros){
					 contador = 0;
					 iteracaoQtdDeRegistros = 0;
					 MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
					 qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(By.xpath(".//*[@id='ufw_total_linhas']"));
				 }else{
					 contador++;
					 iteracaoQtdDeRegistros++;
				 }
			}
		 }while (!achou);
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
	
	public static WebDriverWait getWait() {
		return wait;
	}
}

