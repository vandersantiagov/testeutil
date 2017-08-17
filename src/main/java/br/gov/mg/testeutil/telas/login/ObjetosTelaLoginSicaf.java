package br.gov.mg.testeutil.telas.login;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.gov.mg.testeutil.util.sicaf.SeleniumSicaf;
import br.gov.mg.testeutil.util.sicaf.UtilsSicaf;



public class ObjetosTelaLoginSicaf {
	
	/**
	 * Instancia privada do webDriver que vira da suite de teste
	 */
	private static final WebDriver driver;
	private static final WebDriverWait wait;
	
	/**
	 * Construtor que ira adicionar a instancia do WebDriver para utilizacao dos metodos
	 */
	static {
		driver = SeleniumSicaf.getDriver();
		wait = new WebDriverWait(driver, 10);
	}
	
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
		assertThat("Título incorreto!",  driver.findElement(validacaoTituloCorretoLogin).getText(), is(expectedTitle));
	}
	
	/**
	 * Digita CPF na tela de Login Intranet
	 */
	public static void performSearchCpf (String searchTextCpf) { 
		//Utils.isClickable(cpfField);
		wait.until(ExpectedConditions.visibilityOfElementLocated(cpfField));
		driver.findElement(cpfField).clear();
		driver.findElement(cpfField).sendKeys(searchTextCpf);
	}
	
	/**
	 * Digita a senha na tela de Login Intranet
	 */
	public static void performSearchSenha(String searchTextSenha) { 
		UtilsSicaf.isClickable(senhaField);
		driver.findElement(senhaField).clear();
		driver.findElement(senhaField).sendKeys(searchTextSenha);
	}
	
	/**
	 *
	 *Clicar no comando Login na tela inical do Intranet 
	 *
	 */
	public static void clickSearchButtonLogin() { 
		wait.until(ExpectedConditions.visibilityOfElementLocated(confirmarFieldLogin));
		driver.findElement(confirmarFieldLogin).click(); 
	}
}