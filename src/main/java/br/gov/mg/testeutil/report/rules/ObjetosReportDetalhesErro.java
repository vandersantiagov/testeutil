package br.gov.mg.testeutil.report.rules;

import org.openqa.selenium.By;

public class ObjetosReportDetalhesErro {
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

	/**
	 * Form Erro
	 */

	public static By nameFormErro = By.name("frmErro");

	public static By xPathTituloErro = By
			.xpath("//*[@id='containerConteudoPrincipal']/div/table/tbody/tr/td/table/tbody/tr/td");

	public static By cssSelectorTituloErro = By
			.xpath("#containerConteudoPrincipal > div > table > tbody > tr > td > table > tbody > tr > td");

	/**
	 * Conteudo Erro
	 */
	public static By mensagemErro = By.id("MENSAGEM_ERRO");

	/**
	 * Conteudo Dados do Erro
	 */
	public static By lblAplicacaoErro = By.id("lblAPLICACAO_ERRO");
	public static By aplicacaoErro = By.id("APLICACAO_ERRO");
	public static By lblModuloErro = By.id("lblMODULO_ERRO");
	public static By moduloErro = By.id("MODULO_ERRO");
	public static By lblTelaErro = By.id("lblTELA_ERRO");
	public static By telaErro = By.id("TELA_ERRO");
	public static By lblAcaoErro = By.id("lblACAO_ERRO");
	public static By acaoErro = By.id("ACAO_ERRO");

	/**
	 * Conteudo Pilha de Erros
	 */
	public static By lblUFWStackTrace = By.id("lblUFW_STACK_TRACE");
	public static By UFWStackTrace = By.id("UFW_STACK_TRACE");

	/**
	 * Conteudo Java Stack Trace
	 */
	public static By lblJavaStackTrace = By.id("lblJAVA_STACK_TRACE");
	public static By javaStackTrace = By.id("JAVA_STACK_TRACE");

}
