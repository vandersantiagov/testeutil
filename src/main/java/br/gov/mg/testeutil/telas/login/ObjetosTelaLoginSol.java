package br.gov.mg.testeutil.telas.login;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.gov.mg.testeutil.metodos.MetodosSiare;

//import br.gov.mg.testeutil.util.Utils;

public class ObjetosTelaLoginSol {
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
		MetodosSiare.wait.until(ExpectedConditions.visibilityOfElementLocated(validacaoTituloCorretoLogin));
		assertThat("Texto Incorreto!",  MetodosSiare.driver.findElement(validacaoTituloCorretoLogin).getText(), is(expectedTitle));
	}
	
	/**
	 * @throws InterruptedException 
	 * Tela de Login Internet 
	 */
	public static void comboTipoIdentificacaoSujeitoPassivo() throws InterruptedException{
		MetodosSiare.driver.findElement(selecionarTipoDeUsuario).click();
		MetodosSiare.driver.findElement(selecionarIE).click(); // Inscri��o Estadual
//		MetodosSiare.driver.findElement(selecionarProtocolo).click(); // Protocolo
//		MetodosSiare.driver.findElement(selecionarCPF).click(); // CPF
//		MetodosSiare.driver.findElement(selecionarCNPJ).click(); // CNPJ
//		MetodosSiare.driver.findElement(selecionarProdutorRural).click(); // Produtor Rural
//		MetodosSiare.driver.findElement(selecionarDespachanteCPF).click(); // DESPACHANTE CPF
//		MetodosSiare.driver.findElement(selecionarDespachanteCNPJ).click(); // DESPACHANTE CNPJ
//		MetodosSiare.driver.findElement(selecionarRecintoAlfandegadoPessoaJuridica).click(); // CNPJ
//		MetodosSiare.driver.findElement(selecionarCERM_TFRMPessoaFisica).click(); // TFRM PF
//		MetodosSiare.driver.findElement(selecionarCERM_TFRMPessoaJuridica).click(); // TFRM CNPJ
//		MetodosSiare.driver.findElement(selecionarVAFEspecial).click(); // VAF IE
//		MetodosSiare.driver.findElement(selecionarContribuinteInterestadual).click(); // Contribuinte Interestadual
//		MetodosSiare.wait.equals(50000);
	}
	
	/**
	 * Digita CPF na tela de Login Internet
	 */
	public static void preencheCampoCpf(String searchTextCpf) { 
		//Utils.isClickable(preencherCampoCPF);
		MetodosSiare.wait.until(ExpectedConditions.visibilityOfElementLocated(preencherCampoCPF));
		MetodosSiare.driver.findElement(preencherCampoCPF).click();
		MetodosSiare.driver.findElement(preencherCampoCPF).clear();
		MetodosSiare.driver.findElement(preencherCampoCPF).sendKeys(searchTextCpf);
	}
	
	/**
	 * Digita a senha na tela de Login Internet
	 */
	public static void preencheCampoSenha(String searchTextSenha) { 
		//Utils.isClickable(preencherSenhaAtual);
		MetodosSiare.wait.until(ExpectedConditions.visibilityOfElementLocated(preencherSenhaAtual));
		MetodosSiare.driver.findElement(preencherSenhaAtual).click();
		MetodosSiare.driver.findElement(preencherSenhaAtual).clear();
		MetodosSiare.driver.findElement(preencherSenhaAtual).sendKeys(searchTextSenha);
	}
	
	/**
	 * Clica no comando Login Internet
	 */
	public static void clickComandoLogin () { 
		MetodosSiare.wait.until(ExpectedConditions.visibilityOfElementLocated(comandoConfirmarLoginInternet));
		MetodosSiare.driver.findElement(comandoConfirmarLoginInternet).click(); 
	}
	
	
	/**
	 *
	 *Clicar no comando Login na tela inical do Intranet 
	 *
	 */
	public static void clickSearchButtonLogin() { 
		MetodosSiare.wait.until(ExpectedConditions.visibilityOfElementLocated(comandoConfirmarLoginInternet));
		MetodosSiare.driver.findElement(comandoConfirmarLoginInternet).click(); 
	}

	/**
	 * Compo identificacao visivel
	 */
	public static void preencheCampoIdentificacao(String searchTextIdentificacao){ 
		MetodosSiare.wait.until(ExpectedConditions.visibilityOfElementLocated(preencherCampoIdentificacao));
		MetodosSiare.driver.findElement(preencherCampoIdentificacao).click();
		MetodosSiare.driver.findElement(preencherCampoIdentificacao).clear();
		MetodosSiare.driver.findElement(preencherCampoIdentificacao).sendKeys(searchTextIdentificacao);
		}
}
