package br.gov.mg.testeutil.report.rules;

import org.openqa.selenium.By;

public class ObjetosReport {
	/**
	 * Teste de pilha de erro
	 */

	public static By nomeDoConteinerIdentiricacaoAmbiente = By.id("lblTituloTabela");
	public static By comboEstabelicimento = By
			.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[2]/td[2]/div/input");
	public static By opcaocomboEstabelicimento = By
			.xpath(".//*[@id='containerConteudoPrincipal']/div/form/table[2]/tbody/tr[2]/td[2]/div/div[2]/span[2]");
	public static By comandoConfirmarTelaCadastroEmissor = By.name("actConfirmar");

	public static By validarMensagemPilhaDeErro = By.id("MENSAGEM_ERRO");
	public static By comandoDetalharPilhaDeErro = By.name("btnDetalhes");
}
