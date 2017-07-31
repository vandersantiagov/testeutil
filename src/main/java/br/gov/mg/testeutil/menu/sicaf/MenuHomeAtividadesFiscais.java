package br.gov.mg.testeutil.menu.sicaf;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.gov.mg.testeutil.util.sicaf.SeleniumSicaf;
import br.gov.mg.testeutil.util.sicaf.UtilsSicaf;

public class MenuHomeAtividadesFiscais {
		
		/**
		 * Instancia privada do webDriver que vira da suite de teste
		 */
		private static final WebDriver driver;
		@SuppressWarnings("unused")
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
		 * Tela Home do Intranet do SIARE
		 * Acesso ao menu Atividadades Fiscais
		 */
		static By validacaoTituloCorretoCaixaDeServico = By.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[1]/tbody/tr[1]/td");
		static By menuPrincipalVertical = By.linkText("Home");
		static By submenuPrincipalVertical = By.linkText("Home");
		static By subMenuVerticalAtividadesFiscais = By.linkText("Atividades Fiscais");
		static By subMenuVerticalInclusaoAtividadesFiscais = By.linkText("Inclus�o de Documento de In�cio de A��o Fiscal");
		
		static By subMenuVerticalManutencaoAtividadesFiscais = By.linkText("Manuten��o de Documento In�cio de A��o Fiscal");
		
		static By subMenuVerticalRecebimentoDIAF = By.linkText("Recebimento de Documento de In�cio de A��o Fiscal");
		static By subMenuVerticalInformarRecebimento = By.linkText("Informar Recebimento");
		static By subMenuVerticalExluirRecebimento = By.linkText("Excluir Recebimento");

		
		/**
		 * Valida��o do Titulo da tela da Caixa de Servi�o
		 */
		public static void tituloPaginaLoginCorreto(String expectedTitle){
			assertThat("T�tulo Incorreto",  driver.findElement(validacaoTituloCorretoCaixaDeServico).getText(), is(expectedTitle));
		}
		
		/**
		 * Clicar no menu Princiapl Vertical HOME
		 * 
		 */
		public static void menuPrincipalVertical(){
//			wait.until(ExpectedConditions.elementToBeClickable(clickSelecaoDocumentoDiaf));
			UtilsSicaf.isClickable(menuPrincipalVertical);
			driver.findElement(menuPrincipalVertical).click();
		}	
			
		/**
		 * Clicar no item de subMenu Inclus�o DIAF no Menu Vertical HOME
		 * 
		 */
		public static void subMenuPrincipalVerticalAtivdadesFiscaisInclusao(String subMenuPrincipalVertical){
			By correctLocator = null;
			if (subMenuPrincipalVertical.equalsIgnoreCase("Atividades Fiscais")){
				correctLocator = subMenuVerticalAtividadesFiscais;
			} else if (subMenuPrincipalVertical.equalsIgnoreCase("Inclus�o de Documento de In�cio de A��o Fiscal")){
				correctLocator = subMenuVerticalInclusaoAtividadesFiscais;
			}
			UtilsSicaf.isVisible(correctLocator);
			driver.findElement(correctLocator).click();
		}
		
		/**
		 * Clicar no item de subMenu Mantuten��o DIAF no Menu Vertical HOM
		 * 
		 */
		public static void subMenuPrincipalVerticalAtivdadesFiscaisManutencao(String subMenuPrincipalVertical){
			By correctLocator = null;
			if (subMenuPrincipalVertical.equalsIgnoreCase("Atividades Fiscais")){
				correctLocator = subMenuVerticalAtividadesFiscais;
			} else if (subMenuPrincipalVertical.equalsIgnoreCase("Manuten��o de Documento In�cio de A��o Fiscal")){
				correctLocator = subMenuVerticalManutencaoAtividadesFiscais;
			}
			UtilsSicaf.isVisible(correctLocator);
			driver.findElement(correctLocator).click();
		}
		
		/**
		 * Clicar no item de subMenu Informar Recebimento DIAF no Menu Vertical HOM
		 * 
		 */
		public static void subMenuPrincipalVerticalAtivdadesFiscaisInformarRecebimento(String subMenuPrincipalVertical){
			By correctLocator = null;
			if (subMenuPrincipalVertical.equalsIgnoreCase("Atividades Fiscais")){
				correctLocator = subMenuVerticalAtividadesFiscais;
			} else if (subMenuPrincipalVertical.equalsIgnoreCase("Recebimento de Documento de In�cio de A��o Fiscal")){
				correctLocator = subMenuVerticalRecebimentoDIAF;
			}else if (subMenuPrincipalVertical.equalsIgnoreCase("Informar Recebimento")){
				correctLocator = subMenuVerticalInformarRecebimento;
			}
			UtilsSicaf.isVisible(correctLocator);
			driver.findElement(correctLocator).click();
		}
		
		/**
		 * Clicar no item de subMenu Excluir Recebimento DIAF no Menu Vertical HOM
		 * 
		 */
		public static void subMenuPrincipalVerticalAtivdadesFiscaisExcluirRecebimento(String subMenuPrincipalVertical){
			By correctLocator = null;
			if (subMenuPrincipalVertical.equalsIgnoreCase("Atividades Fiscais")){
				correctLocator = subMenuVerticalAtividadesFiscais;
			} else if (subMenuPrincipalVertical.equalsIgnoreCase("Recebimento de Documento de In�cio de A��o Fiscal")){
				correctLocator = subMenuVerticalRecebimentoDIAF;
			}else if (subMenuPrincipalVertical.equalsIgnoreCase("Excluir Recebimento")){
				correctLocator = subMenuVerticalExluirRecebimento;
			}
			UtilsSicaf.isVisible(correctLocator);
			driver.findElement(correctLocator).click();
		}
}
