package br.gov.mg.testeutil.telas.login;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.gov.mg.testeutil.util.sol.SeleniumSol;
//import br.gov.mg.testeutil.util.Utils;

public class ObjetosTelaLoginSol {
	/**
	 * Instancia privada do webDriver que vira da suite de teste
	 */
	private static final WebDriver driver;
	private static final WebDriverWait wait;
	
	/**
	 * Construtor que ira adicionar a instancia do WebDriver para utilizacao dos metodos
	 */
	static {
		driver = SeleniumSol.getDriver();
		wait = new WebDriverWait(driver, 10);
	}
	/**
	 * Defini��o T�cnica dos locators utilizados na p�gina
	 * Tela de Login Internet do SIARE
	 */	
	public static By validacaoTituloCorretoLogin = By.id("boxFooter");
	
	public static By selecionarTipoDeUsuario = By.name("cmbDominio");
	
	public static By selecionarIE = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[2]");
	public static By selecionarProtocolo = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[3]");
	public static By selecionarCPF = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[4]");
	public static By selecionarCNPJ = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[5]");
	public static By selecionarProdutorRural= By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[6]");
	public static By selecionarDespachanteCPF = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[7]");
	public static By selecionarDespachanteCNPJ = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[8]");
	public static By selecionarRecintoAlfandegadoPessoaJuridica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[9]");
	public static By selecionarCERM_TFRMPessoaFisica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[10]");
	public static By selecionarCERM_TFRMPessoaJuridica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[11]");
	public static By selecionarVAFEspecial = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[12]");
	public static By selecionarContribuinteInterestadual = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[13]");
	
	public static By preencherCampoCPF = By.name("login");
	
	public static By preencherCampoIdentificacao = By.name("dominio");//Exibido somente após selecionar o tipo de usuário
	
	public static By preencherSenhaAtual = By.name("senhaAtual");
	
	public static By comandoConfirmarLoginInternet = By.name("Confirmar");
	
	
	/**
	 * Valida��o do Titulo da tela de Login Internet
	 */
	public static void tituloPaginaLoginCorretoInternet(String expectedTitle){
		wait.until(ExpectedConditions.visibilityOfElementLocated(validacaoTituloCorretoLogin));
		assertThat("Texto Incorreto!",  driver.findElement(validacaoTituloCorretoLogin).getText(), is(expectedTitle));
	}
	
	/**
	 * @throws InterruptedException 
	 * Tela de Login Internet 
	 */
	public static void comboTipoIdentificacaoSujeitoPassivo() throws InterruptedException{
		driver.findElement(selecionarTipoDeUsuario).click();
		driver.findElement(selecionarIE).click(); // Inscri��o Estadual
//		driver.findElement(selecionarProtocolo).click(); // Protocolo
//		driver.findElement(selecionarCPF).click(); // CPF
//		driver.findElement(selecionarCNPJ).click(); // CNPJ
//		driver.findElement(selecionarProdutorRural).click(); // Produtor Rural
//		driver.findElement(selecionarDespachanteCPF).click(); // DESPACHANTE CPF
//		driver.findElement(selecionarDespachanteCNPJ).click(); // DESPACHANTE CNPJ
//		driver.findElement(selecionarRecintoAlfandegadoPessoaJuridica).click(); // CNPJ
//		driver.findElement(selecionarCERM_TFRMPessoaFisica).click(); // TFRM PF
//		driver.findElement(selecionarCERM_TFRMPessoaJuridica).click(); // TFRM CNPJ
//		driver.findElement(selecionarVAFEspecial).click(); // VAF IE
//		driver.findElement(selecionarContribuinteInterestadual).click(); // Contribuinte Interestadual
//		wait.equals(50000);
	}
	
	/**
	 * Digita CPF na tela de Login Internet
	 */
	public static void preencheCampoCpf(String searchTextCpf) { 
		//Utils.isClickable(preencherCampoCPF);
		wait.until(ExpectedConditions.visibilityOfElementLocated(preencherCampoCPF));
		driver.findElement(preencherCampoCPF).click();
		driver.findElement(preencherCampoCPF).clear();
		driver.findElement(preencherCampoCPF).sendKeys(searchTextCpf);
	}
	
	/**
	 * Digita a senha na tela de Login Internet
	 */
	public static void preencheCampoSenha(String searchTextSenha) { 
		//Utils.isClickable(preencherSenhaAtual);
		wait.until(ExpectedConditions.visibilityOfElementLocated(preencherSenhaAtual));
		driver.findElement(preencherSenhaAtual).click();
		driver.findElement(preencherSenhaAtual).clear();
		driver.findElement(preencherSenhaAtual).sendKeys(searchTextSenha);
	}
	
	/**
	 * Clica no comando Login Internet
	 */
	public static void clickComandoLogin () { 
		wait.until(ExpectedConditions.visibilityOfElementLocated(comandoConfirmarLoginInternet));
		driver.findElement(comandoConfirmarLoginInternet).click(); 
	}
	
	
	/**
	 *
	 *Clicar no comando Login na tela inical do Intranet 
	 *
	 */
	public static void clickSearchButtonLogin() { 
		wait.until(ExpectedConditions.visibilityOfElementLocated(comandoConfirmarLoginInternet));
		driver.findElement(comandoConfirmarLoginInternet).click(); 
	}

	/**
	 * Compo identificacao visivel
	 */
	public static void preencheCampoIdentificacao(String searchTextIdentificacao){ 
		wait.until(ExpectedConditions.visibilityOfElementLocated(preencherCampoIdentificacao));
		driver.findElement(preencherCampoIdentificacao).click();
		driver.findElement(preencherCampoIdentificacao).clear();
		driver.findElement(preencherCampoIdentificacao).sendKeys(searchTextIdentificacao);
		}
}
