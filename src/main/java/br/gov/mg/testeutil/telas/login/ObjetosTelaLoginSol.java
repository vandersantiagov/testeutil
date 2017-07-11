package br.gov.mg.testeutil.telas.login;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.gov.mg.testeutil.util.sol.SeleniumSol;
import br.gov.mg.testeutil.util.sol.Utils;

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
	static By validacaoTituloCorretoLogin = By.id("boxFooter");
	
	static By selecionarTipoDeUsuario = By.name("cmbDominio");
	
	static By selecionarIE = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[2]");
	static By selecionarProtocolo = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[3]");
	static By selecionarCPF = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[4]");
	static By selecionarCNPJ = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[5]");
	static By selecionarProdutorRural= By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[6]");
	static By selecionarDespachanteCPF = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[7]");
	static By selecionarDespachanteCNPJ = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[8]");
	static By selecionarRecintoAlfandegadoPessoaJuridica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[9]");
	static By selecionarCERM_TFRMPessoaFisica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[10]");
	static By selecionarCERM_TFRMPessoaJuridica = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[11]");
	static By selecionarVAFEspecial = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[12]");
	static By selecionarContribuinteInterestadual = By.xpath(".//*[@id='containerPublicidade']/form/div[2]/p[2]/select/option[13]");
	
	static By preencherCampoCPF = By.name("login");
	
	static By preencherCampoIdentificacao = By.name("dominio");
	
	static By preencherSenhaAtual = By.name("senhaAtual");
	
	static By comandoConfirmarLoginInternet = By.name("Confirmar");
	
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
		Utils.isClickable(preencherCampoCPF);
		driver.findElement(preencherCampoCPF).clear();
		driver.findElement(preencherCampoCPF).sendKeys(searchTextCpf);
	}
	
	/**
	 * Digita a Identifica��o na tela de Login Internet
	 */
	public static void preencheCampoIdentificacao(String searchTextSenha) { 
		Utils.isClickable(preencherCampoIdentificacao);
		driver.findElement(preencherCampoIdentificacao).clear();
		driver.findElement(preencherCampoIdentificacao).sendKeys(searchTextSenha);
	}
	
	/**
	 * Digita a senha na tela de Login Internet
	 */
	public static void preencheCampoSenha(String searchTextSenha) { 
		Utils.isClickable(preencherSenhaAtual);
		driver.findElement(preencherSenhaAtual).clear();
		driver.findElement(preencherSenhaAtual).sendKeys(searchTextSenha);
	}
	
	/**
	 *
	 *Clicar no comando Login na tela inical do Internet 
	 *
	 */
	public static void clickComandoLogin() { 
		wait.until(ExpectedConditions.visibilityOfElementLocated(comandoConfirmarLoginInternet));
		driver.findElement(comandoConfirmarLoginInternet).click(); 
	}
}
