package br.gov.mg.testeutil.metodos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.PageSnapshot;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import br.gov.mg.testeutil.objetos.ObjetosMetodosComuns;
import br.gov.mg.testeutil.util.FileUtil;
import br.gov.mg.testeutil.util.naoSiare.PropertyNaoSiare;
import br.gov.mg.testeutil.util.naoSiare.SeleniumNaoSiare;
import br.gov.mg.testeutil.util.sicaf.PropertySicaf;
import br.gov.mg.testeutil.util.sicaf.SeleniumSicaf;
import br.gov.mg.testeutil.util.sol.PropertySol;
import br.gov.mg.testeutil.util.sol.SeleniumSol;
//import br.gov.mg.testeutil.util.sol.UtilsSol;
//import gov.sefmg.CopiarColar.objetos.sicaf.ObjetosConsultaProtocolo;
//import gov.sefmg.CopiarColar.objetos.sicaf.ObjetosCopiarEColarValores;

public class MetodosSiare {

	private static boolean killTasks = true;
	

	/*
	 ***************************** METODOS GLOBAIS PARA CHAMADA NAS CLASSES*****************************
	 **/

	/**
	 * Método para instanciar objetos HTML
	 * 
	 * @Author Fábio Heller
	 */
	public static By campoID(String nomeElementoID) {
		By cpoIDBy = By.id(nomeElementoID);
		return cpoIDBy;
	}

	public static By campoName(String nomeElementoName) {
		By cpoNameBy = By.name(nomeElementoName);
		return cpoNameBy;
	}

	public static By campoXpath(String nomeElementoXpath) {
		By cpoXpathBy = By.xpath(nomeElementoXpath);
		return cpoXpathBy;
	}

	public static By campoLinkText(String nomeElementoLinkText) {
		By cpoLinkTextBy = By.linkText(nomeElementoLinkText);
		return cpoLinkTextBy;
	}

	public static By campoCssSelector(String nomeElementocssSelector) {
		By cpoCssSelectorBy = By.linkText(nomeElementocssSelector);
		return cpoCssSelectorBy;
	}

	/**
	 * Método que valida a igualdade entre duas strings Objetivo: Verificar se
	 * duas frases são iguais.
	 * 
	 * @Author Fábio Heller
	 */
	public static boolean validarTexto(String texto, By campo) {
		boolean achou = false;
		if (driver.findElement(campo).getText().equals(texto))
			achou = true;
		else
			assertThat("Título Incorreto!!", driver.findElement(campo).getText(), is(texto));
		return achou;
	}

	/**
	 * Método que efetua a ação de um clique
	 * 
	 * @Author Fábio Heller
	 */
	public static void umClique(By ElementoOpcaoClick1) {
		By correctLocator = null;
		Actions action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClick1));
		correctLocator = ElementoOpcaoClick1;
		WebElement element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();
	}

	/**
	 * Método que efetua a ação de dois cliques
	 * 
	 * @Author Fábio Heller
	 */
	public static void doisCliques(By ElementoOpcaoClick1, By ElementoOpcaoClik2) {
		By correctLocator = null;
		Actions action = new Actions(driver);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClick1));
		correctLocator = ElementoOpcaoClick1;
		WebElement element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClik2));
		correctLocator = ElementoOpcaoClik2;
		element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();
	}

	/**
	 * Método que efetua a ação de três cliques
	 * 
	 * @Author Fábio Heller
	 */
	public static void tresCliques(By ElementoOpcaoClick1, By ElementoOpcaoClik2, By ElementoOpcaoClik3) {
		By correctLocator = null;
		Actions action = new Actions(driver);

		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClick1));
		correctLocator = ElementoOpcaoClick1;
		WebElement element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClik2));
		correctLocator = ElementoOpcaoClik2;
		element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClik3));
		correctLocator = ElementoOpcaoClik3;
		element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();
	}

	/**
	 * Método que efetua a ação de quatro cliques
	 * 
	 * @Author Fábio Heller
	 */
	public static void quatroCliques(By ElementoOpcaoClick1, By ElementoOpcaoClik2, By ElementoOpcaoClik3,
			By ElementoOpcaoClik4) {
		By correctLocator = null;
		Actions action = new Actions(driver);

		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClick1));
		correctLocator = ElementoOpcaoClick1;
		WebElement element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClik2));
		correctLocator = ElementoOpcaoClik2;
		element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClik3));
		correctLocator = ElementoOpcaoClik3;
		element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementoOpcaoClik4));
		correctLocator = ElementoOpcaoClik4;
		element = driver.findElement(correctLocator);
		action.moveToElement(element).build().perform();
		driver.findElement(correctLocator).click();
	}

	/**
	 * Método que insere um valor em um campo
	 * 
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
	 * 
	 * @Author Fábio Heller
	 */
	public static void validaJanelaPopUpDetalhamento(String NomePopup) {
		Set<String> janelas = driver.getWindowHandles();
		for (String janela : janelas) {
			driver.switchTo().window(janela);
			if (janela.equals(NomePopup)) {
				break;
			}
		}
	}

	/**
	 * Fechar janela/popup
	 * 
	 * @Author Fábio Heller
	 */
	public static void fecharDriverAposJanelaPopUpDetalhamento() {
		driver.close();
	}

	/**
	 * Comando de aceitar o alerta de um Javascript
	 * 
	 * @Author Antonio Bernardo
	 */
	@SuppressWarnings("unused")
	public static void aceitarCancelamentoJavaScriptSicaf(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSicaf runner;
		WebDriver driver = (WebDriver) SeleniumSicaf.getDriver();
		driver.switchTo().alert().accept();
	}

	/**
	 * Comando de cancelar o alerta de um Javascript
	 * 
	 * @Author Antonio Bernardo
	 */
	@SuppressWarnings("unused")
	public static void cancelarAlertaCancelamentoJavaScriptSicaf(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSicaf runner;
		WebDriver driver = (WebDriver) SeleniumSicaf.getDriver();
		driver.switchTo().alert().dismiss();
	}

	/**
	 * Comando de aceitar o alerta de um Javascript
	 * 
	 * @Author Antonio Bernardo
	 */
	@SuppressWarnings("unused")
	public static void aceitarCancelamentoJavaScriptSol(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSol runner;
		WebDriver driver = (WebDriver) SeleniumSol.getDriver();
		driver.switchTo().alert().accept();
	}

	/**
	 * Comando de cancelar o alerta de um Javascript
	 * 
	 * @Author Antonio Bernardo
	 */
	@SuppressWarnings("unused")
	public static void cancelarAlertaCancelamentoJavaScriptSol(String Alert) {
		wait.until(ExpectedConditions.alertIsPresent());
		SeleniumSol runner;
		WebDriver driver = (WebDriver) SeleniumSol.getDriver();
		driver.switchTo().alert().dismiss();
	}

	/**
	 * Validar a existência do campo na Interface
	 * 
	 * @Author Antonio Bernardo
	 */
	public static void validarCampoVisivelNaInterface(By objetoVisivel) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(objetoVisivel));
	}

	/**
	 * Método que clica em um item da lista de um Combobox
	 * 
	 * @throws InterruptedException
	 * @Author Fábio Heller
	 */
	public static void selecionarOpcaoEmCombobox(By combobox, By opcaoCombobox) throws InterruptedException {
		Actions action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(combobox));
		driver.findElement(combobox).click();
		WebElement element = driver.findElement(opcaoCombobox);
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
		element.click();
	}

	/**
	 * 
	 * Método para criar uma subpasta no diretório ScreencShot e capturar Print.
	 * 
	 * @param fileName
	 *            - Nome do arquivo
	 * @Athor Antonio Bernardo e Fábio Heller e Sandra Leodoro
	 */
	public static File capturaScreenDaTela(String subPastaProjeto, String fileName) {
		boolean existeScrollHorizontalNaPagina = existeScrollHorizontalNaPagina();
		boolean existeScrollVerticalNaPagina = existeScrollVerticalNaPagina();
		if (!existeScrollHorizontalNaPagina && !existeScrollVerticalNaPagina) {
			return capturaScreenDaTelaIgnoreScrool(subPastaProjeto, fileName);
		} else {
			try {
				capturaScreenDaTelaTrataScroll(subPastaProjeto, fileName, existeScrollHorizontalNaPagina,
						existeScrollVerticalNaPagina);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		ObjetosMetodosComuns.contadorTelas++;
		return null;
	}

	private static void capturaScreenDaTelaTrataScroll(String subPastaProjeto, String fileName,
			boolean existeScrollHorizontalNaPagina, boolean existeScrollVerticalNaPagina) throws AWTException {
		ScrollStrategy direcaoRolarPagina = ScrollStrategy.BOTH_DIRECTIONS;
		boolean navegarNaVertical = existeScrollHorizontalNaPagina && !existeScrollVerticalNaPagina;
		boolean navegarNaHorizontal = !existeScrollHorizontalNaPagina && existeScrollVerticalNaPagina;
		if (navegarNaVertical) {
			direcaoRolarPagina = ScrollStrategy.VERTICALLY;
		}

		if (navegarNaHorizontal) {
			direcaoRolarPagina = ScrollStrategy.HORIZONTALLY;
		}

		PageSnapshot shootPage = Shutterbug.shootPage(driver, direcaoRolarPagina);
		shootPage.withName(fileName);
		shootPage.save(diretorioPrincipal + subPastaProjeto);

		if (navegarNaVertical) {
			pageUp();
		}
	}

	public static boolean existeScrollHorizontalNaPagina() {
		JavascriptExecutor javascript = (JavascriptExecutor) driver;

		Boolean existe = (Boolean) javascript
				.executeScript("return document.documentElement.scrollWidth>document.documentElement.clientWidth;");

		return existe;
	}

	public static boolean existeScrollVerticalNaPagina() {
		JavascriptExecutor javascript = (JavascriptExecutor) driver;

		Boolean existe = (Boolean) javascript
				.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");

		return existe;
	}

	/**
	 * 
	 * Método para criar uma subpasta no diretório ScreencShot e capturar Print.
	 * 
	 * @param fileName
	 *            - Nome do arquivo
	 * @Athor Antonio Bernardo e Fábio Heller
	 */
	public static File capturaScreenDaTelaIgnoreScrool(String subPastaProjeto, String fileName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			File destFile = new File(diretorioPrincipal + subPastaProjeto + "\\" + fileName + ".jpeg");
			FileUtils.copyFile(scrFile, destFile, true);
			return destFile;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Método para excluir os arquivo na Subpasta do diretório onde estão sendo
	 * gerados os prints. * @Author Antonio Bernardo e Fábio Heller
	 */
	public static void deletarArquivosDaSubpasta(String subPastaProjeto) {
		try {
			String enderecoCriacaoPasta = diretorioPrincipal + subPastaProjeto + "\\";
			File pasta = FileUtil.createPastaCaseNotExists(enderecoCriacaoPasta);
			File[] arquivos = pasta.listFiles();
			for (File arquivo : arquivos) {
				if (arquivo.getName().endsWith("jpeg") || arquivo.getName().endsWith("sql")
						|| arquivo.getName().endsWith("html") || arquivo.getName().endsWith("out")
						|| arquivo.getName().endsWith("txt") || arquivo.getName().endsWith("pdf")
						|| arquivo.getName().endsWith("png")) {
					arquivo.delete();
				}
			}
		} catch (NullPointerException ex) {
			System.out.println("Diretorio e/ou subpastas inexistentes!");
		} catch (Exception ex) {

		}
	}

	@SuppressWarnings("unused")
	private static PrintWriter getPrintWriter(String subPastaProjeto, String nomeDoArquivo){
		//TODO: (Fabio) ajustar metodo para correção via sonar referente ao metodo  escreverEmArquivoTexto
		String string = "\\"; 
		try (FileWriter canal = new FileWriter(
				new File(diretorioPrincipal + subPastaProjeto + string + nomeDoArquivo + ".txt"));
				PrintWriter escrever = new PrintWriter(canal)){
			
			return escrever;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Método para Criar o arquivo colocar a informação dentro do arquivo
	 * Arquivo.txt Por exemplo, copiar o número de um protocolo e colar em um
	 * arquivo texto para utilizá-lo posteriormente. (CTRL C + CTRL V)
	 * 
	 * @Author Antonio Bernardo e Fábio Heller Atualizado dia 25/08/2017 -
	 *         Antonio Bernardo
	 */

	public static void escreverEmArquivoTexto(By objetoCopiar, String subPastaProjeto, String nomeDoArquivo) {
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
			String str = guardaValor;
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Método para ler o Arquivo.txt que foi criado e inserido um valor Por
	 * exemplo, copiar o número de um protocolo que está em um arquivo txt e
	 * inserir no elemento que receberá a informação
	 * 
	 * @Author Antonio Bernardo
	 */
	public static void lerArquivoTexto(String subPastaProjeto, String nomeDoArquivo, By elementoRecebedorValor)
			throws IOException {
		@SuppressWarnings("unused")
		String conteudo = "";
		try {
			String string = "\\";
			String string2 = ".txt";
			BufferedReader ler = new BufferedReader(
					new FileReader(diretorioPrincipal + subPastaProjeto + string + nomeDoArquivo + string2));
			String linha = ler.readLine();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementoRecebedorValor));
			driver.findElement(elementoRecebedorValor).clear();
			driver.findElement(elementoRecebedorValor).sendKeys(linha);

			try {
				linha = ler.readLine();
				while (linha != null) {
					conteudo += linha + "\r\n";
					linha = ler.readLine();
				}
				ler.close();

			} catch (IOException ex) {
				System.out.println("Erro: Não foi possivel ler arquivo!");
			}
		} catch (FileNotFoundException ex) {

		}
		finally {
			
		}
	}

	/**
	 * Método para criar um arquivo .pdf e inserido um valor
	 * 
	 * @Athor Jacqueline Lucas
	 */
	public static void criarArquivoPDFEInserirTexto(String subPastaProjeto, String nomeDoArquivo, String inserirTexto) {
		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
					new File(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo + ".pdf")));
			document.open();
			document.add(new Paragraph(inserirTexto));
			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para anexar um arquivo
	 * 
	 * @Athor Jacqueline Lucas e Antonio Bernardo
	 */
	public static void comandoAnexarArquivo(By nomeElemento, String subPastaProjeto, String nomeDoArquivo) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(nomeElemento));
		WebElement file_input = driver.findElement(nomeElemento);
		file_input.sendKeys(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo + ".pdf");
	}

	/**
	 * Método para acessar o Menu e o Submenu1.
	 * 
	 * @Athor Jacqueline Lucas
	 */
	public static void menuSubMenuNivel1(By Menu, By subMenu1) {
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
	 * 
	 * @Athor Jacqueline Lucas
	 */
	public static void menuSubMenuNivel2(By Menu, By subMenu1, By subMenu2) {
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
	 * 
	 * @Athor Jacqueline Lucas
	 */
	public static void menuSubMenuNivel3(By Menu, By subMenu1, By subMenu2, By subMenu3) {
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
	 * 
	 * @Athor Jacqueline Lucas
	 */
	public static void menuSubMenuNivel4(By Menu, By subMenu1, By subMenu2, By subMenu3, By subMenu4) {
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
	 * Método para clicar em um campo checkbox esse método irá setar o elemento
	 * da tela conforme o value seja (true,false,0,1,2,3...)
	 * 
	 * @Author Jacqueline Lucas
	 */
	public static void clickCampoCheckBox(By nomeCampo, String valueDoCampo) throws InterruptedException {
		final List<WebElement> rdBtn_Campo = driver.findElements((nomeCampo));
		int size = rdBtn_Campo.size();
		for (int i = 0; i < size; i++) {
			String sValue = rdBtn_Campo.get(i).getAttribute("value");
			if (sValue.equalsIgnoreCase(valueDoCampo)) {
				rdBtn_Campo.get(i).click();
			}
		}
	}

	/**
	 * Método para um tempo para o
	 * 
	 * @Author Antonio Bernardo
	 */
	public static void aguardarOProximoPasso(int valorEmMilisegundos) throws InterruptedException {
		Thread.sleep(valorEmMilisegundos);
	}

	/**
	 * Método para capturar a data atual do sistema e acrescentar ou diminuir em
	 * dias a data capturada set true com mascara ("dd/MM/yyyy") // set false
	 * sem mascara ("ddMMyyyy")
	 * 
	 * @Author Rogerio Cesar e Fabio Heller
	 */
	@SuppressWarnings("deprecation")
	public static String retornaDataAtualOuPosteriorOuAnteriorDaDataAtualDoSistema(int dias, boolean comMascara) {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		if (comMascara) {
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		} else {
			dateFormat = new SimpleDateFormat("ddMMyyyy");
		}
		Date data = new Date();
		data.setDate(data.getDate() + dias);
		return dateFormat.format(data);
	}

	/**
	 * Método para capturar a hora atual do sistema
	 * 
	 * @Author Fábio Heller
	 */
	public static String retornaHoraAtualDoSistema() {
		DateFormat dateFormat = new SimpleDateFormat("HHmmss");
		Date data = new Date();
		return dateFormat.format(data);
	}

	/**
	 * Método que efetua a ação de um clique em uma opção existente em Tela
	 * String texto = título da tela modal By campo = passar um elemento para
	 * validar o título da tela Modal By ElementoOpcaoClick1 = passar o elemento
	 * que vai receber a ação click int int tempo = tempo de espera do
	 * carregamento DOM
	 * 
	 * @Author Jacqueline Lucas
	 */
	public static void modalValidareClicar(String texto, By campo, By ElementoOpcaoClick1, int tempo)
			throws InterruptedException {
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
			int contador = 0;
			do {
				FileWriter canal = new FileWriter(
						new File(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo + ".txt"));
				PrintWriter escrever = new PrintWriter(canal);
				String guardaValor = null;
				int limiteDoContador = 15;
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
		MetodosSiare
				.validarTexto(
						"SEF/MG - Rodovia Papa João Paulo II, nº 4001. Edifício Gerais.  7º andar.  Bairro Serra Verde - "
								+ "Belo Horizonte-MG. CEP 31.630-901",
						ObjetosMetodosComuns.validacaoTituloCorretoLogin);
		MetodosSiare.inserirDadoNoCampo("88888888888", ObjetosMetodosComuns.cpfField);
		MetodosSiare.inserirDadoNoCampo("12345678", ObjetosMetodosComuns.senhaField);
		MetodosSiare.umClique(ObjetosMetodosComuns.confirmarFieldLogin);

	}

	/**
	 * Método para acessar a tela Consulta Histórico de Protocolo Histório de
	 * Protocolo
	 * 
	 * @author Antonio Benardo
	 * @throws IOException
	 */

	public static void acessarMenuconsultaDeHistoriocoProtocolo() throws IOException {
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
	 * Método que que retorna o texto de um xpath de acordo com o parâmetro
	 * informado no método. Utilizado na chamada dos métodos
	 * (solCaixadeServicosSolicitadosEncontrarProtocolo e
	 * sicafCaixaDeTarefasEncontrarProtocolo) a chamada do .gettext no proprio
	 * método estava ocasionando erro, daí a necessidade de criar um método a
	 * parte.
	 * 
	 * @Author Fábio Heller
	 */
	public static String textoDoElemento(By campo) {
		String textoDoElelemto = driver.findElement(campo).getText();
		return textoDoElelemto;
	}

	/**
	 * Método que que retorna o primetiro inteiro de uma string de acordo com o
	 * parâmetro informado no método. Utilizado na chamada dos métodos
	 * (solCaixadeServicosSolicitadosEncontrarProtocolo e
	 * sicafCaixaDeTarefasEncontrarProtocolo) afim de obter a quantidade de
	 * regitros/protocolos informados na caixa de protocolo.
	 * 
	 * @Author Fábio Heller
	 */
	public static Integer obterQTDDeRegistrosNaCaixa(By campo) {
		int qtdRegistros = 0;
		String meuteste = " ", registros = "", textoDoElelemto = driver.findElement(campo).getText();
		for (int i = 0; i < textoDoElelemto.length(); i++) {
			if (meuteste.charAt(0) == textoDoElelemto.charAt(i))
				break;
			else
				registros = registros + textoDoElelemto.charAt(i);
		}
		qtdRegistros = Integer.parseInt(registros);
		return qtdRegistros;
	}

	/**
	 * Método que percorre a caixa de serviços afim de localizar o protocolo
	 * informado, quando encontrado o sistema irá selecionar o registro e
	 * acionar o botão infomado no parâmetro do método.
	 * 
	 * @Author Fábio Heller
	 */
	public static void solCaixadeServicosSolicitadosEncontrarProtocolo(String protocolo, By botaoAcao)
			throws InterruptedException {
		boolean achou = false;
		int contador = 0, iteracaoQtdDeRegistros = 0, qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(
				By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]"));
		String[][] arrayprotocolos = {
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[2]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[2]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[3]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[3]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[4]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[4]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[5]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[5]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[6]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[6]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[7]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[7]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[8]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[8]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[9]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[9]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[10]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[10]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[11]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[4]/tbody/tr[11]/td[1]/input" } };
		do {
			if (iteracaoQtdDeRegistros + 1 > qtdDeRegistros) {
				contador = 0;
				iteracaoQtdDeRegistros = 0;
				MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
				qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(
						By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]"));
				MetodosSiare.inserirDadoNoCampo("1", MetodosSiare.campoXpath(
						".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]/a[2]/input"));
				MetodosSiare.umClique(MetodosSiare
						.campoXpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]/a[3]"));
				MetodosSiare.aguardarOProximoPasso(5000);
			}
			if (protocolo.equals(textoDoElemento(MetodosSiare.campoXpath(arrayprotocolos[contador][0])))) {
				achou = true;
				MetodosSiare.umClique(MetodosSiare.campoXpath(arrayprotocolos[contador][1]));
				MetodosSiare.umClique(botaoAcao);
			}
			if (qtdDeRegistros > 10 && achou == false) {
				if (contador == 9) {
					contador = 0;
					MetodosSiare.umClique(MetodosSiare.campoLinkText(">"));
					iteracaoQtdDeRegistros++;
				} else {
					contador++;
					iteracaoQtdDeRegistros++;
				}
			} else if (!achou) {
				if (contador + 1 == qtdDeRegistros) {
					contador = 0;
					iteracaoQtdDeRegistros = 0;
					MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
					qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(
							By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[3]/tbody/tr/td[3]"));
				} else {
					contador++;
					iteracaoQtdDeRegistros++;
				}
			}
		} while (!achou);
	}

	/**
	 * Método que percorre a caixa de tarefas afim de localizar o protocolo
	 * informado, quando encontrado o sistema irá selecionar o registro e
	 * acionar o botão infomado no parâmetro do método.
	 * 
	 * @Author Fábio Heller
	 */
	public static void sicafCaixaDeTarefasEncontrarProtocolo(String protocolo, By botaoAcao)
			throws InterruptedException {
		boolean achou = false;
		int contador = 0, iteracaoQtdDeRegistros = 0, qtdDeRegistros = MetodosSiare
				.obterQTDDeRegistrosNaCaixa(MetodosSiare.campoXpath(".//*[@id='ufw_total_linhas']"));
		String[][] arrayprotocolosAnalista = {
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[4]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[4]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[5]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[5]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[6]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[6]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[7]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[7]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[8]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[8]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[9]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[9]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[10]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[10]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[11]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[11]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[12]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[12]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[13]/td[2]/p[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[13]/td[1]/input" } };
		do {
			if (iteracaoQtdDeRegistros + 1 > qtdDeRegistros) {
				contador = 0;
				iteracaoQtdDeRegistros = 0;
				MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
				qtdDeRegistros = MetodosSiare
						.obterQTDDeRegistrosNaCaixa(MetodosSiare.campoXpath(".//*[@id='ufw_total_linhas']"));
				MetodosSiare.inserirDadoNoCampo("1", MetodosSiare.campoXpath(
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody/tr/td/input"));
				MetodosSiare.umClique(MetodosSiare.campoLinkText("Ir"));
				MetodosSiare.aguardarOProximoPasso(5000);
			}
			if (protocolo.equals(textoDoElemento(MetodosSiare.campoXpath(arrayprotocolosAnalista[contador][0])))) {
				achou = true;
				MetodosSiare.umClique(MetodosSiare.campoXpath(arrayprotocolosAnalista[contador][1]));
				MetodosSiare.umClique(botaoAcao);
			}
			if (qtdDeRegistros > 10 && achou == false) {
				if (contador == 9) {
					contador = 0;
					MetodosSiare.umClique(MetodosSiare.campoLinkText(">"));
					iteracaoQtdDeRegistros++;
				} else {
					contador++;
					iteracaoQtdDeRegistros++;
				}
			} else if (!achou) {
				if (contador + 1 == qtdDeRegistros) {
					contador = 0;
					iteracaoQtdDeRegistros = 0;
					MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
					qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(By.xpath(".//*[@id='ufw_total_linhas']"));
				} else {
					contador++;
					iteracaoQtdDeRegistros++;
				}
			}
		} while (!achou);
	}

	/**
	 * Método para ler a primeira linha do Arquivo.txt e retornar o registro
	 * como uma string
	 * 
	 * @Author Fábio Heller
	 */
	public static String retornaRegistroDaPrimeiraLinhaDeArquivoTexto(String subPastaProjeto, String nomeDoArquivo)
			throws IOException {
		String conteudo = null;
		try {
			BufferedReader ler = new BufferedReader(
					new FileReader(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo + ".txt"));
			conteudo = ler.readLine();
			ler.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Erro: Não foi possivel ler arquivo!");
		} catch (IOException ex) {
			System.out.println("Erro: Indefinido!");
		}
		return conteudo;
	}

	/**
	 * Método para verificar se o elemento está visível na tela retorna true -
	 * caso elemento visível retorna false - caso elemento não visível	 * 
	 * @Author Fábio Heller
	 */
	public static boolean verificaSeOElementoEstaVisivel(By elemento) {
		boolean visivel = false;
		try {
			Actions action = new Actions(driver);
			WebElement element = driver.findElement(elemento);
			action.moveToElement(element).build().perform();
			if (element.isDisplayed())
				visivel = true;
		} catch (Exception e) {
			//e.printStackTrace(); 
			visivel = false;
		}
		return visivel;
	}

	/**
	 * Método para verificar se o campo possui informação ou se está vazio
	 * (nulo) retorna true - caso elemento possui informação retorna false -
	 * caso elemento não possui informação
	 * 
	 * @Author Fábio Heller
	 */
	public static boolean verificaSeOElementoPossuiInformacao(By elemento) {
		boolean dados = false;
		try {
			Actions action = new Actions(driver);
			WebElement element = driver.findElement(elemento);
			action.moveToElement(element).build().perform();
			if (!driver.findElement(elemento).getText().equals(""))
				dados = true;
		} catch (Exception e) {
			dados = false;
		}
		return dados;
	}

	/**
	 * Método que aponta o driver para o popUp pelo frame do popUp Observações:
	 * o nome do elemento deve ser capturado pela tag "<iframe>" do popup
	 * 
	 * @Author Fábio Heller
	 */
	public static void localizaPopUpPeloFrame(By elemtentoFrame) {
		try {
			driver.switchTo().frame(driver.findElement(elemtentoFrame));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * Método que executa o fluxo para a Resolução de Pendência de um protocolo
     * - Pendências de Documentacao
     * 
      * @author jacqueline.lucas
     * @throws InterruptedException
     */
     public static void acessarMenuHomeAtendimentoEntregadeDocumentosResolucaodePendenciasPendenciasdeDocumentacao(
                  String subPastaDiretorio, String nomeDoArquivoProtocolo) throws IOException, InterruptedException {
           Set<String> janelas;
           String[] janela;
           boolean achou = false;
           MetodosSiare.umClique(ObjetosMetodosComuns.abaHomeSiareSICAF);
           MetodosSiare.doisCliques(ObjetosMetodosComuns.menuAtendimento,
                         ObjetosMetodosComuns.submenuEntregadeDocumentosResolucaodePendencias);
           MetodosSiare.validarTexto("Entrega de Documentos / Resolução de Pendências",
                         ObjetosMetodosComuns.textoTituloTelaEntregadeDocumentosResolucaodePendencias);
           MetodosSiare.inserirDadoNoCampo(
                         lerArquivoTextoERetornaDadoDaPrimeiraLinha(subPastaDiretorio, nomeDoArquivoProtocolo).replace(".", "")
                                      .replace("-", ""),
                         ObjetosMetodosComuns.campoProtocolo);
           MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisarPendencias);
                  MetodosSiare.umClique(ObjetosMetodosComuns.selecaoRegistro);
                  MetodosSiare.umClique(ObjetosMetodosComuns.linkResolvePendencia);
                  MetodosSiare.validarTexto("Pendências",
                                ObjetosMetodosComuns.textoTituloTelaEntregadeDocumentosResolucaodePendencias);
                  if (MetodosSiare.verificaSeOElementoPossuiInformacao(ObjetosMetodosComuns.campoDocumento)){
                                MetodosSiare.umClique(ObjetosMetodosComuns.selecaoRegistroPendenciasdeDocumentacao);
                                MetodosSiare.umClique(ObjetosMetodosComuns.comandoRegistrarEntrega);
                                MetodosSiare.validarTexto("Entrega de documentos pendentes",
                         ObjetosMetodosComuns.textoTituloTelaEntregadeDocumentosResolucaodePendencias);
                                MetodosSiare.inserirDadoNoCampo("Teste Pendência de Documentação", ObjetosMetodosComuns.campoObservacoes);
                                MetodosSiare.umClique(ObjetosMetodosComuns.comandoConfirmarEntregaDocumento);
                  }
           
                  janelas = driver.getWindowHandles();
                  janela = janelas.toArray(new String[0]);
                  do {
                         if (janela.length > 1) {
                                driver.switchTo().window(janela[janela.length - 1]).close();
                                janelas = driver.getWindowHandles();
                                janela = janelas.toArray(new String[0]);
                         } else {
                                MetodosSiare.aguardarOProximoPasso(200);
                                janelas = driver.getWindowHandles();
                                janela = janelas.toArray(new String[0]);
                         }
                         if (janela.length == 1) {
                                driver.switchTo().window(janela[0]);
                                achou = true;
                         }
                  } while (!achou);
           }


     /**
      * Método que executa o fluxo para a Resolução de Pendência de um protocolo
      * - Pendências de Esclarecimento
      * 
       * @author jacqueline.lucas
      */
      public static void acessarMenuHomeAtendimentoEntregadeDocumentosResolucaodePendenciasPendenciasdeEsclarecimento(
                   String subPastaDiretorio, String nomeDoArquivoProtocolo) throws IOException {
            MetodosSiare.umClique(ObjetosMetodosComuns.abaHomeSiareSICAF);
            MetodosSiare.menuSubMenuNivel1(ObjetosMetodosComuns.menuAtendimento,
                          ObjetosMetodosComuns.submenuEntregadeDocumentosResolucaodePendencias);
            MetodosSiare.validarTexto("Entrega de Documentos / Resolução de Pendências",
                          ObjetosMetodosComuns.textoTituloTelaEntregadeDocumentosResolucaodePendencias);
            MetodosSiare.inserirDadoNoCampo(
                          lerArquivoTextoERetornaDadoDaPrimeiraLinha(subPastaDiretorio, nomeDoArquivoProtocolo).replace(".", "")
                                       .replace("-", ""),
                          ObjetosMetodosComuns.campoProtocolo);
            MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisarPendencias);
            
            MetodosSiare.umClique(ObjetosMetodosComuns.selecaoRegistro);
            MetodosSiare.umClique(ObjetosMetodosComuns.linkResolvePendencia);
            MetodosSiare.validarTexto("Pendências",
                          ObjetosMetodosComuns.textoTituloTelaEntregadeDocumentosResolucaodePendencias);
            if (MetodosSiare.verificaSeOElementoPossuiInformacao(ObjetosMetodosComuns.campoDocumento)){
                   MetodosSiare.umClique(ObjetosMetodosComuns.checkProtocoloCaixaDeServico);
                   MetodosSiare.umClique(ObjetosMetodosComuns.comandoResolver);
                   MetodosSiare.validarTexto("Resolver Pendências de Esclarecimento", ObjetosMetodosComuns.textoTituloTelaEntregadeDocumentosResolucaodePendencias);
                   MetodosSiare.inserirDadoNoCampo("Teste Pendências de Esclarecimento", ObjetosMetodosComuns.campoRelato);
                   MetodosSiare.umClique(ObjetosMetodosComuns.comandoConfirmarSuspensaoProtocolo);
            }
      }


	/**
	 * Método que auxilia a execução do método presumirDAEPeloNumeroDoProtocolo
	 * 
	 * @author Fábio Heller
	 */
	public static void presuncaoDeQuitacaoDeDocumentoDeArrecadacao(String valorDoDAE,
			boolean efetuarLoginELogoffComAdministrador) {
		MetodosSiare.inserirDadoNoCampo(valorDoDAE, ObjetosMetodosComuns.campoValorDoDAE);
		MetodosSiare.umClique(ObjetosMetodosComuns.campoBanco);
		MetodosSiare.umClique(ObjetosMetodosComuns.campoSelecaoBanco);
		MetodosSiare.inserirDadoNoCampo("3782", ObjetosMetodosComuns.campoAgencia);
		MetodosSiare.inserirDadoNoCampo(MetodosSiare.retornaDataAtualOuPosteriorOuAnteriorDaDataAtualDoSistema(0, true),
				ObjetosMetodosComuns.campoDataPagamento);
		MetodosSiare.inserirDadoNoCampo("12345abcde", ObjetosMetodosComuns.campoNSU);
		MetodosSiare.umClique(ObjetosMetodosComuns.comandoConfirmarSuspensaoProtocolo);
		if (MetodosSiare.verificaSeOElementoEstaVisivel(ObjetosMetodosComuns.comandoConfirmarSuspensaoProtocolo))
			MetodosSiare.umClique(ObjetosMetodosComuns.comandoConfirmarSuspensaoProtocolo);
		if (efetuarLoginELogoffComAdministrador)
			MetodosSiare.umClique(ObjetosMetodosComuns.linkSairSiareSICAF);
	}

	/**
	 * Método que executa o fluxo de presunção do DAE pelo número do protocolo
	 * sem formatação Observação para o parâmetro boolean
	 * efetuarLoginELogoffComAdministrador: (true) - será efetuado login com
	 * administrador no SICAF o fluxo será executado e ao fim será feito logoff
	 * (false) - não será efetuado login e logoff, sendo pré-condição analista
	 * logado no SICAF com perfil apto para presumir DAE
	 * 
	 * @author Fábio Heller
	 * @throws ParseException
	 */
	public static void presumirDAEPeloNumeroDoProtocolo(String subPastaDiretorio, String nomeDoArquivoProtocolo,
			String dataInicial, boolean efetuarLoginELogoffComAdministrador) throws IOException, ParseException {
		String valorTotalDoDAE;
		if (efetuarLoginELogoffComAdministrador)
			MetodosSiare.logarComAdministrador();
		wait.until(ExpectedConditions.visibilityOfElementLocated(ObjetosMetodosComuns.menuDocumentodeArrecadacaoDAE));
		MetodosSiare.menuSubMenuNivel1(ObjetosMetodosComuns.menuDocumentodeArrecadacaoDAE,
				ObjetosMetodosComuns.subMenuManutencaoDAE);
		MetodosSiare.inserirDadoNoCampo(
				lerArquivoTextoERetornaDadoDaPrimeiraLinha(subPastaDiretorio, nomeDoArquivoProtocolo).replace(".", "")
						.replace("-", ""),
				ObjetosMetodosComuns.campoProtocolo);
		MetodosSiare.inserirDadoNoCampo(dataInicial, ObjetosMetodosComuns.campoPeriodoDeEmissaoInicial);
		MetodosSiare.inserirDadoNoCampo(MetodosSiare.acrescentarDiasEmUmaData(dataInicial, 180),
				ObjetosMetodosComuns.campoPeriodoDeEmissaoFinal);
		MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisar);
		valorTotalDoDAE = driver.findElement(ObjetosMetodosComuns.campoValorTotalDoDAE).getText();
		MetodosSiare.umClique(ObjetosMetodosComuns.checkProtocoloCaixaDeServico);
		MetodosSiare.umClique(ObjetosMetodosComuns.linkPresumirQuitacaoDAE);
		MetodosSiare.presuncaoDeQuitacaoDeDocumentoDeArrecadacao(valorTotalDoDAE, efetuarLoginELogoffComAdministrador);
	}

	/**
	 * Método que executa o fluxo de presunção do DAE pelo número do protocolo
	 * sem formatação Observação para o parâmetro boolean
	 * efetuarLoginELogoffComAdministrador: (true) - será efetuado login com
	 * administrador no SICAF o fluxo será executado e ao fim será feito logoff
	 * (false) - não será efetuado login e logoff, sendo pré-condição analista
	 * logado no SICAF com perfil apto para presumir DAE
	 * 
	 * @author Fábio Heller
	 */
	public static void presumirDAEPeloNumeroDoDAE(String subPastaDiretorio, String nomeDoArquivoDae, String dataInicial,
			boolean efetuarLoginELogoffComAdministrador) throws IOException, ParseException {
		String valorTotalDoDAE,
				numeroDoDAE = lerArquivoTextoERetornaDadoDaPrimeiraLinha(subPastaDiretorio, nomeDoArquivoDae)
						.replace("-", "");
		if (efetuarLoginELogoffComAdministrador)
			MetodosSiare.logarComAdministrador();
		MetodosSiare.doisCliques(ObjetosMetodosComuns.menuDocumentodeArrecadacaoDAE,
				ObjetosMetodosComuns.subMenuManutencaoDAE);
		if (numeroDoDAE.length() > 12)
			numeroDoDAE = numeroDoDAE.substring(numeroDoDAE.length() - 12, numeroDoDAE.length());
		MetodosSiare.inserirDadoNoCampo(numeroDoDAE, ObjetosMetodosComuns.campoNumeroDAE);
		MetodosSiare.inserirDadoNoCampo(dataInicial, ObjetosMetodosComuns.campoPeriodoDeEmissaoInicial);
		MetodosSiare.inserirDadoNoCampo(MetodosSiare.acrescentarDiasEmUmaData(dataInicial, 180),
				ObjetosMetodosComuns.campoPeriodoDeEmissaoFinal);
		MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisar);
		valorTotalDoDAE = driver.findElement(ObjetosMetodosComuns.campoValorTotalDoDAE).getText();
		MetodosSiare.umClique(ObjetosMetodosComuns.checkProtocoloCaixaDeServico);
		MetodosSiare.umClique(ObjetosMetodosComuns.linkPresumirQuitacaoDAE);
		MetodosSiare.presuncaoDeQuitacaoDeDocumentoDeArrecadacao(valorTotalDoDAE, efetuarLoginELogoffComAdministrador);
	}

	/**
	 * Método que acrescenta mais dias a partir de uma data informada
	 * 
	 * @author Fábio Heller
	 * @throws ParseException
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public static String acrescentarDiasEmUmaData(String dataInicial, int quantidadeDeDiasAcrescentar)
			throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateInicial = (Date) formatter.parse(dataInicial);
		dateInicial.parse(dataInicial);
		dateInicial.setDate(dateInicial.getDate() + 180);
		formatter.format(dateInicial);
		return String.valueOf(formatter.format(dateInicial));
	}

	/**
	 * Método que aponta o foco em uma janela especifica
	 * 
	 * @author Fábio Heller
	 */
	public static void focaEmPaginaEspecificaDoNavegador(String InstanciaDoDriverDaPagina) {
		driver.switchTo().window(InstanciaDoDriverDaPagina);
	}

	/**
	 * Método que retorna a instância do driver em foco
	 * 
	 * @author Fábio Heller
	 */
	public static String retornaInstanciaDoDriverDaPaginaAtual() {
		return driver.getWindowHandle();
	}

	/**
	 * PRIORIZAR PROTOCOLO MÉTODO priorizoarProtocolo
	 * 
	 * @author Antônio Bernardo
	 */
	public static void priorizoarProtocolo(String subPastaProjeto, String nomeDoArquivo)
			throws InterruptedException, IOException {
		MetodosSiare.umClique(ObjetosMetodosComuns.linkHomeSiare);
		MetodosSiare.validarTexto("Suas tarefas para o momento :", ObjetosMetodosComuns.textoValidarTituloHome);
		MetodosSiare.tresCliques(ObjetosMetodosComuns.menuAdiministracaoDeServico, ObjetosMetodosComuns.subMenuServico,
				ObjetosMetodosComuns.subMenuPriorizacao);
		MetodosSiare.validarTexto("Manutenção de Priorização de Serviços", ObjetosMetodosComuns.textoValidarTituloHome);
		MetodosSiare.lerArquivoTexto(subPastaProjeto, nomeDoArquivo, ObjetosMetodosComuns.campoProtocoloPriorizacao);
		MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisar);
		MetodosSiare.validarTexto("Manutenção de Priorização de Serviços", ObjetosMetodosComuns.textoValidarTituloHome);
		MetodosSiare.umClique(ObjetosMetodosComuns.selecionarProtocoloPesquisado);
		MetodosSiare.umClique(ObjetosMetodosComuns.linkPriorizar);
		MetodosSiare.validarTexto("Priorizar Serviços", ObjetosMetodosComuns.textoValidarTituloHome);
		MetodosSiare.umClique(ObjetosMetodosComuns.checkEnviarAnalistaResponsavelSim);
		MetodosSiare.umClique(ObjetosMetodosComuns.comandoPriorizar);
		MetodosSiare.aguardarOProximoPasso(3000);
		MetodosSiare.validarTexto("Solicitação efetuada com sucesso.", ObjetosMetodosComuns.mensagemDeSucesso);
	}

	/**
	 * Mover a janela como Page Down e Page Up
	 * 
	 * @author antonio.bernardo
	 * @throws AWTException
	 */
	public static void pageDown() throws AWTException {
		Robot robot = new Robot();
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public static void pageUp() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_UP);
	}

	/**
	 * Mover a janela com o Page Down e Page Up com a janela Maximizada.
	 * 
	 * @author antonio.bernardo
	 * @throws AWTException
	 */
	public static void pageDownJanelaMaximizada() throws AWTException {
		driver.manage().window().maximize();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	}

	public static void pageUpJanelaMaximizada() throws AWTException {
		driver.manage().window().maximize();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_UP);
	}

	/**
	 * Método que insere um valor em um campo
	 * 
	 * @author antonio.bernardo
	 */
	public static void limparDadoNoCampo(By nomeElemento) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(nomeElemento));
		driver.findElement(nomeElemento);
		driver.findElement(nomeElemento).clear();
	}

	/**
	 * Método para seleciona opção no campo Combobox Tipo String
	 * 
	 * @param ElementoOpcaoClick1
	 * @param textoaSerSelecionado
	 * @author antonio.bernardo
	 */
	public static void selecionarOpcaoCombobox(By ElementoOpcaoClick1, String textoaSerSelecionado) {
		new Select(driver.findElement(ElementoOpcaoClick1)).selectByVisibleText(textoaSerSelecionado);
	}

	/**
	 * Método utlizado para introdução manualmente do texto exibido pela imagem
	 * do captcha
	 * 
	 * @param elementoCapcha
	 * @param elementoPesquisar
	 * @throws InterruptedException
	 * @author Jacqueline.lucas e Fábio Heller
	 */
	public static void aguardarInsercaoDeTextoDaImagemCapcha(By elementoCapcha, By elementoPesquisar)
			throws InterruptedException {
		boolean processa = true, saida = false;
		int cont = 0;
		MetodosSiare.umClique(elementoCapcha);
		while (!saida) {
			if (processa) {
				MetodosSiare.aguardarOProximoPasso(10000);
				if (!MetodosSiare.driver.findElement(elementoCapcha).getAttribute("value").equals("")) {
					MetodosSiare.umClique(elementoPesquisar);
					cont++;
				}
				if (MetodosSiare.verificaSeOElementoEstaVisivel(elementoCapcha)) {
					MetodosSiare.umClique(elementoCapcha);
					if (MetodosSiare.verificaSeOElementoEstaVisivel(ObjetosMetodosComuns.menssagemCapcha)) {
						if (!MetodosSiare.verificaSeOElementoPossuiInformacao(ObjetosMetodosComuns.menssagemCapcha)
								&& cont > 0)
							saida = true;
					}
					if (MetodosSiare.verificaSeOElementoEstaVisivel(ObjetosMetodosComuns.excecaoMenssage)) {
						if (!MetodosSiare.verificaSeOElementoPossuiInformacao(ObjetosMetodosComuns.excecaoMenssage)
								&& cont > 0)
							saida = true;
					}
				} else {
					processa = false;
					saida = true;
				}
			}
		}
	}

	/**
	 * Método para Leitura de um valor, incrementar um outro valor e atualizar o
	 * arquivo com o valor incrementado. OBS.: Para esse método precisa criar um
	 * subdiretório dentro da pastas do projeto no Z: e ter os aqruivos de
	 * parametrização criados dentro deste novo subdiretório.
	 * 
	 * @param subPastaProjeto
	 * @param nomeDoArquivo
	 * @param valorIncrementado
	 * @return
	 * @throws IOException
	 * @author antonio.bernardo Data 19-10-2017
	 */
	public static String lerArquivoTextoEIncrementarValorSalvarEmArquivo(String subPastaProjeto, String nomeDoArquivo,
			String valorIncrementado, String nomeDoArquivo1) throws IOException {
		int valor, contador;
		String resultado, conteudo = "";
		try {
			// Método para Ler o arquivo texto e incrementar o valor passado
			// como o paramentro.
			BufferedReader ler = new BufferedReader(
					new FileReader(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo + ".txt"));
			String linha = ler.readLine();
			valor = Integer.parseInt(linha);
			contador = Integer.parseInt(valorIncrementado);
			resultado = Integer.toString(valor + contador);

			// Método para escrever em arquivo do valor já incrementado.
			FileWriter canal = new FileWriter(
					new File(diretorioPrincipal + subPastaProjeto + "\\" + nomeDoArquivo1 + ".txt"));
			PrintWriter escrever = new PrintWriter(canal);
			String guardaValor = null;
			guardaValor = resultado;
			escrever.println(guardaValor);
			escrever.close();
			try {
				linha = ler.readLine();
				while (linha != null) {
					conteudo += linha + "\r\n";
					linha = ler.readLine();
				}
				ler.close();

			} catch (IOException ex) {
				System.out.println("Erro: Não foi possivel ler arquivo!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (FileNotFoundException ex) {

		}
		return conteudo;
	}

	/**
	 * Método que percorre a caixa de relatórios afim de localizar o relatório
	 * informado, quando encontrado o sistema irá selecionar o registro e
	 * acionar o botão infomado no parâmetro do método.
	 * 
	 * @author Fábio Heller
	 */
	public static void sicafCaixaDeRelatoriosSolicitados(String protocolo, By botaoAcao) throws InterruptedException {
		boolean achou = false;
		int contador = 0, iteracaoQtdDeRegistros = 0, qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(
				MetodosSiare.campoXpath(".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[1]/td[2]"));
		String[][] arrayprotocolosAnalista = {
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[3]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[3]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[4]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[4]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[5]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[5]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[6]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[6]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[7]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[7]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[8]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[8]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[9]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[9]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[10]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[10]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[11]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[11]/td[1]/input" },
				{ ".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[12]/td[2]",
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[12]/td[1]/input" } };
		do {
			if (iteracaoQtdDeRegistros + 1 > qtdDeRegistros) {
				contador = 0;
				iteracaoQtdDeRegistros = 0;
				MetodosSiare.umClique(MetodosSiare.campoLinkText("Relatório"));
				qtdDeRegistros = MetodosSiare
						.obterQTDDeRegistrosNaCaixa(MetodosSiare.campoXpath(".//*[@id='ufw_total_linhas']"));
				MetodosSiare.inserirDadoNoCampo("1", MetodosSiare.campoXpath(
						".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody/tr/td/input"));
				MetodosSiare.umClique(MetodosSiare.campoLinkText("Ir"));
				MetodosSiare.aguardarOProximoPasso(5000);
			}
			if (protocolo.equals(textoDoElemento(MetodosSiare.campoXpath(arrayprotocolosAnalista[contador][0])))) {
				achou = true;
				MetodosSiare.umClique(MetodosSiare.campoXpath(arrayprotocolosAnalista[contador][1]));
				MetodosSiare.umClique(botaoAcao);
			}
			if (qtdDeRegistros > 10 && achou == false) {
				if (contador == 9) {
					contador = 0;
					MetodosSiare.umClique(MetodosSiare.campoLinkText(">"));
					iteracaoQtdDeRegistros++;
				} else {
					contador++;
					iteracaoQtdDeRegistros++;
				}
			} else if (!achou) {
				if (contador + 1 == qtdDeRegistros) {
					contador = 0;
					iteracaoQtdDeRegistros = 0;
					MetodosSiare.umClique(MetodosSiare.campoLinkText("Relatório"));
					qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(By.xpath(".//*[@id='ufw_total_linhas']"));
				} else {
					contador++;
					iteracaoQtdDeRegistros++;
				}
			}
		} while (!achou);
	}

	/**
	 * Método que irá setar o atributo 'value' do elemento com um dado por meio
	 * do javaScript Para utilizar o método inserir Dado Pelo JavaScript é
	 * imprescindível que seja informado o id do campo, se for informado um
	 * valor divergente do id do campo, o JavascriptExecutor não vai encontrar o
	 * campo e consequentemente não fará a inserção do dado
	 * 
	 * @param idCampo
	 * @param valueDoCampo
	 * @author Sandra Leodoro
	 */
	public static void inserirDadoNoCampoPeloValueDoElementoComJavaScript(By idCampo, String valueDoCampo) {
		String texto = idCampo.toString();
		String id = texto.replace(texto.substring(0, texto.indexOf(" ") + 1), "");
		String script = "document.getElementById('" + id + "').value='" + valueDoCampo + "'";
		JavascriptExecutor jse = (JavascriptExecutor) MetodosSiare.driver;
		jse.executeScript(script);

	}

	/**
	 * @Author Jacqueline Lucas, 24/11/2017 - String subPastaProjeto: informar a
	 *         subpasta do Projeto String nomeDoArquivoProtocolo: informar nome
	 *         do arquivo que foi salvo o protocolo a ser pesquisado. String
	 *         nomeDoArquivoResponsavel: informar nome do arquivo que foi salvo
	 *         o nome do responsável a ser pesquisado. boolean isExecutado =
	 *         false
	 */

	public static void consultarResponsavelOuPriorizarELogar(String subPastaProjeto, String nomeDoArquivoProtocolo,
		String nomeDoArquivoResponsavel, boolean isExecutado) throws IOException, InterruptedException {
		MetodosSiare.umClique(ObjetosMetodosComuns.abaHomeSiareSICAF);
		MetodosSiare.umClique(ObjetosMetodosComuns.menuAdiministracaoDeServico);
		// Verifica se o usuário tem perfil para acesso a opção Home >
		// Administração de Serviço > Serviço > Priorização
		if (MetodosSiare.verificaVisibilidadeVariosElementos(ObjetosMetodosComuns.subMenuServico)) {
			MetodosSiare.menuSubMenuNivel2(ObjetosMetodosComuns.menuAdiministracaoDeServico,
					ObjetosMetodosComuns.subMenuServico, ObjetosMetodosComuns.subMenuPriorizacao);
			MetodosSiare.validarTexto("Manutenção de Priorização de Serviços",
					ObjetosMetodosComuns.textoValidarTituloHome);
			MetodosSiare.lerArquivoTexto(subPastaProjeto, nomeDoArquivoProtocolo,
					ObjetosMetodosComuns.campoProtocoloPriorizacao);
			MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisar);
			MetodosSiare.validarTexto("Manutenção de Priorização de Serviços",
					ObjetosMetodosComuns.textoValidarTituloHome);
			boolean achou = false;
			int contador = 0;
			int limiteDoContador = 15;
			do {
				if (!MetodosSiare.verificaSeOElementoPossuiInformacao(ObjetosMetodosComuns.campoAnalistaResponsavel)) {
					// codificar a estrutura caso não seja encontrado o
					// responsavel pelo protocolo
					MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisar);
					Thread.sleep(20000);
					contador++;
					System.out.println("Aguardando o protocolo cair para um Servidor Responsável... " + contador);
				}
				if (contador == limiteDoContador) {
					if (!MetodosSiare
							.verificaSeOElementoPossuiInformacao(ObjetosMetodosComuns.campoAnalistaResponsavel)) {
						// metodo que ira priorizar o protocolo
						MetodosSiare.umClique(ObjetosMetodosComuns.selecionarProtocoloPesquisado);
						MetodosSiare.umClique(ObjetosMetodosComuns.linkPriorizar);
						MetodosSiare.validarTexto("Priorizar Serviços", ObjetosMetodosComuns.textoValidarTituloHome);
						MetodosSiare.clickCampoCheckBox(ObjetosMetodosComuns.checkEnviarAnalistaResponsavelSim, "1");
						MetodosSiare.selecionarOpcaoEmCombobox(ObjetosMetodosComuns.campoAnalistaResp,
								ObjetosMetodosComuns.opcaoAnalistaResp);//
						MetodosSiare.umClique(ObjetosMetodosComuns.comandoPriorizar);
						// Trata exceção quando o protocolo é distribuido no
						// momento que solicita a Priorização.
						if (iniciaComTexto(ObjetosMetodosComuns.excecaoMenssage, "Solicitação efetuada com sucesso.")) {
							System.out.println("Solicitação efetuada com sucesso.");
						} else if (iniciaComTexto(ObjetosMetodosComuns.excecaoMenssage,
								"Não foi possível enviar o protocolo")) {
							System.out.println("Protocolo NÃO Priorizado.");
						}
						achou = true;					}
				}
				if (MetodosSiare.verificaSeOElementoPossuiInformacao(ObjetosMetodosComuns.campoAnalistaResponsavel)) {
					achou = true;
				}
			} while (!achou);
		} else {
			// Caso o ultimo usuário não tenha perfil para acessar a rotina de
			// priorizar, sai do SICAF e loga com usuário ADM
			MetodosSiare.umClique(ObjetosMetodosComuns.linkSairSiareSICAF);
			MetodosSiare.logarComAdministrador();
			consultarResponsavelOuPriorizarELogar(subPastaProjeto, nomeDoArquivoProtocolo, nomeDoArquivoResponsavel,
					isExecutado);
			isExecutado = true;
		}

		if (!isExecutado) {
			MetodosSiare.escreverEmArquivoTexto(ObjetosMetodosComuns.campoAnalistaResponsavel, subPastaProjeto,
					nomeDoArquivoResponsavel);
			System.out.println("Arquivo OK");
			// Pesquisa para achar o o CPF do responsável para logar através do
			// Nome
			MetodosSiare.umClique(ObjetosMetodosComuns.linkControleDeAcesso);
			// Verifica se o usuário tem perfil de acesso a opção Controle de
			// Acesso > Cadastros > Servidores
			if (MetodosSiare.verificaVisibilidadeVariosElementos(ObjetosMetodosComuns.menuCadastroControleDeAcesso,
					ObjetosMetodosComuns.subMenuServidorControleDeAcesso)) {
				MetodosSiare.doisCliques(ObjetosMetodosComuns.menuCadastroControleDeAcesso,
						ObjetosMetodosComuns.subMenuServidorControleDeAcesso);
				MetodosSiare.lerArquivoTexto(subPastaProjeto, nomeDoArquivoResponsavel,
						ObjetosMetodosComuns.campoNomeResponsavel);
				MetodosSiare.metodoParaRecuperarOResponsavelPeloProcolo();
				MetodosSiare.escreverEmArquivoTexto(ObjetosMetodosComuns.campoCPFResponsavelPeloProtocolo,
						subPastaProjeto, "CPFResponsavel");
			} else {
				MetodosSiare.umClique(ObjetosMetodosComuns.linkSairSiareSICAF);
				MetodosSiare.logarComAdministrador();
				MetodosSiare.metodoConsultaOResponsavelPeloProtocolo();
				MetodosSiare.lerArquivoTexto(subPastaProjeto, nomeDoArquivoResponsavel,
						ObjetosMetodosComuns.campoNomeResponsavel);
				MetodosSiare.metodoParaRecuperarOResponsavelPeloProcolo();
				MetodosSiare.escreverEmArquivoTexto(ObjetosMetodosComuns.campoCPFResponsavelPeloProtocolo,
						subPastaProjeto, "CPFResponsavel");
			}

			// Logar com o Responsável no qual o Protocolo caiu em sua caixa
			MetodosSiare.umClique(ObjetosMetodosComuns.linkSairSiareSICAF);
			MetodosSiare.lerArquivoTexto(subPastaProjeto, "CPFResponsavel", ObjetosMetodosComuns.cpfField);
			MetodosSiare.inserirDadoNoCampo("12345678", ObjetosMetodosComuns.senhaField);
			MetodosSiare.umClique(ObjetosMetodosComuns.confirmarFieldLogin);
		}

	}
	/**Desenvolvido por 
	 * @Author Jacqueline Lucas
	 */
	public static boolean verificaVisibilidadeVariosElementos(By... elementos) {
		Boolean visible = null;
		if (elementos != null) {
			for (By elemento : elementos) {
				boolean elementoEstaVisivel = MetodosSiare.verificaSeOElementoEstaVisivel(elemento);
				if (visible == null) {
					visible = elementoEstaVisivel;
				} else {
					visible &= elementoEstaVisivel;
				}

				if (!visible) {
					return visible;
				}
			}
		} else {
			visible = false;
		}
		return visible;
	}

	/**
	 * 
	 * Método para verificar um trecho de um texto
	 * 
	 * @Author Sandra
	 */

	public static boolean iniciaComTexto(By elemento, String texto) {
		String textoElemento = MetodosSiare.driver.findElement(elemento).getText();
		return StringUtils.startsWithIgnoreCase(textoElemento, texto);
	}

	/**
	 * @Author Jacqueline Lucas,
	 */
	public static boolean iniciaComTextoIgnoreCase(By elemento, String texto) {
		String textoElemento = MetodosSiare.driver.findElement(elemento).getText();
		return StringUtils.containsIgnoreCase(textoElemento, texto);
	}

	/**
	 * @Author Jacqueline Lucas, 08/01/2018 - Método para acessar Internet com
	 *         dados do Contribuinte e na Caixa de Ciência, localizar o
	 *         protocolo, ler a mensagem, para o RE se tornar Vigente.
	 * 
	 *         String subPastaProjeto: informar a subpasta do Projeto String
	 *         nomeDoArquivoProtocolo: informar nome do arquivo que foi salvo o
	 *         protocolo a ser pesquisado. String assunto: informar o Assunto a
	 *         ser filtrado.
	 **/
	public static void solCaixadeMensagensPTAVigente(String subPastaProjeto, String nomeDoArquivoProtocolo,
			String assunto) throws InterruptedException, IOException {

		MetodosSiare.umClique(ObjetosMetodosComuns.botaoCaixaMensagem);
		MetodosSiare.inserirDadoNoCampo(assunto, ObjetosMetodosComuns.campoPesquisarAssunto);
		MetodosSiare.umClique(ObjetosMetodosComuns.comandoPesquisar);

		boolean achou = false;
		String protocolo = MetodosSiare.retornaRegistroDaPrimeiraLinhaDeArquivoTexto(subPastaProjeto,
				nomeDoArquivoProtocolo);
		int contador = 0, iteracaoQtdDeRegistros = 0, qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(
				By.xpath("/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/table/tbody/tr/td[3]"));
		String[][] arrayprotocolos = {
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[1]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[2]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[3]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[4]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[5]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[6]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[7]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[8]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[9]/td[4]" },
				{ "/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/div/div/table[1]/tbody/tr[10]/td[4]" } };
		do {
			if (iteracaoQtdDeRegistros + 1 > qtdDeRegistros) {
				contador = 0;
				iteracaoQtdDeRegistros = 0;
				MetodosSiare.umClique(ObjetosMetodosComuns.botaoCaixaMensagem);
				qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(
						By.xpath("/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/table/tbody/tr/td[3]"));
				MetodosSiare.inserirDadoNoCampo("1", ObjetosMetodosComuns.campoPaginacao);
				MetodosSiare.umClique(ObjetosMetodosComuns.botaoIr);
				MetodosSiare.aguardarOProximoPasso(3000);
			}

			MetodosSiare.umClique(MetodosSiare.campoXpath(arrayprotocolos[contador][0]));
			if (iniciaComTextoIgnoreCase(ObjetosMetodosComuns.campoMensage, protocolo)) {
				achou = true;
				System.out.println("Localizou Protocolo");
			}
			if (qtdDeRegistros > 10 && achou == false) {
				if (contador == 9) {
					contador = 0;
					MetodosSiare.umClique(MetodosSiare.campoLinkText(">"));
					iteracaoQtdDeRegistros++;
				} else {
					contador++;
					iteracaoQtdDeRegistros++;
				}
			} else if (!achou) {
				if (contador + 1 == qtdDeRegistros) {
					contador = 0;
					iteracaoQtdDeRegistros = 0;
					MetodosSiare.umClique(MetodosSiare.campoLinkText("Home"));
					qtdDeRegistros = MetodosSiare.obterQTDDeRegistrosNaCaixa(
							By.xpath("/html/body/div[3]/div[2]/div/div[3]/div/form/div[1]/table/tbody/tr/td[3]"));
				} else {
					contador++;
					iteracaoQtdDeRegistros++;
				}
			}
		} while (!achou);
	}

	/**
	 ***************************** METODOS DEFINIDOS E JA UTLIZADOS NO
	 * ARCHETYPE*****************************
	 */

	public static String diretorioPrincipal = new String("Z:\\ArtefatosWebdriver\\");
	public static String diretorioPrincipalNFCE = "Z:\\ArtefatosNFCe\\";

	/**
	 * Instância privada do WebDriver que virá da suite de teste Objetivo:
	 * Definir o objetivo que será utilizado. No caso, o WebDriver
	 * 
	 * @Author Fábio Heller
	 */
	public static WebDriver driver;
	public static WebDriverWait wait;

	/**
	 * Construtor que ira adicionar a instância do WebDriver nos amientes para
	 * utilização dos scripts métodos
	 * 
	 * @Author Fábio Heller
	 */
	public static void setAmbienteSol() {
		try {
			if (killTasks) {
				Runtime.getRuntime().exec("cmd /c TASKKILL /F /IM chromedriver_2_33.exe");
				killTasks = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver = SeleniumSol.getDriver();
		wait = new WebDriverWait(driver, 30);
		driver.navigate().to(PropertySol.SITE_ADDRESS);
		driver.manage().window().maximize();
		PropertySol.ambienteSOL = true;
	}

	public static void setAmbienteSicaf() {
		try {
			if (killTasks) {
				Runtime.getRuntime().exec("cmd /c TASKKILL /F /IM chromedriver_2_33.exe");
				killTasks = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver = SeleniumSicaf.getDriver();
		wait = new WebDriverWait(driver, 30);
		driver.navigate().to(PropertySicaf.SITE_ADDRESS);
		driver.manage().window().maximize();
		PropertySicaf.ambienteSICAF = true;
	}

	public static void setAmbienteNaoSiare() {
		try {
			if (killTasks) {
				Runtime.getRuntime().exec("cmd /c TASKKILL /F /IM chromedriver_2_33.exe");
				killTasks = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver = SeleniumNaoSiare.getDriver();
		wait = new WebDriverWait(driver, 30);
		driver.navigate().to(PropertyNaoSiare.SITE_ADDRESS);
		driver.manage().window().maximize();
		PropertyNaoSiare.ambienteNAOSIARE = true;
	}

	/**
	 * Construtor que irá fechar a instância que foi aberta na anotação
	 * BeforeClasse
	 * 
	 * @Author Antonio Bernardo
	 */
	public static void quitAmbiente() throws Exception {
		driver.quit();
	}

	public static WebDriverWait getWait() {
		return wait;
	}
}
