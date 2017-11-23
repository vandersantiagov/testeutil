package br.gov.mg.testeutil.report.html;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedHashMap;

import br.gov.mg.testeutil.vo.KeyMapVO;
import br.gov.mg.testeutil.vo.QuantitativoRunVO;
import br.gov.mg.testeutil.vo.SuitePrincipalVO;
import br.gov.mg.testeutil.vo.SuiteVO;

public class TestaHTML {

	// private static StringBuilder sbReportGeral;

	public static void main(String[] args) {
		try {

			QuantitativoRunVO quantitativoVO = new QuantitativoRunVO();
			quantitativoVO.setQuantidadeErro(20);
			quantitativoVO.setQuantidadeFalha(5);
			quantitativoVO.setQuantidadeRun(186);
			quantitativoVO.setQuantidadeSucesso(186 - 5 - 20);
			;

			String escreveQuantitativo = escreveQuantitativo(quantitativoVO);
			System.out.println(escreveQuantitativo);

			testaHTML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String escreveQuantitativo(QuantitativoRunVO quantitativoVO) {
		StringBuilder sb = new StringBuilder();

		String simboloPercentual = "% ";
		int quantidadeRun = quantitativoVO.getQuantidadeRun();
		int quantidadeFalha = quantitativoVO.getQuantidadeFalha();
		int quantidadeErro = quantitativoVO.getQuantidadeErro();
		int quantidadeSucesso = quantitativoVO.getQuantidadeSucesso();

		StringBuilder sbTitulo = new StringBuilder();
		sbTitulo.append("Total testes: ").append("\n");

		StringBuilder sbCountFailed = new StringBuilder();
		sbCountFailed.append("Failures: ").append(quantidadeFalha);

		StringBuilder sbCountErro = new StringBuilder();
		sbCountErro.append("Erro: ").append(quantidadeErro);

		StringBuilder sbPercentualSuccess = new StringBuilder();
		sbPercentualSuccess.append("Success: ")
				.append(arredondaValor((Double.valueOf(quantidadeSucesso) / Double.valueOf(quantidadeRun)) * 100, 2))
				.append(simboloPercentual);

		StringBuilder sbPercentualFailed = new StringBuilder();
		sbPercentualFailed.append("Failures: ").append(getTotal(quantidadeRun, quantidadeFalha))
				.append(simboloPercentual);

		StringBuilder sbPercentualErro = new StringBuilder();
		sbPercentualErro.append("Erro: ").append(getTotal(quantidadeRun, quantidadeErro)).append(simboloPercentual);

		StringBuilder sbResultCount = new StringBuilder();
		sbResultCount.append(sbTitulo.toString()).append("Quantidade: ").append("\n\t").append("runs: ")
				.append(quantidadeRun).append("\n\t").append(sbCountFailed.toString()).append("\n\t")
				.append(sbCountErro.toString());

		StringBuilder sbResultPercentual = new StringBuilder();
		sbResultPercentual.append("Percentual: ").append("\n\t").append(sbPercentualSuccess.toString()).append("\n\t")
				.append(sbPercentualFailed.toString()).append("\n\t").append(sbPercentualErro.toString());

		sb.append(sbResultCount.toString()).append("\n");
		sb.append(sbResultPercentual.toString()).append("\n");

		return sb.toString();
	}

	private static BigDecimal getTotal(int totalTestesExecutados, int value) {
		return BigDecimal.ZERO.equals(BigDecimal.valueOf(totalTestesExecutados)) ? BigDecimal.ZERO
				: arredondaValor(Double.valueOf(100 * value) / totalTestesExecutados, 2);
	}

	private static BigDecimal arredondaValor(double valor, int casasDecimais) {
		return new BigDecimal(valor).setScale(casasDecimais, RoundingMode.HALF_UP);
	}

	private static void testaHTML() throws IOException {
		SuitePrincipalVO suitePrincipalVO = new SuitePrincipalVO();
		SuiteVO suiteVO = new SuiteVO();
		suiteVO.setNomeProjeto("Projeto Teste");
		suiteVO.setNomeSuite("Suite Teste");

		if (suitePrincipalVO.getSuitesFilhasByNome() == null) {
			suitePrincipalVO.setSuitesFilhasByNome(new LinkedHashMap<KeyMapVO<String, String>, SuiteVO>());
		}

		KeyMapVO<String, String> keySuite = new KeyMapVO<String, String>(suiteVO.getNomeProjeto(),
				suiteVO.getNomeSuite());
		suitePrincipalVO.getSuitesFilhasByNome().put(keySuite, suiteVO);
		suiteVO.setDataInicioExecucao(new Date());
		suiteVO.setDataFimExecucao(new Date());

		ReportHTML.createHTML(suitePrincipalVO);
		// testeStringBuilder();
	}

	// private static void testeStringBuilder() {
	// sbReportGeral = new StringBuilder();
	// ReportHTML.appendReportGeral("Teste ", "is ", "array ", "ou ", "list?");
	// System.out.println(sbReportGeral.toString());
	// }
}
