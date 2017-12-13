package br.gov.mg.testeutil.menu.sicaf;

import org.openqa.selenium.By;


public class MenuHomeAtividadesFiscais {
	
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
}
