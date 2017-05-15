package br.gov.mg.testeutil.telas.login;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.gov.mg.testeutil.util.Selenium;
import br.gov.mg.testeutil.util.Utils;



public class MenuHomeAtividadesFiscais {
	
	/**
	 * Instancia privada do webDriver que vira da suite de teste
	 */
	private static final WebDriver driver;
	private static final WebDriverWait wait;
	
	/**
	 * Construtor que ira adicionar a instancia do WebDriver para utilizacao dos metodos
	 */
	static {
		driver = Selenium.getDriver();
		wait = new WebDriverWait(driver, 10);
	}
	
	/**
	 * Definição Técnica dos locators utilizados na página
	 * Tela Home do Intranet do SIARE
	 * Acesso ao menu Atividadades Fiscais
	 */
	static By validacaoTituloCorretoCaixaDeServico = By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[1]/tbody/tr[1]/td");
	static By menuPrincipalVertical = By.linkText("Home");
	static By submenuPrincipalVertical = By.linkText("Home");
	static By subMenuVerticalAtividadesFiscais = By.linkText("Atividades Fiscais");
	static By subMenuVerticalInclusaoAtividadesFiscais = By.linkText("Inclusão de Documento de Início de Ação Fiscal");
	
	static By subMenuVerticalManutencaoAtividadesFiscais = By.linkText("Manutenção de Documento Início de Ação Fiscal");
	
	static By subMenuVerticalRecebimentoDIAF = By.linkText("Recebimento de Documento de Início de Ação Fiscal");
	static By subMenuVerticalInformarRecebimento = By.linkText("Informar Recebimento");
	static By subMenuVerticalExluirRecebimento = By.linkText("Excluir Recebimento");

	
	/**
	 * Validação do Titulo da tela da Caixa de Serviço
	 */
	public static void tituloPaginaLoginCorreto(String expectedTitle){
		assertThat("Título Incorreto",  driver.findElement(validacaoTituloCorretoCaixaDeServico).getText(), is(expectedTitle));
	}
	
	/**
	 * Clicar no menu Princiapl Vertical HOME
	 * 
	 */
	public static void menuPrincipalVertical(){
//		wait.until(ExpectedConditions.elementToBeClickable(clickSelecaoDocumentoDiaf));
		Utils.isClickable(menuPrincipalVertical);
		driver.findElement(menuPrincipalVertical).click();
	}	
		
	/**
	 * Clicar no item de subMenu Inclusão DIAF no Menu Vertical HOME
	 * 
	 */
	public static void subMenuPrincipalVerticalAtivdadesFiscaisInclusao(String subMenuPrincipalVertical){
		By correctLocator = null;
		if (subMenuPrincipalVertical.equalsIgnoreCase("Atividades Fiscais")){
			correctLocator = subMenuVerticalAtividadesFiscais;
		} else if (subMenuPrincipalVertical.equalsIgnoreCase("Inclusão de Documento de Início de Ação Fiscal")){
			correctLocator = subMenuVerticalInclusaoAtividadesFiscais;
		}
		Utils.isVisible(correctLocator);
		driver.findElement(correctLocator).click();
	}
	
	/**
	 * Clicar no item de subMenu Mantutenção DIAF no Menu Vertical HOM
	 * 
	 */
	public static void subMenuPrincipalVerticalAtivdadesFiscaisManutencao(String subMenuPrincipalVertical){
		By correctLocator = null;
		if (subMenuPrincipalVertical.equalsIgnoreCase("Atividades Fiscais")){
			correctLocator = subMenuVerticalAtividadesFiscais;
		} else if (subMenuPrincipalVertical.equalsIgnoreCase("Manutenção de Documento Início de Ação Fiscal")){
			correctLocator = subMenuVerticalManutencaoAtividadesFiscais;
		}
		Utils.isVisible(correctLocator);
		driver.findElement(correctLocator).click();
	}
	
	/**
	 * Clicar no item de subMenu Informar Recebimento DIAF no Menu Vertical HOM
	 * 
	 */
	public static void subMenuPrincipalVerticalAtivdadesFiscaisManutencao(String subMenuPrincipalVertical){
		By correctLocator = null;
		if (subMenuPrincipalVertical.equalsIgnoreCase("Atividades Fiscais")){
			correctLocator = subMenuVerticalAtividadesFiscais;
		} else if (subMenuPrincipalVertical.equalsIgnoreCase("Recebimento de Documento de Início de Ação Fiscal")){
			correctLocator = subMenuVerticalRecebimentoDIAF;
		}else if (subMenuPrincipalVertical.equalsIgnoreCase("Informar Recebimento")){
			correctLocator = subMenuVerticalInformarRecebimento;
		}
		Utils.isVisible(correctLocator);
		driver.findElement(correctLocator).click();
	}
	
	/**
	 * Clicar no item de subMenu Excluir Recebimento DIAF no Menu Vertical HOM
	 * 
	 */
	public static void subMenuPrincipalVerticalAtivdadesFiscaisManutencao(String subMenuPrincipalVertical){
		By correctLocator = null;
		if (subMenuPrincipalVertical.equalsIgnoreCase("Atividades Fiscais")){
			correctLocator = subMenuVerticalAtividadesFiscais;
		} else if (subMenuPrincipalVertical.equalsIgnoreCase("Recebimento de Documento de Início de Ação Fiscal")){
			correctLocator = subMenuVerticalRecebimentoDIAF;
		}else if (subMenuPrincipalVertical.equalsIgnoreCase("Excluir Recebimento")){
			correctLocator = subMenuVerticalExluirRecebimento;
		}
		Utils.isVisible(correctLocator);
		driver.findElement(correctLocator).click();
	}
}