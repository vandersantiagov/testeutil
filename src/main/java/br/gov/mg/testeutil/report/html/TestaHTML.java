package br.gov.mg.testeutil.report.html;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import br.gov.mg.testeutil.vo.KeyMapVO;
import br.gov.mg.testeutil.vo.SuitePrincipalVO;
import br.gov.mg.testeutil.vo.SuiteVO;

public class TestaHTML {

	//private static StringBuilder sbReportGeral;

	public static void main(String[] args) {
		try {
			testaHTML();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void testaHTML() throws IOException {
		SuitePrincipalVO suitePrincipalVO = new SuitePrincipalVO();
		SuiteVO suiteVO = new SuiteVO();
		suiteVO.setNomeProjeto("Projeto Teste");
		suiteVO.setNomeSuite("Suite Teste");

		if (suitePrincipalVO.getSuitesFilhasByNome() == null) {
			suitePrincipalVO.setSuitesFilhasByNome(new HashMap<KeyMapVO<String, String>, SuiteVO>());
		}

		KeyMapVO<String, String> keySuite = new KeyMapVO<String, String>(suiteVO.getNomeProjeto(),
				suiteVO.getNomeSuite());
		suitePrincipalVO.getSuitesFilhasByNome().put(keySuite, suiteVO);
		suiteVO.setDataInicioExecucao(new Date());
		suiteVO.setDataFimExecucao(new Date());

		ReportHTML.createHTML(suitePrincipalVO);
		// testeStringBuilder();
	}

//	private static void testeStringBuilder() {
//		sbReportGeral = new StringBuilder();
//		ReportHTML.appendReportGeral("Teste ", "is ", "array ", "ou ", "list?");
//		System.out.println(sbReportGeral.toString());
//	}
}
