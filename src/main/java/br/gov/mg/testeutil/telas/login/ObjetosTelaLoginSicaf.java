package br.gov.mg.testeutil.telas.login;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import br.gov.mg.testeutil.metodos.MetodosSiare;
import br.gov.mg.testeutil.util.sicaf.UtilsSicaf;



public class ObjetosTelaLoginSicaf {
	

	/**
	 * Defini��o T�cnica dos locators utilizados na p�gina
	 * Tela de Login Intranet do SIARE
	 */	
	public static By validacaoTituloCorretoLogin = By.id("boxFooter");
	public static By cpfField = By.name("login");
	public static By senhaField = By.name("senhaAtual");
	public static By confirmarFieldLogin = By.name("Confirmar");
	
	public static By escreverArquivo = By.id("buscaRapida");
	
	/**
	 * Validação do  Titulo da tela de Login
	 */
	public static void tituloPaginaLoginCorreto(String expectedTitle){
		assertThat("Título incorreto!",  MetodosSiare.driver.findElement(validacaoTituloCorretoLogin).getText(), is(expectedTitle));
	}
	
	/**
	 * Digita CPF na tela de Login Intranet
	 */
	public static void performSearchCpf (String searchTextCpf) { 
		//Utils.isClickable(cpfField);
		MetodosSiare.wait.until(ExpectedConditions.visibilityOfElementLocated(cpfField));
		MetodosSiare.driver.findElement(cpfField).clear();
		MetodosSiare.driver.findElement(cpfField).sendKeys(searchTextCpf);
	}
	
	/**
	 * Digita a senha na tela de Login Intranet
	 */
	public static void performSearchSenha(String searchTextSenha) { 
		UtilsSicaf.isClickable(senhaField);
		MetodosSiare.driver.findElement(senhaField).clear();
		MetodosSiare.driver.findElement(senhaField).sendKeys(searchTextSenha);
	}
	
	/**
	 *
	 *Clicar no comando Login na tela inical do Intranet 
	 *
	 */
	public static void clickSearchButtonLogin() { 
		MetodosSiare.wait.until(ExpectedConditions.visibilityOfElementLocated(confirmarFieldLogin));
		MetodosSiare.driver.findElement(confirmarFieldLogin).click(); 
	}
}